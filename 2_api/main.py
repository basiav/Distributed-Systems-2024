from fastapi import Depends, FastAPI, Request, Form
from fastapi.responses import HTMLResponse, JSONResponse
from fastapi.staticfiles import StaticFiles
from fastapi.templating import Jinja2Templates
from schemas import RequestForm
import requests
import numpy as np
import matplotlib.pyplot as plt
import base64
from io import BytesIO
import os
from os.path import join, dirname
from dotenv import load_dotenv
from datetime import datetime


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


# @app.get("/weather/{latitude},{longitude}", response_class=JSONResponse)
@app.get("/weather/{latitude},{longitude}", response_class=HTMLResponse)
async def get_weather(request: Request, latitude: str, longitude: str):
    response = requests.get(
        f"https://api.weather.gov/points/{latitude},{longitude}"
    )
    # return JSONResponse(content=response.json())
    print(response.json())
    return templates.TemplateResponse(
        "results.html",
        context={
            "request": request,
            "latitude": latitude,
            "longitude": longitude,
        },
    )


@app.get("/img", response_class=HTMLResponse)
async def get_plt(request: Request):
    x = np.arange(0, 2 * np.pi, 0.1)
    y = np.sin(x)

    fig, ax = plt.subplots()
    ax.plot(x, y)

    ax.set_xlabel("x")
    ax.set_ylabel("y")
    ax.set_title("Sinusoid")

    tmpfile = BytesIO()
    fig.savefig(tmpfile, format="jpeg")
    encoded = base64.b64encode(tmpfile.getvalue()).decode("utf-8")

    html = (
        "Some html head"
        + "<img src='data:image/jpeg;base64,{}' alt='Hello'>".format(encoded)
        + "Some more html"
    )
    ext = "{% extends 'base.html' %}"
    b = "{% block content %}"
    e = "{% endblock %}"

    html_content = """
    {}
    {}
    <html>
        <head>
            <title>Some HTML in here</title>
        </head>
        <body>
            <h1>{}</h1>
        </body>
    </html>
    {}
    """.format(
        ext, b, html, e
    )

    print(html_content)

    with open("./templates/results_plt.html", "w") as f:
        f.write(html_content)
    return templates.TemplateResponse(
        "results_plt.html",
        context={"request": request, "html": html},
    )


# @app.post("/", response_class=HTMLResponse)
# async def post_form(request: Request, city: str = Form(...), country: str = Form(...)):
#     print(f"city: {city}")
#     print(f"country: {country}")
#     return templates.TemplateResponse("index.html", {"request": request})


@app.get("/forms", response_class=HTMLResponse)
async def get_form(request: Request):
    context = {"request": request}
    return templates.TemplateResponse("index.html", context)


@app.post("/forms", response_class=HTMLResponse)
async def post_form(
    request: Request, form_data: RequestForm = Depends(RequestForm.as_form)
):
    print(form_data)
    return templates.TemplateResponse("index.html", {"request": request})


@app.get("/current_weather/{place_id}")
async def get_weather_meteosource(request: Request, place_id: str):
    response = requests.get(
        f"https://www.meteosource.com/api/v1/free/point?place_id={place_id}&key={API_KEY_METEOSOURCE}"
    )
    # return JSONResponse(content=response.json())
    print(response.json())
    t = response.json()["current"]["temperature"]
    return templates.TemplateResponse(
        "results_weather.html",
        context={"request": request, "results": t},
    )


@app.get("/weather/openmeteo/{latitude},{longitude}")
async def get_weather_openmeteo(
    request: Request, latitude: str, longitude: str
):
    response = requests.get(
        f"https://api.open-meteo.com/v1/forecast?latitude={latitude}&longitude={longitude}&hourly=temperature_2m"
    )
    # return JSONResponse(content=response.json())
    print(response.json())
    hourly = response.json()["hourly"]
    times = hourly["time"]
    temperature_2m = hourly["temperature_2m"]

    now = datetime.now()
    # current_time = now.strftime("%H:%M:%S")
    dateStart = datetime.strptime(times[0], "%Y-%m-%dT%H:%M")
    # dateNow = datetime.strptime(str(now), "%Y-%m-%dT%H:%M")
    dateNow = now.strftime("%Y-%m-%dT%H:%M")
    print(type(dateStart))

    i = 0
    j = 0
    date = times[i]
    while date <= dateNow:
        j = i
        date = times[i]
        i += 1

    print(f"j {j}")
    print(times[j])
    print(temperature_2m[j])

    return templates.TemplateResponse(
        "results_weather.html",
        context={"request": request, "results": dateNow},
    )
