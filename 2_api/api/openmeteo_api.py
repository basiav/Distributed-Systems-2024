import requests
from datetime import datetime, timedelta
from pydantic import BaseModel
from typing import Union


BASE_URL = "https://api.open-meteo.com/v1/forecast"


class OpenMeteoResults(BaseModel):
    current_temp: str
    past: dict[Union[str, datetime], Union[str, float]]
    forecast: dict[Union[str, datetime], Union[str, float]]


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

        response = requests.get(url)

        hourly_data_arr = response.json()["hourly"]
        times = hourly_data_arr["time"]
        temperature_2m = hourly_data_arr["temperature_2m"]

        now = datetime.now()
        now_string = now.strftime("%Y-%m-%dT%H:%M")
        datetime_now = datetime.strptime(now_string, "%Y-%m-%dT%H:%M")

        assert len(times) == len(temperature_2m)

        past: dict[Union[str, datetime], Union[str, float]] = {}
        forecast: dict[Union[str, datetime], Union[str, float]] = {}

        for i, time in enumerate(times):
            date = datetime.strptime(time, "%Y-%m-%dT%H:%M")
            if date <= datetime_now:
                past[f"{date}"] = temperature_2m[i]
            else:
                forecast[f"{date}"] = temperature_2m[i]

            if (date - datetime_now).total_seconds() <= 3600:
                current_temp = temperature_2m[i]

        return OpenMeteoResults(
            current_temp=current_temp, past=past, forecast=forecast
        )
