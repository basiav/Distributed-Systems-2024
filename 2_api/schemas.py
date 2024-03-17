from pydantic import BaseModel
from fastapi import Form


# class RequestForm(BaseModel):
#     city: int
#     country: str

#     @classmethod
#     def as_form(cls, city: int = Form(...), country: str = Form(...)):
#         return cls(city=city, country=country)


class RequestForm(BaseModel):
    latitude: str
    longitude: str

    @classmethod
    def as_form(cls, latitude: str = Form(...), longitude: str = Form(...)):
        return cls(latitude=latitude, longitude=longitude)
