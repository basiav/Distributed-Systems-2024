import requests


BASE_URL = "https://www.meteosource.com/api/v1/free/point"


class MeteosourceAPI:
    base: str
    key: str

    def __init__(self, key) -> None:
        self.base = BASE_URL
        self.key = key

    async def request(self, latitude: str, longitude: str):
        pass
