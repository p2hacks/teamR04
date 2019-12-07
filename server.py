import json
import uuid
import requests
from flask import Flask

app = Flask(__name__)
server_url = 'http://133.242.224.119:5000/'

@app.route("/")
def root():
	return "ok\n"

@app.route("/mac")
def return_mac():
	return str(uuid.UUID(int=uuid.getnode()).hex[-12:])

data = {
	'tatuo': 'gomisutero',
	'id' : 1,
}
headers = {
	'Content-Type':'application/json',
}

if __name__ == '__main__':
	req = urllib.request.Request(server_url, json.dumps(data).encode(), headers)

	app.run(host='0.0.0.0')
