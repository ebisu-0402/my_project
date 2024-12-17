from bleak import BleakClient

async def connect_to_device(address: str):
    async with BleakClient(address) as client:
        print(f"Connected: {client.is_connected}")
        # 必要に応じてサービスやキャラクタリスティックの読み書きが可能
        # 例: value = await client.read_gatt_char(some_characteristic)

# デバイスのアドレス（例: 'XX:XX:XX:XX:XX:XX'）
device_address = '9C:2E:7A:ED:A8:3A'

import asyncio
asyncio.run(connect_to_device(device_address))
