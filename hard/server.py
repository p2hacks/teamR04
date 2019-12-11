from time import sleep
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
	send.send_json()
	app.run(host='0.0.0.0')
