import requests
from datetime import datetime


BASE_URL = "https://api.open-meteo.com/v1/forecast"


# def parse_response()


class OpenMeteoAPI:
    base: str

    def __init__(self) -> None:
        self.base = BASE_URL

    async def request(self, latitude: str, longitude: str):
        url = (
            self.base
            + f"?latitude={latitude}"
            + f"&longitude={longitude}"
            + "&&hourly=temperature_2m"
        )

        results = {}

        response = requests.get(url)
        hourly = response.json()["hourly"]
        times = hourly["time"]
        temperature_2m = hourly["temperature_2m"]

        now = datetime.now()
        # dateStart = datetime.strptime(times[0], "%Y-%m-%dT%H:%M")
        dateNow = now.strftime("%Y-%m-%dT%H:%M")

        i = 0
        date = times[i]
        while date <= dateNow:
            date = times[i]
            i += 1

        results["temperature_2m"] = temperature_2m
        results["times"] = times
        results["current_temp"] = temperature_2m[i]
        results["time_current_temp"] = times[i - 1] if i > 0 else i

        return results
