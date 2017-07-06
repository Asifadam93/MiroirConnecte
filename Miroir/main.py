from pprint import pprint
import apiWeather
import affichage
import apiMirror

# Récupération de tout les utilisateurs (et téléchargement de leurs photos si elles ne sont pas déjà présentes)
users = apiMirror.get_users()
# pprint(users)

# Récupération d'un seul utilisateur
userIdentifie = apiMirror.get_user(16)
# pprint(userIdentifie)

try:
    user_modules = userIdentifie["modules"]
except:
    user_modules = []

for user_module in user_modules:
    # pprint(user_module)
    if user_module["type"] == "weather":
        affichage.add_widget_meteo(user_module["position"], user_module["name"], user_module["city"], user_module["country"])
    elif user_module["type"] == "time":
        affichage.add_widget_time(user_module["position"], user_module["name"], user_module["time_zone"])




# création des widgets de météo
#
'''
widget_meteo_2 = affichage.add_widget_meteo("S")
widget_meteo_1 = affichage.add_widget_meteo("top_left")

# création des widgets d'horaire
label_heure_tahiti = affichage.add_widget_time("NW", "Heure à tahiti", "Pacific/Tahiti")

# création des widgets d'horaire
label_heure_tahiti = affichage.add_widget_time("NE", "Heure à paris", "Europe/Paris")

'''

# affichage de la fenêtre
affichage.fenetre.mainloop()

