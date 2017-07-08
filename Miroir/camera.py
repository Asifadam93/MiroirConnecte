from picamera import PiCamera

camera = PiCamera()
camera.hflip = True
camera.vflip = True
camera.resolution = (320, 240)

def take_picture():
    global camera
    camera.start_preview(fullscreen=False, window = (100, 20, 640, 480))
    camera.capture("img/tempo_photo/photo.jpg")
    camera.stop_preview()