import json
import sqlite3
from flask import Flask, request
import ast
from func import create_table, insert_data, update_data, exist_data, delete

app = Flask(__name__)
@app.route('/', methods=['GET','POST'])
def root():
	if request.method == 'GET':
		#TODO:send data to APP
		conn = sqlite3.connect('./db/raspi.db')
		c = conn.cursor()
		query = c.execute('''
		SELECT id, latituede, longitude 
		FROM raspi
		WHERE status = wait
		''')
		json.dump(query)
		
		return 'GET'
	else:
		json_dict = json.loads(request.data)
		json_dict = ast.literal_eval(json_dict)
		conn = sqlite3.connect('./db/raspi.db')
		c = conn.cursor()
		#If there's not table
		try:create_table(c)
		except:print('already exist table')
		if exist_data(c, json_dict):
			update_data(c, json_dict)
		else:
			insert_data(c, json_dict)
		conn.commit()
		conn.close()
		return 'POST'

if __name__ == '__main__':
	app.run(host='0.0.0.0')
