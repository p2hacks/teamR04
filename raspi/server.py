import json
import uuid
import requests
from flask import Flask

app = Flask(__name__)
server_url = 'http://133.242.224.119:5000/'

@app.route("/")
def root():
	return "ok\n"

def get_mac_address():
	return str(uuid.UUID(int=uuid.getnode()).hex[-12:])

if __name__ == '__main__':
	res = requests.post(server_url, data)
	print(res)
	data = json.dump({'mac_address': get_mac_address(), ''})
	app.run(host='0.0.0.0')
