import time
import wiringpi as w

w.wiringPiSetup()
SERVO_PIN = 18
w.pinMode(SERVO_PIN , 2)
w.pwmSetMode(0)
w.pwmSetRange(2000)
w.pwmSetClock(192)

def down():
    i = 30
    while i <= 270:
        w.pwmWrite(SERVO_PIN,i)
        i += 10
        time.sleep(0.3)

def release():
    i = 270
    while i >= 30:
        w.pwmWrite(SERVO_PIN,i)
        i -= 10
        time.sleep(0.3)

