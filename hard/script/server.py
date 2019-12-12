#!/usr/bin/python3
from time import sleep
import sys
sys.path.append('/home/mouse/.local/lib/python3/site-packages')
from flask import Flask
import send

app = Flask(__name__)

@app.route("/")
def root():
	return "ok\n"

@app.route("/down", methods=['GET'])
def down():
	print('down!!! servo!!!')
	return 'done'

if __name__ == '__main__':
	try send.send_json()
	app.run(host='0.0.0.0')
