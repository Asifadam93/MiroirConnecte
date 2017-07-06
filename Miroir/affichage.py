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

((fenetre.winfo_screenwidth() -40)/3)




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


# test heure à Tahiti
labelTempoNW_titre = Label(cellules[2], text="Heure à Tahiti :", fg="white", bg='#000000', font=('Arial', 20))
labelTempoNW_titre.pack(pady=0, padx=0, anchor=NE)
labelTempoNW_heure = Label(cellules[2], text="test", fg="white", bg='#000000', font=('Arial', 50))
labelTempoNW_heure.pack(anchor=NE)

# test heure à Paris
labelTempoNE_titre = Label(cellules[0], text="Heure à Paris :", fg="white", bg='#000000', font=('Arial', 20))
labelTempoNE_titre.pack(pady=0, padx=0, anchor=NW)
labelTempoNE_heure = Label(cellules[0], text="test", fg="white", bg='#000000', font=('Arial', 50))
labelTempoNE_heure.pack(pady=0, padx=0, anchor=NW)

# test météo



def add_widget_meteo(cellule_position):
    global images, fenetre_block_height

    images.append(PhotoImage(file="./img/weather-icon/sunny.gif"))
    canvas = Canvas(cellule_position, width=256, height=128, bg='#000000', borderwidth=0, highlightcolor='#000000',
                    highlightbackground='#000000')
    canvas.create_image(0, 64, anchor=W, image=images[(len(images)-1)])

    canvas.create_text(128, 44, text="21.4°", font="Arial 30", fill="white", anchor=W)
    canvas.create_text(128, 74, text="68%", font="Arial 15", fill="white", anchor=W)
    canvas.create_text(128, 94, text="Paris", font="Arial 15", fill="white", anchor=W)
    canvas.pack(anchor=S)
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


widget_meteo_2 = add_widget_meteo(cellules[7])

update()
fenetre.mainloop()

