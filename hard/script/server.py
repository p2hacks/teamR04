#!/usr/bin/python3
from time import sleep
import sys
sys.path.append('/home/mouse/.local/lib/python3/site-packages')
from flask import Flask
import send
import servo

app = Flask(__name__)

@app.route("/")
def root():
	return "ok\n"

@app.route("/down", methods=['GET'])
def down():
	return servo.braker_down()

@app.route("/get_data", methods=['GET'])
def get_data():
	return send.make_json_data()

if __name__ == '__main__':
	cnt = 0
	while cnt < 5:
		try: 
			send.send_json()
			cnt += 5
		except:
			sleep(1)
			cnt += 1
			print('try!!')
	app.run(host='0.0.0.0')
