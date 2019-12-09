import json
import uuid
import requests
import socket
from flask import Flask

app = Flask(__name__)
SERVER_URL = 'http://133.242.224.119:5000/'

@app.route("/")
def root():
	return "ok\n"

def get_mac_address():
	return str(uuid.UUID(int=uuid.getnode()).hex[-12:])

def get_ip_address():
	return str(socket.gethostbyname(socket.gethostname()))

if __name__ == '__main__':
	data = json.dumps({'mac_address' : get_mac_address(), 'ip_address' : get_ip_address(), 'latitude' : 11111, 'longtude' : 22222})
	res = requests.post(SERVER_URL, json.loads(data))
	app.run(host='0.0.0.0')
