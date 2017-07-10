from picamera import PiCamera

camera = PiCamera()
camera.hflip = True
camera.vflip = True
camera.resolution = (320, 240)

def start_preview(left, top):
    global camera
    camera.start_preview(fullscreen=False, window=(left, top, 640, 480))
    
def take_picture():
    camera.capture("img/tempo_photo/photo.jpg")
    camera.stop_preview()
    return ("img/tempo_photo/photo.jpg")

