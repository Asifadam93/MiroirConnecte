from pprint import pprint
import api_config
import requests


def get_actual_weather_with_city(city, country):
    '''
    Permet de récupérer d'une ville
    :param city:
    :param country:
    :return:
    '''
    # TODO try except
    url_params = {"appid": api_config.params["token_weather"], "units": "metric", "lang": "fr"}
    query = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "," + country
    req = requests.get(query, params=url_params)
    data = req.json()
    return data


def weather_api_icon_converter(icon):
    '''
    Permet de convertir le nom des icones openweathermap par les icones en notre possession
    :param icon:
    :return:
    '''
    if (icon == '01d') or (icon == '01n'):
        return 'sunny.gif'
    elif (icon == '02d') or (icon == '02n'):
        return 'mostlycloudy.gif'
    elif (icon == '03d') or (icon == '03n'):
        return 'cloudy.gif'
    elif (icon == '04d') or (icon == '04n'):
        return 'cloudy.gif'
    elif (icon == '50d') or (icon == '50n'):
        return 'hazy.gif'
    elif (icon == '09d') or (icon == '09n'):
        return 'rain.gif'
    elif (icon == '10d') or (icon == '10n'):
        return 'chancerain.gif'
    elif (icon == '11d') or (icon == '11n'):
        return 'tstorms.gif'
    elif (icon == '13d') or (icon == '13n'):
        return 'chancesnow.gif'
    else:
        return 'unknow.gif'