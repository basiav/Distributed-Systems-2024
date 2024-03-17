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
from html_parser import get_plt


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


@app.get("/img", response_class=HTMLResponse)
async def request_plt(request: Request):
    # print(html_content)

    # with open("./templates/results_plt.html", "w") as f:
    #     f.write(html_content)
    # return templates.TemplateResponse(
    #     "results_plt.html",
    #     context={"request": request, "html": html},
    # )
    pass


@app.get("/forms", response_class=HTMLResponse)
async def get_form(request: Request):
    context = {"request": request}
    return templates.TemplateResponse("index.html", context)


# @app.post("/forms", response_class=HTMLResponse)
@app.post("/forms", response_class=RedirectResponse)
async def post_form(
    request: Request, form_data: RequestForm = Depends(RequestForm.as_form)
):
    print(f"\n\n\n\nform data: {form_data}\n\n\n\n")
    latitude = form_data.latitude
    longitude = form_data.longitude
    return RedirectResponse(
        f"/results/{latitude},{longitude}", status_code=302
    )


@app.get("/results/{latitude},{longitude}")
async def get_weather(request: Request, latitude: str, longitude: str):
    meteosource_api = OpenMeteoAPI()
    response_meteosource = await meteosource_api.request(
        latitude=latitude, longitude=longitude
    )
    # return r
    # html_content = get_plt(response_meteosource["times"], response_meteosource["temperature_2m"])
    # with open("./templates/results_plt.html", "w") as f:
    #     f.write(html_content)
    # return templates.TemplateResponse(
    #     "results_plt.html",
    #     context={"request": request, "html": html_content},
    # )

    return templates.TemplateResponse(
        "results.html",
        context={"request": request, "results": response_meteosource},
    )


@app.get("/current_weather/{place_id}")
async def get_weather_meteosource(request: Request, place_id: str):
    response = requests.get(
        f"https://www.meteosource.com/api/v1/free/point?place_id={place_id}&key={API_KEY_METEOSOURCE}"
    )
    # return JSONResponse(content=response.json())
    print(response.json())
    t = response.json()["current"]["temperature"]
    return templates.TemplateResponse(
        "results.html",
        context={"request": request, "results": t},
    )
