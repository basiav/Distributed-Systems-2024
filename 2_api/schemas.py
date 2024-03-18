from pydantic import BaseModel
from fastapi import Form


class RequestForm(BaseModel):
    latitude: str
    longitude: str

    @classmethod
    def as_form(cls, latitude: str = Form(...), longitude: str = Form(...)):
        return cls(latitude=latitude, longitude=longitude)
