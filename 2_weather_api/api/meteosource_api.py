import asyncio
from fastapi import HTTPException
import requests
from datetime import datetime
from pydantic import BaseModel
from typing import Union


BASE_URL = "https://www.meteosource.com/api/v1/free"


class MeteosourceResults(BaseModel):
    current_temp: str
    past: dict[Union[str, datetime], Union[str, float]]
    forecast: dict[Union[str, datetime], Union[str, float]]


class MeteosourceAPI:
    base: str
    key: str

    def __init__(self, key) -> None:
        self.base = BASE_URL
        self.key = key

    def check_and_raise_502(self, response, latitude: str, longitude: str):
        if response is None:
            raise HTTPException(
                status_code=502,
                detail="No data found in MeteosourceAPI "
                + f"for latitude: {latitude} "
                + f"and longitude: {longitude}",
            )

    async def request_nearest_place(self, latitude: str, longitude: str):
        url = (
            self.base
            + "/nearest_place"
            + f"?lat={latitude}"
            + f"&lon={longitude}"
            + f"&key={self.key}"
        )
        res = await asyncio.to_thread(requests.get, url)

        response = res.json()

        self.check_and_raise_502(response, latitude, longitude)

        try:
            place_name = response["name"]
            place_id = response["place_id"]
            adm_area = response["adm_area1"]
            country = response["country"]
        except Exception as e:
            print(e)
            raise HTTPException(
                status_code=502,
                detail="No data found in MeteosourceAPI "
                + f"for latitude: {latitude} "
                + f"and longitude: {longitude}",
            )

        return place_name, place_id, adm_area, country

    async def request_points(self, place_id: str) -> MeteosourceResults:
        url = (
            self.base + "/point" + f"?place_id={place_id}" + f"&key={self.key}"
        )

        res = await asyncio.to_thread(requests.get, url)

        self.check_and_raise_502(res, place_id, place_id)

        current_temp = res.json()["current"]["temperature"]
        hourly_data_arr = res.json()["hourly"]["data"]

        now = datetime.now()
        now_string = now.strftime("%Y-%m-%dT%H:%M:%S")
        datetime_now = datetime.strptime(now_string, "%Y-%m-%dT%H:%M:%S")

        past: dict[Union[str, datetime], Union[str, float]] = {}
        forecast: dict[Union[str, datetime], Union[str, float]] = {}
        for data_json in hourly_data_arr:
            date = datetime.strptime(data_json["date"], "%Y-%m-%dT%H:%M:%S")
            if date <= datetime_now:
                past[f"{date}"] = data_json["temperature"]
            else:
                forecast[f"{date}"] = data_json["temperature"]

        results = MeteosourceResults(
            current_temp=current_temp, past=past, forecast=forecast
        )

        return results
