from pprint import pprint

from os.path import isfile

import api_config
import requests
import json
import urllib.request

token = ""
token_id = 0

payload_connection = {
    "login": api_config.params["login"],
    "password": api_config.params["password"]
}


def connection():
    """
    Permet de se connecter à l'API et de récupérer le token
    """
    global token_id, token
    connection_request = requests.post(api_config.params["url_api"] + "/auth-tokens", data=payload_connection)

    if connection_request.status_code == 201:

        data_token = json.loads(connection_request.text)

        token_id = data_token["id"]
        token = data_token["value"]

        print(token_id)
        print(token)
    else:
        connection_request.raise_for_status()


def token_header():
    """
    Permet de récupérer l'en-tête nécessaire pour la réalisation de certaines requêtes
    """
    global token_id, token
    if not token:
        connection()
    return {'X-Auth-Token': token}


def get_users():
    """
    Permet de récupérer la liste de tout les utilisateurs et de télécharger leur photo
    """
    # récupération des utilisateurs sur l'api du mirroir
    try:
        users_request = requests.get(api_config.params["url_api"] + "/users", headers=token_header())
        users = json.loads(users_request.text)
    except:
        users = []

    # calcul du nombre d'utilisateurs (pour l'affichage du pourcentage
    nombre_utilisateurs = len(users)

    # on récupère la photo de chaque utilisateur que l'on n'a pas encore
    for user_index, user in enumerate(users):

        print("progression : ", (((user_index+1)/nombre_utilisateurs)*100), "%")

        # vérification de la présence de la photo
        if not isfile("./img/photos/" + user["photo_name"]):
            # print("Téléchargement de la photo de", user["first_name"], user["last_name"], "en cours")
            # récupération
            try:
                urllib.request.urlretrieve(
                    api_config.params["url_api"] + "/img/photos/" + user["photo_name"],
                    "./img/photos/" + user["photo_name"]
                )
            except:
                print("Photo de", user["first_name"], user["last_name"], "introuvable")
        # else:
            # print("Photo de", user["first_name"], user["last_name"], "déjà présente")

    return users


def get_user(id):
    """
    Permet de récupérer un utilisateur précis avec la liste de tout ses modules
    :param id:
    :return:
    """
    try:
        users_request = requests.get(api_config.params["url_api"] + "/user/" + str(id), headers=token_header())
        user = json.loads(users_request.text)
        return user
    except:
        return None


def post_photo(photo):
    """
    Permet d'envoyer une photo à notre api
    :param id:
    :return:
    """
    file = {'image': open(photo, 'rb')}

    photo_request = requests.post(api_config.params["url_api"] + "/photo", headers=token_header(), files=file)

    if photo_request.status_code == 200:
        data_photo = json.loads(photo_request.text)
        return data_photo["name"]
    else:
        return None
