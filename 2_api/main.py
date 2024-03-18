from fastapi import Depends, FastAPI, Request
from fastapi.responses import HTMLResponse, RedirectResponse
from fastapi.templating import Jinja2Templates
from schemas import RequestForm
import os
from os.path import join, dirname
from dotenv import load_dotenv
from api.meteosource_api import MeteosourceAPI, MeteosourceResults
from api.openmeteo_api import OpenMeteoAPI, OpenMeteoResults
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
    MS_api = MeteosourceAPI(key=API_KEY_METEOSOURCE)
    (
        place_name,
        place_id,
        adm_area,
        country,
    ) = await MS_api.request_nearest_place(
        latitude=latitude, longitude=longitude
    )

    MS_res: MeteosourceResults = await MS_api.request_points(place_id)
    MS_current_temp = MS_res.current_temp
    MS_past_plt = get_encoded_img(
        xx=[k for k in MS_res.past.keys()],
        yy=[v for v in MS_res.past.values()],
    )
    MS_forecast_plt = get_encoded_img(
        xx=[k for k in MS_res.forecast.keys()],
        yy=[v for v in MS_res.forecast.values()],
    )

    OM_api = OpenMeteoAPI()
    OM_res: OpenMeteoResults = await OM_api.request(
        latitude=latitude, longitude=longitude
    )
    OM_current_temp = OM_res.current_temp
    OM_past_plt = get_encoded_img(
        xx=[k for k in OM_res.past.keys()],
        yy=[v for v in OM_res.past.values()],
    )
    OM_forecast_plt = get_encoded_img(
        xx=[k for k in OM_res.forecast.keys()],
        yy=[v for v in OM_res.forecast.values()],
        locator=True,
    )

    mean_temp = round((float(MS_current_temp) + float(OM_current_temp)) / 2, 2)

    return templates.TemplateResponse(
        "results.html",
        context={
            "request": request,
            "place_name": place_name,
            "adm_area": adm_area,
            "country": country,
            "mean_temp": mean_temp,
            "MS_current_temp": MS_current_temp,
            "MS_past_img": MS_past_plt,
            "MS_forecast_img": MS_forecast_plt,
            "OM_past_img": OM_past_plt,
            "OM_forecast_img": OM_forecast_plt,
            "OM_current_temp": OM_current_temp,
        },
    )
