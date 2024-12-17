import asyncio
from bleak import BleakClient, BleakScanner

# デバイスのUUIDとキャラクタリスティックUUIDを設定してください
DEVICE_UUID = "9C:2E:7A:ED:A8:3A"  # スマホデバイスのUUID
CHARACTERISTIC_UUID = "B3391564-2DF5-11EF-8B14-B91562EBEC60"  # データを受信するキャラクタリスティックUUID

async def run():
    # デバイスをスキャン
    devices = await BleakScanner.discover()
    for device in devices:
        if device.address == DEVICE_UUID:
            print(f"Found device: {device.name} ({device.address})")
            async with BleakClient(device) as client:
                print("Connected to device")
                
                # キャラクタリスティックの読み取り
                data = await client.read_gatt_char(CHARACTERISTIC_UUID)
                print(f"Received data: {data.decode('utf-8')}")
                
                # データに基づく処理をここに追加
                # 例: キー操作やマウス操作をシミュレートするなど
                
                # 例: データに基づいて何らかの処理を実行
                if data.decode('utf-8') == 'some_command':
                    print("Command received: some_command")
                    # コマンドに対する処理をここに追加
            break
        else:
            print("Device not found")
    
asyncio.run(run())
