from picamera import PiCamera

camera = PiCamera()


def take_picture():
    global camera
    camera.resolution = (320, 240)
    camera.start_preview()
    camera.capture("img/tempo_photo/photo.jpg")
    camera.stop_preview()