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


labelTempoNW_titre = Label(cellules[2], text="Heure à Tahiti :", fg="white", bg='#000000', font=('Calibri', 20))
labelTempoNW_titre.pack(pady=5, padx=5, anchor=NE)
labelTempoNW_heure = Label(cellules[2], text="test", fg="white", bg='#000000', font=('Calibri', 50))
labelTempoNW_heure.pack(pady=0, padx=5, anchor=NE)

labelTempoNE_titre = Label(cellules[0], text="Heure à Paris :", fg="white", bg='#000000', font=('Calibri', 20))
labelTempoNE_titre.pack(pady=5, padx=5, anchor=NW)
labelTempoNE_heure = Label(cellules[0], text="test", fg="white", bg='#000000', font=('Calibri', 50))
labelTempoNE_heure.pack(pady=0, padx=5, anchor=NW)

def update():
    paris_zone = timezone('Europe/Paris')
    paris_time = datetime.now(paris_zone)
    time1 = paris_time.strftime('%H:%M:%S')

    tahiti_zone = timezone('Pacific/Tahiti')
    tahiti_time = datetime.now(tahiti_zone)
    time2 = tahiti_time.strftime('%H:%M:%S')

    labelTempoNW_heure.config(text=time2)
    labelTempoNE_heure.config(text=time1)

    labelTempoNW_heure.after(1000, update)



update()
fenetre.mainloop()

