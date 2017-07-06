from pprint import pprint
from tkinter import *
from datetime import datetime, time
from pytz import timezone



# la fenêtre
fenetre = Tk()
fenetre.configure(bg="#000000", pady=20, padx=20)
fenetre.attributes("-fullscreen", True)

# définitions des variables destinée à la taille des frames
fenetre.attributes("-fullscreen", True)
fenetre_block_width = ((fenetre.winfo_screenwidth() - 40) / 3)
fenetre_block_height = ((fenetre.winfo_screenheight() -40) / 3)

# les cellules
cellules = []
# les images qui seront utilisées pour certains module
images = []

((fenetre.winfo_screenwidth() -40)/3)


# génération de la grille ici les Frame seront vides mais prendront tout l'espace disponible grâce au weight
for ligne in range(3):
    for colonne in range(3):

        tempo = Frame(fenetre, relief=GROOVE, bg='#000000', width=fenetre_block_width, height=fenetre_block_height) # 000000 quand les test sont terminés
        tempo.grid(row=ligne, column=colonne, sticky=W+E+N+S)
        cellules.append(tempo)


# test heure à Tahiti
labelTempoNW_titre = Label(cellules[2], text="Heure à Tahiti :", fg="white", bg='#000000', font=('Arial', 20))
labelTempoNW_titre.pack(pady=0, padx=0, anchor=NE)
labelTempoNW_heure = Label(cellules[2], text="test", fg="white", bg='#000000', font=('Arial', 50))
labelTempoNW_heure.pack(pady=0, padx=0, anchor=NE)

# test heure à Paris
labelTempoNE_titre = Label(cellules[0], text="Heure à Paris :", fg="white", bg='#000000', font=('Arial', 20))
labelTempoNE_titre.pack(pady=0, padx=0, anchor=NW)
labelTempoNE_heure = Label(cellules[0], text="test", fg="white", bg='#000000', font=('Arial', 50))
labelTempoNE_heure.pack(pady=0, padx=0, anchor=NW)

# test météo



def add_widget_meteo(cellule_position):
    global images, fenetre_block_height, fenetre_block_width

    images.append(PhotoImage(file="./img/weather-icon/cloudy.gif"))
    canvas = Canvas(cellule_position, width=fenetre_block_width, height=fenetre_block_height, bg='#000000', borderwidth=0, highlightcolor='#000000',
                    highlightbackground='#000000')
    canvas.create_image(fenetre_block_width/2 -138, fenetre_block_height/2 -64, anchor=CENTER, image=images[(len(images)-1)])
    canvas.create_text(fenetre_block_width/2 + 10, fenetre_block_height/2 -64, text="21.4°", font="Arial 50", fill="white", anchor=CENTER)
    canvas.create_text(fenetre_block_width/2 +20, fenetre_block_height/2 -124, text="Paris", font="Arial 20", fill="white", anchor=CENTER)
    canvas.create_text(256, 80, text="68%", font="Arial 20", fill="white", anchor=CENTER)
    canvas.pack(pady=0, padx=0, anchor=CENTER)
    return canvas


def update():
    paris_zone = timezone('Europe/Paris')
    paris_time = datetime.now(paris_zone)
    time1 = paris_time.strftime('%H:%M')

    tahiti_zone = timezone('Pacific/Tahiti')
    tahiti_time = datetime.now(tahiti_zone)
    time2 = tahiti_time.strftime('%H:%M')

    labelTempoNW_heure.config(text=time2)
    labelTempoNE_heure.config(text=time1)

    labelTempoNW_heure.after(1000, update)



widget_meteo_1 = add_widget_meteo(cellules[1])
widget_meteo_2 = add_widget_meteo(cellules[5])
update()
fenetre.mainloop()

