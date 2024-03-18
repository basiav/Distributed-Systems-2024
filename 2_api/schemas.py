from pydantic import BaseModel
from fastapi import Form, HTTPException
import re


class RequestForm(BaseModel):
    latitude: str
    longitude: str

    @classmethod
    def validate_latitude_longitude(
        cls, latitude: str, longitude: str
    ) -> bool:
        regex_dec = re.compile(r"^\d*[.]?\d*$")
        if not regex_dec.match(latitude) or not regex_dec.match(longitude):
            return False

        return abs(float(latitude)) <= 90 and abs(float(longitude)) <= 180

    @classmethod
    def as_form(
        cls,
        latitude: str = Form(..., max_length=10),
        longitude: str = Form(..., max_length=10),
    ):
        if not RequestForm.validate_latitude_longitude(latitude, longitude):
            raise HTTPException(
                status_code=400,
                detail="Latitude and longitude must be like %digits.%digits"
                + f" {latitude} and {longitude} were given instead",
            )

        return cls(latitude=latitude, longitude=longitude)
