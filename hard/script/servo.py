import time
import wiringpi as w

w.wiringPiSetup()
SERVO_PIN = 1
w.pinMode(SERVO_PIN , 2)
w.pwmSetMode(0)
w.pwmSetRange(2000)
w.pwmSetClock(192)

def down():
    i = 50
    while i <= 270:
        w.pwmWrite(SERVO_PIN,i)
        print(i)
        i += 5
        time.sleep(0.05)

def release():
    i = 270
    while i >= 50:
        w.pwmWrite(SERVO_PIN,i)
        print(i)
        i -= 5
        time.sleep(0.05)

