import json
import sqlite3
from flask import Flask, request
import sys
import ast

def create_table(c):
	c.execute('''CREATE TABLE raspi(
	id INTEGER PRIMARY KEY, mac_address TEXT, ip_address TEXT,
	latitude TEXT, longitude TEXT)''')

def insert_data(c, json_dict):
	info = (json_dict['mac_address'],json_dict['ip_address'],json_dict['latitude'],json_dict['longitude'])
	print('info>>>{}'.format(info))
c.execute('INSERT INTO raspi(mac_address, ip_address, latitude, longitude) VALUES(?,?,?,?)',info)

app = Flask(__name__)
@app.route('/', methods=['GET','POST'])
def root():
	if request.method == 'GET':
		#TODO:send data to APP
		return 'kotti ha GET'
	else:
		json_dict = json.loads(request.data)
		json_dict = ast.literal_eval(json_dict)
		print('json_dict>>>{}, {}'.format(json_dict, type(json_dict)))
		conn = sqlite3.connect('./db/raspi.db')
		c = conn.cursor()
		#TODO:WHERE EXIST id -> update location
		#     else           -> isnert all data
		insert_data(c,json_dict)
		conn.commit()
		conn.close()
		
		return 'kotti ha POST'

if __name__ == '__main__':
	app.run(host='0.0.0.0')
