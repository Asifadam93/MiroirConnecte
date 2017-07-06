import functools
from pprint import pprint
from tkinter import *
from datetime import datetime, time
from pytz import timezone

# la fenêtre
fenetre = Tk()
fenetre.configure(bg="#000000", pady=20, padx=20)

# définitions des variables destinée à la taille des frames
fenetre.attributes("-fullscreen", True)
fenetre_block_width = ((fenetre.winfo_screenwidth() - 40) / 3)
fenetre_block_height = ((fenetre.winfo_screenheight() -40) / 3)
fenetre_block_center_x = (fenetre_block_width /2)
fenetre_block_center_y = (fenetre_block_height /2)


# définitions des variables destinée à la taille des frames
fenetre.attributes("-fullscreen", True)
fenetre_block_width = ((fenetre.winfo_screenwidth() - 40) / 3)
fenetre_block_height = ((fenetre.winfo_screenheight() -40) / 3)

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
position = {
    "NW" : cellules[0],
    "N"  : cellules[1],
    "NE" : cellules[2],
    "SW" : cellules[6],
    "S"  : cellules[7],
    "SE" : cellules[8]
}


'''

# test heure à Tahiti
labelTempoNW_titre = Label(position["NE"], text="Heure à Tahiti :", fg="white", bg='#000000', font=('Arial', 20))
labelTempoNW_titre.pack(pady=0, padx=0, anchor=NE)
labelTempoNW_heure = Label(position["NE"], text="test", fg="white", bg='#000000', font=('Arial', 50))
labelTempoNW_heure.pack(anchor=NE)

# test heure à Paris
labelTempoNE_titre = Label(cellules[0], text="Heure à Paris :", fg="white", bg='#000000', font=('Arial', 20))
labelTempoNE_titre.pack(pady=0, padx=0, anchor=NW)
labelTempoNE_heure = Label(cellules[0], text="test", fg="white", bg='#000000', font=('Arial', 50))
labelTempoNE_heure.pack(pady=0, padx=0, anchor=NW)
'''
# test météo



def add_widget_meteo(position_label):
    '''
    Cette fonction permet d'ajouter un widget de météo

    :param position_label: String
    :return: Le canvas correspondant à l'objet créé
    '''

    global images, fenetre_block_height, position

    # création du canas
    canvas = Canvas(position[position_label], width=256, height=128, bg='#000000', borderwidth=0, highlightcolor='#000000',
                    highlightbackground='#000000')
    # Ajout d'une image
    images.append(PhotoImage(file="./img/weather-icon/sunny.gif"))
    canvas.create_image(0, 64, anchor=W, image=images[(len(images)-1)])

    # Ajout du texte
    canvas.create_text(128, 44, text="21.4°", font="Arial 30", fill="white", anchor=W)
    canvas.create_text(128, 74, text="68%", font="Arial 15", fill="white", anchor=W)
    canvas.create_text(128, 94, text="Paris", font="Arial 15", fill="white", anchor=W)

    if position_label == "NW":
        canvas.pack(anchor=NW)
    elif position_label == "N":
        canvas.pack(anchor=N)
    elif position_label == "NE":
        canvas.pack(anchor=NE)
    elif position_label == "SW":
        canvas.pack(anchor=SW)
    elif position_label == "S":
        canvas.pack(anchor=S)
    elif position_label == "SE":
        canvas.pack(anchor=SE)

    return canvas


def update_widget_time(label, module_timezone):
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
    global fenetre_block_height, position

    anchor_widget = NW

    if position_label == "NW":
        anchor_widget = NW
    elif position_label == "N":
        anchor_widget = N
    elif position_label == "NE":
        anchor_widget = NE
    elif position_label == "SW":
        anchor_widget = SW
    elif position_label == "S":
        anchor_widget = S
    elif position_label == "SE":
        anchor_widget = SE

    # test heure à Tahiti
    wdget_titre = Label(position[position_label], text=libelle, fg="white", bg='#000000', font=('Arial', 20))
    wdget_titre.pack(pady=0, padx=0, anchor=anchor_widget)
    wdget_heure = Label(position[position_label], text="test", fg="white", bg='#000000', font=('Arial', 50))
    wdget_heure.pack(anchor=anchor_widget)

    wdget_heure.after(1000, lambda: update_widget_time(wdget_heure, module_timezone))

    return wdget_heure





# création des widgets de météo
widget_meteo_1 = add_widget_meteo("SE")
widget_meteo_2 = add_widget_meteo("S")

# création des widgets d'horaire
label_heure_tahiti = add_widget_time("NW", "Heure à tahiti", "Pacific/Tahiti")

# création des widgets d'horaire
label_heure_tahiti = add_widget_time("NE", "Heure à paris", "Europe/Paris")


# affichage de la fenêtre
fenetre.mainloop()

