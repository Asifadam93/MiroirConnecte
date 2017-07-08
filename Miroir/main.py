import affichage
import apiMirror
from gpiozero import Button
import time
import camera

# Définition des boutons qu'on utilisera
button_1 = Button(17)
button_2 = Button(27)

#
def button_1_pressed():
    affichage.widget_information.config(text="Prise d'une photo dans 5s")
    compteur = 5
    while compteur >= 0:
        time.sleep(1)
        affichage.widget_information.config(text=str(compteur))
        compteur -= 1
    camera.take_picture()
    affichage.widget_information.config(text=str("Photo prise"))

#
def button_2_pressed():
    affichage.widget_information.config(text="Bouton 2")

#
button_1.when_pressed = button_1_pressed
button_2.when_pressed = button_2_pressed

# Récupération de tout les utilisateurs (et téléchargement de leurs photos si elles ne sont pas déjà présentes)
users = apiMirror.get_users()



# Récupération d'un seul utilisateur
userIdentifie = apiMirror.get_user(1)

try:
    user_modules = userIdentifie["modules"]
except:
    print("L'utilisateur n'a pas de module")
    user_modules = []

# création des modules
for user_module in user_modules:
    # pprint(user_module)
    if user_module["type"] == "weather":
        affichage.add_widget_meteo(user_module["position"], user_module["name"], user_module["city"], user_module["country"])
    elif user_module["type"] == "time":
        affichage.add_widget_time(user_module["position"], user_module["name"], user_module["time_zone"])


# affichage de la fenêtre
affichage.fenetre.mainloop()

