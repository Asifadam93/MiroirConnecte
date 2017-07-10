import functools
from pprint import pprint
from tkinter import *
from datetime import datetime, time
from pytz import timezone

# la fenêtre
import apiWeather

fenetre = Tk()
fenetre.configure(bg="#000000", pady=20, padx=20)
fenetre.attributes("-fullscreen", True)

# définitions des variables destinée à la taille des frames
fenetre.attributes("-fullscreen", True)
fenetre_block_width = int((fenetre.winfo_screenwidth() - 40) / 3)
fenetre_block_height = int((fenetre.winfo_screenheight() -40) / 3)
fenetre_block_center_x = int(fenetre_block_width /2)
fenetre_block_center_y = int(fenetre_block_height /2)

# définition des variables permettant de connaitre la position centrale de la fenetre
fenetre_center_left = int(fenetre.winfo_screenwidth() /2)
fenetre_center_top = int(fenetre.winfo_screenheight() /2)



# les cellules
cellules = []

# les images qui seront utilisées pour certains module
images = []

# génération de la grille ici les Frame seront vides mais prendront tout l'espace disponible grâce au weight
for ligne in range(3):
    for colonne in range(3):
        tempo = Frame(fenetre, relief=GROOVE, bg='#000000', width=fenetre_block_width, height=fenetre_block_height) # 000000 quand les test sont terminés

        sticky=N+S+E+W

        if ligne == 0:
            sticky=N
        elif ligne == 1:
            sticky=N+S
        else:
            sticky = S

        if colonne == 0:
            sticky = sticky+W
        elif colonne == 1:
            sticky = sticky+W+E
        else:
            sticky = sticky+E

        tempo.grid(row=ligne, column=colonne, sticky=sticky)
        cellules.append(tempo)

# Création d'un array qui contient toute les cellule avec une position plus explicite
positions = {
    "top_left":     cellules[0],
    "top":          cellules[1],
    "top_right":    cellules[2],
    "left":         cellules[3],
    "center":       cellules[4],
    "right":        cellules[5],
    "bottom_left":  cellules[6],
    "bottom":       cellules[7],
    "bottom_right": cellules[8]
}
# Création d'un label d'information
widget_information = Label(positions["center"], text="", fg="white", bg='#000000', font=('Arial', 20))
widget_information.pack(pady=0, padx=0, anchor=N)

def convert_position(libelle_position="top"):
    if libelle_position == "top_left":
        return NW
    elif libelle_position == "top":
        return N
    elif libelle_position == "top_right":
        return NE
    elif libelle_position == "left":
        return W
    elif libelle_position == "center":
        return CENTER
    elif libelle_position == "right":
        return E
    elif libelle_position == "bottom_left":
        return SW
    elif libelle_position == "bottom":
        return S
    elif libelle_position == "bottom_right":
        return SE
    else:
        return None


def add_widget_meteo(position_label, libelle, city, country):
    '''
    Cette fonction permet d'ajouter un widget de météo
    :param position_label: l'emplacement du module
    :param libelle: Le libellé descriptif du widget
    :param city: La ville demandée pour la météo
    :param country: Le pays de la ville demandée pour la météo
    :return:
    '''

    global images, fenetre_block_height, positions

    #récupération de la position du widget
    anchor_widget = convert_position(position_label)

    # ajout du libellé du titre
    widget_titre = Label(positions[position_label], text=libelle, fg="white", bg='#000000', font=('Arial', 20))
    widget_titre.pack(pady=0, padx=0, anchor=anchor_widget)

    # récupération des données météo depuis l'apiWeather
    weather_data = apiWeather.get_actual_weather_with_city(city, country)
    weather_icon = apiWeather.weather_api_icon_converter(weather_data["weather"][0]["icon"])

    # création du canas
    canvas = Canvas(positions[position_label], width=256, height=128, bg='#000000', borderwidth=0, highlightcolor='#000000',
                    highlightbackground='#000000')
    # Ajout d'une image
    images.append(PhotoImage(file="./img/weather-icon/"+weather_icon))
    canvas.create_image(0, 54, anchor=W, image=images[(len(images)-1)])

    # Ajout des textes pour la météo
    canvas.create_text(128, 34, text=str(weather_data["main"]["temp"])+"°", font="Arial 30", fill="white", anchor=W)
    canvas.create_text(128, 64, text=str(weather_data["main"]["humidity"])+"%", font="Arial 15", fill="white", anchor=W)
    canvas.pack(anchor=anchor_widget)

    return canvas


def update_widget_time(label, module_timezone):
    '''
    Fonction permettant de mettre l'horaire du label utilisé pour afficher l'heure
    :param label: Le label de l'heure
    :param module_timezone: La timezone
    :return:
    '''
    widget_zone = timezone(module_timezone)
    widget_time = datetime.now(widget_zone)
    if widget_time.second %2 == 0:
        time_text = widget_time.strftime('%H:%M')
    else:
        time_text = widget_time.strftime('%H %M')

    label.config(text=time_text)

    label.after(1000, lambda: update_widget_time(label, module_timezone))


def add_widget_time(position_label, libelle, module_timezone):
    '''
    Fonction permettant de créer un widget de type time
    :param position_label:  emplacement du widget
    :param libelle: libellé du widget
    :return: None
    '''
    global fenetre_block_height, positions

    anchor_widget = convert_position(position_label)

    # Création label indiquant le nom du widget
    widget_titre = Label(positions[position_label], text=libelle, fg="white", bg='#000000', font=('Arial', 20))
    widget_titre.pack(pady=0, padx=0, anchor=anchor_widget)

    # Création du label indiquant l'heure
    widget_heure = Label(positions[position_label], text="test", fg="white", bg='#000000', font=('Arial', 50))
    widget_heure.pack(anchor=anchor_widget)

    # lancement de la mise à jour de l'heure
    widget_heure.after(1000, lambda: update_widget_time(widget_heure, module_timezone))

    return widget_heure


def clear_mirror():
    global cellules
    for index, cellule in enumerate(cellules):
        if index != 4:
            for widget in cellule.winfo_children():
                widget.destroy()
