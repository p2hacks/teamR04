import requests
import uuid
import socket
import json

SERVER_URL = 'http://133.242.224.119:5000/'

def get_mac_address():
	return str(uuid.UUID(int=uuid.getnode()).hex[-12:]) 

def get_ip_address():
	return str(socket.gethostbyname(socket.gethostname()))

def make_json_data():
	data = json.dumps({'mac_address' : get_mac_address(), 'ip_address' : get_ip_address(), 'latitude' : '11111.0', 'longitude' : '22222.0'})
	return data

def send_json():
	head = {'content-type': 'application/json'}
	res = requests.post(SERVER_URL, data=json.dumps(make_json_data()), headers=head)
