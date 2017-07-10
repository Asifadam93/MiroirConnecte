from pprint import pprint

from os.path import isfile

import affichage
import apiMirror
from gpiozero import Button
import time
import camera
import face_recognition
import numpy as np
from threading import Timer

# définition des variables necessaires à la reconnaissance faciale
face_locations = []
face_encodings = []
known_faces = []
known_users = []
output = np.empty((240, 320, 3), dtype=np.uint8)

# utilisateur reconnu
actual_user = 0

# Définition des boutons qu'on utilisera
button_1 = Button(17)
button_2 = Button(27)


#
def button_1_pressed():
    camera.start_preview(int((affichage.fenetre_center_left )-320), int((affichage.fenetre_block_height +60)))

    compteur = 5
    while compteur >= 0:
        affichage.widget_information.config(text="Prise d'une photo dans " + str(compteur) + " seconde(s)")
        compteur -= 1
        time.sleep(1)
    chemin_photo = camera.take_picture()
    affichage.widget_information.config(text=str("Photo prise, envoie de la photo au serveur"))
    code_photo = apiMirror.post_photo(chemin_photo)
    affichage.widget_information.config(text="Votre code photo : "+str(code_photo))

#
def button_2_pressed():
    affichage.widget_information.config(text="Bouton 2")


#
def add_user(photo_name, id):
    global known_faces, known_users

    if isfile("./img/photos/" + photo_name):
        print("Ajout de " + str(id) + " " + photo_name)
        image = face_recognition.load_image_file("./img/photos/" + user["photo_name"])
        image_encoding = face_recognition.face_encodings(image)[0]
        known_faces.append(image_encoding)
        known_users.append(id)
    else:
        print("Erreur avec " + str(id) + " " + photo_name)


def update_mirror(user_id):
    global actual_user
    # Récupération d'un seul utilisateur
    userIdentifie = apiMirror.get_user(user_id)

    if user_id != actual_user:
        pprint(userIdentifie)

        actual_user = user_id

        try:
            user_modules = userIdentifie["modules"]
        except:
            print("L'utilisateur n'a pas de module")
            user_modules = []

        affichage.clear_mirror()
        affichage.widget_information.config(text="Hello " + userIdentifie["first_name"] + " !")

        # création des modules
        for user_module in user_modules:
            # pprint(user_module)
            if user_module["type"] == "weather":
                affichage.add_widget_meteo(user_module["position"], user_module["name"], user_module["city"],
                                           user_module["country"])
            elif user_module["type"] == "time":
                affichage.add_widget_time(user_module["position"], user_module["name"], user_module["time_zone"])

#
def search_face():
    global output, known_faces, known_users, face_encodings, face_locations

    print("Je recherche un visage")
    camera.camera.capture(output, format="rgb")

    print("Found {} faces in image.".format(len(face_locations)))
    face_locations = face_recognition.face_locations(output)

    faces_encoding = face_recognition.face_encodings(output, face_locations)

    # Loop over each face found in the frame to see if it's someone we know.
    for face_encoding in faces_encoding:
        # See if the face is a match for the known face(s)
        matches = face_recognition.compare_faces(known_faces, face_encoding)

        for index, match in (enumerate(matches)):
            if match == True:
                print("Trouvé")
                update_mirror(known_users[index])

    t = Timer(1, search_face)
    t.start()


# Ajout des comportements qux boutons
button_1.when_pressed = button_1_pressed
button_2.when_pressed = button_2_pressed

# Récupération de tout les utilisateurs (et téléchargement de leurs photos si elles ne sont pas déjà présentes)
users = apiMirror.get_users()

pprint(users)

for index,user in enumerate(users):
    add_user(user["photo_name"], user["id"])

t = Timer(1, search_face)
t.start()

# affichage de la fenêtre
affichage.fenetre.mainloop()

