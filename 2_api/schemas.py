from pydantic import BaseModel
from fastapi import Form


class RequestForm(BaseModel):
    city: int
    country: str

    @classmethod
    def as_form(cls, city: int = Form(...), country: str = Form(...)):
        return cls(city=city, country=country)
