from tkinter import *
from datetime import datetime, time
from pytz import timezone



# la fenêtre
fenetre = Tk()
fenetre.configure(bg="#ffffff")

# les cellules
cellules = []
# fenêtre en plein écran (voir ce qui fonctionne sur raspberry)
fenetre.attributes("-fullscreen", True)
# this should make Esc exit fullscrreen, but doesn't seem to work..
#fenetre.bind('', fenetre.attributes("-fullscreen", False))


# génération de la grille ici les Frame seront vides mais prendront tout l'espace disponible grâce au weight
for ligne in range(3):
    for colonne in range(3):
        Grid.rowconfigure(fenetre, colonne, weight=1)
        Grid.columnconfigure(fenetre, ligne, weight=1)

        tempo = Frame(fenetre, relief=GROOVE, bg='#000000') # 000000 quand les test sont terminés

        tempo.grid(row=ligne, column=colonne, sticky=W+E+N+S)

        cellules.append(tempo)





# test heure à Tahiti
labelTempoNW_titre = Label(cellules[2], text="Heure à Tahiti :", fg="white", bg='#000000', font=('Arial', 20))
labelTempoNW_titre.pack(pady=5, padx=5, anchor=NE)
labelTempoNW_heure = Label(cellules[2], text="test", fg="white", bg='#000000', font=('Arial', 50))
labelTempoNW_heure.pack(pady=0, padx=5, anchor=NE)

# test heure à Paris
labelTempoNE_titre = Label(cellules[0], text="Heure à Paris :", fg="white", bg='#000000', font=('Arial', 20))
labelTempoNE_titre.pack(pady=5, padx=5, anchor=NW)
labelTempoNE_heure = Label(cellules[0], text="test", fg="white", bg='#000000', font=('Arial', 50))
labelTempoNE_heure.pack(pady=0, padx=5, anchor=NW)

# test météo



def add_widget_meteo(cellule_position):
    global photo
    photo = PhotoImage(file="./img/weather-icon/cloudy.gif")
    canvas = Canvas(cellule_position, width=256, height=128, bg='#000000', borderwidth=0, highlightcolor='#000000',
                    highlightbackground='#000000')
    canvas.create_image(0, 0, anchor=NW, image=photo)
    temp = canvas.create_text(140, 20, text="21.4°", font="Arial 50", fill="white", anchor=NW)
    temp = canvas.create_text(140, 80, text="Paris", font="Arial 20", fill="white", anchor=NW)
    temp = canvas.create_text(256, 80, text="68%", font="Arial 20", fill="white", anchor=NE)
    canvas.pack(pady=0, padx=0, anchor=N)



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

add_widget_meteo(cellules[1])
update()
fenetre.mainloop()

