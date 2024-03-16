from fastapi import Depends, FastAPI, Request, Form
from fastapi.responses import HTMLResponse, JSONResponse
from fastapi.staticfiles import StaticFiles
from fastapi.templating import Jinja2Templates
from schemas import RequestForm
import requests


app = FastAPI()


# app.mount("/static", StaticFiles(directory="templates"), name="static")
templates = Jinja2Templates(directory="templates")


@app.get("/", response_class=HTMLResponse)
async def root(request: Request):
    context = {"request": request}
    return templates.TemplateResponse("base.html", context)


@app.get("/weather/{latitude},{longitude}", response_class=JSONResponse)
async def get_weather(latitude: str, longitude: str):
    response = requests.get(f"https://api.weather.gov/points/{latitude},{longitude}")
    return JSONResponse(content=response.json())


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
