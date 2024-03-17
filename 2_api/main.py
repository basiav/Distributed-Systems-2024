from fastapi import Depends, FastAPI, Request, Form
from fastapi.responses import HTMLResponse, JSONResponse, RedirectResponse
from fastapi.staticfiles import StaticFiles
from fastapi.templating import Jinja2Templates
from schemas import RequestForm
import requests

# import base64
# from io import BytesIO
import os
from os.path import join, dirname
from dotenv import load_dotenv
from datetime import datetime
from api.meteosource_api import MeteosourceAPI
from api.openmeteo_api import OpenMeteoAPI
from html_parser import get_encoded_img


dotenv_path = join(dirname(__file__), ".env")
load_dotenv(dotenv_path)

API_KEY_METEOSOURCE = os.environ.get("API_KEY_METEOSOURCE")


app = FastAPI()


# app.mount("/static", StaticFiles(directory="templates"), name="static")
templates = Jinja2Templates(directory="templates")


@app.get("/", response_class=HTMLResponse)
async def root(request: Request):
    context = {"request": request}
    return templates.TemplateResponse("base.html", context)


@app.get("/forms", response_class=HTMLResponse)
async def get_form(request: Request):
    context = {"request": request}
    return templates.TemplateResponse("index.html", context)


@app.post("/forms", response_class=RedirectResponse)
async def post_form(
    request: Request, form_data: RequestForm = Depends(RequestForm.as_form)
):
    latitude = form_data.latitude
    longitude = form_data.longitude
    return RedirectResponse(
        f"/results/{latitude},{longitude}", status_code=302
    )


@app.get("/results/{latitude},{longitude}")
async def get_weather(request: Request, latitude: str, longitude: str):
    openmeteo_api = OpenMeteoAPI()
    response_openmeteo = await openmeteo_api.request(
        latitude=latitude, longitude=longitude
    )

    meteosource_api = MeteosourceAPI(key=API_KEY_METEOSOURCE)
    res = await meteosource_api.request_nearest_place(
        latitude=latitude, longitude=longitude
    )
    place_name, place_id, adm_area = res

    results = [response_openmeteo, place_name]
    encoded_img = get_encoded_img(
        response_openmeteo["times"], response_openmeteo["temperature_2m"]
    )

    return templates.TemplateResponse(
        "results.html",
        context={
            "request": request,
            "results": results,
            "img_path": encoded_img,
        },
    )
