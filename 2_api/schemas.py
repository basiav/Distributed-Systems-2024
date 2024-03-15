from pydantic import BaseModel
from fastapi import Form


class RequestForm(BaseModel):
    city: str
    country: str

    @classmethod
    def as_form(cls, city: str = Form(...), country: str = Form(...)):
        return cls(city=city, country=country)
