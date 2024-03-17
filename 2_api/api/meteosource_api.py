import requests


BASE_URL = "https://www.meteosource.com/api/v1/free"


class MeteosourceAPI:
    base: str
    key: str

    def __init__(self, key) -> None:
        self.base = BASE_URL
        self.key = key

    async def request_nearest_place(self, latitude: str, longitude: str):
        url = (
            self.base
            + "/nearest_place"
            + f"?lat={latitude}"
            + f"&lon={longitude}"
            + f"&key={self.key}"
        )
        res = requests.get(url)
        response = res.json()
        place_name = response["name"]
        place_id = response["place_id"]
        adm_area = response["adm_area1"]

        return place_name, place_id, adm_area
