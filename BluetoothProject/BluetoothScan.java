import javax.bluetooth.*;
import java.util.Vector;

public class BluetoothScan implements DiscoveryListener {

    private final Vector<RemoteDevice> devicesDiscovered = new Vector<>();

    public static void main(String[] args) {
        try {
            BluetoothScan scanner = new BluetoothScan();
            LocalDevice localDevice = LocalDevice.getLocalDevice();
            DiscoveryAgent agent = localDevice.getDiscoveryAgent();

            agent.startInquiry(DiscoveryAgent.GIAC, scanner);
            System.out.println("Inquiry started...");
            synchronized (scanner) {
                scanner.wait();
            }
            System.out.println("Inquiry completed.");

            for (RemoteDevice device : scanner.devicesDiscovered) {
                System.out.println("Device found: " + device.getFriendlyName(false) + " - " + device.getBluetoothAddress());
            }
        } catch (BluetoothStateException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
        devicesDiscovered.add(btDevice);
    }

    @Override
    public void inquiryCompleted(int discType) {
        synchronized (this) {
            notifyAll();
        }
    }

    @Override
    public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
        // This method is not used in this example.
    }

    @Override
    public void serviceSearchCompleted(int transID, int respCode) {
        // This method is not used in this example.
    }
}
