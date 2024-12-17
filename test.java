import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String DEVICE_ADDRESS = "58-6D-67-A9-67-3E"; // PCのBluetoothデバイスのMACアドレス
    private static final UUID MY_UUID = UUID.fromString("B3391564-2DF5-11EF-8B14-B91562EBE"); // SPP UUID

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothSocket bluetoothSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetoothがサポートされていません", Toast.LENGTH_SHORT).show();
            return;
        }

        BluetoothDevice device = bluetoothAdapter.getRemoteDevice(DEVICE_ADDRESS);

        try {
            bluetoothSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
            bluetoothSocket.connect();
            sendData("Hello from Android");
        } catch (IOException e) {
            Log.e(TAG, "Bluetooth接続エラー", e);
            Toast.makeText(this, "Bluetooth接続エラー", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendData(String message) {
        try {
            OutputStream outputStream = bluetoothSocket.getOutputStream();
            outputStream.write(message.getBytes());
            outputStream.flush();
        } catch (IOException e) {
            Log.e(TAG, "データ送信エラー", e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (bluetoothSocket != null) {
                bluetoothSocket.close();
            }
        } catch (IOException e) {
            Log.e(TAG, "Bluetoothソケットクローズエラー", e);
        }
    }
}

