import json
import sqlite3
from flask import Flask, request
import ast
import func

SERVER_URL = 'get_json'

app = Flask(__name__)
@app.route('/set_info', methods=['POST'])
def set_from_hard():
	json_dict = json.loads(request.data)
	json_dict = ast.literal_eval(json_dict)
	conn = sqlite3.connect('./db/raspi.db')
	c = conn.cursor()
	#If there's not table
	try:
		func.create_table(c)
	except:
		pass
	if func.exist_data(c, json_dict):
		func.update_data(c, json_dict)
	else:
		func.insert_data(c, json_dict)
	conn.commit()
	conn.close()
	return 'POST'

@app.route('/get_json',methods=['GET'])
def get_from_app():
	conn = sqlite3.connect('./db/raspi.db')
	c = conn.cursor()
	query = c.execute('''
	SELECT id, latitude, longitude 
	FROM raspi
	WHERE status = 'wait'
	''')
	query = tuple(query)
	#print(query)
	head = {'content-type' : 'application/json'}
		
	data = []
	for i in query:
		data.append({'id' : i[0],'latitude' : i[1],'longitude' : i[2]})
	data = json.dumps(data)
	return data 

@app.route('/balse',methods=['GET'])
def balse():
	conn  = sqlite3.connect('./db/raspi.db')

#@app.route('/'



if __name__ == '__main__':
	app.run(host='0.0.0.0')
