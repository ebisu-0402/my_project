import asyncio
from bleak import BleakScanner, BleakClient

# スキャンしてデバイスを発見
async def scan():
    devices = await BleakScanner.discover()
    for device in devices:
        print(f"Device: {device.name}, Address: {device.address}")

# Bluetoothデバイスに接続し、データを受信
async def connect_and_receive(address):
    async with BleakClient(address) as client:
        services = await client.get_services()
        for service in services:
            print(f"[Service] {service.uuid}")
            for char in service.characteristics:
                print(f"\t[Characteristic] {char.uuid}, Handle: {char.handle}")
                if "read" in char.properties:
                    value = await client.read_gatt_char(char)
                    print(f"\t\tValue: {value}")

# メイン処理
async def main():
    print("Scanning for devices...")
    await scan()

    # デバイスのMACアドレス（スマホ側から送信するデバイスのアドレス）
    device_address = "9C:2E:7A:ED:A8:39"  # ここに接続したいデバイスのアドレスを入力
    print(f"Connecting to {device_address}...")
    await connect_and_receive(device_address)

# 実行
asyncio.run(main())
