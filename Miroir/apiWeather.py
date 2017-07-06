from pprint import pprint
import api_config
import requests


def getWeatherByCity(cityName):
    url_params = {"appid": api_config.params["token_weather"], "units": "metric", "lang": "fr"}
    query = "http://api.openweathermap.org/data/2.5/weather?q=" + cityName
    req = requests.get(query, params=url_params)
    data = req.json()
    pprint(data)


getWeatherByCity("Paris")