import sqlite3
from flask import Flask, request, jsonify
import ast
import func
import json

app = Flask(__name__)
@app.route('/set_info', methods=['POST'])
def set_from_hard():
	json_dict = json.loads(request.data)
	json_dict = ast.literal_eval(json_dict)
	conn = sqlite3.connect('./db/raspi.db')
	c = conn.cursor()
	#If there's not table
	try:
		print('table is none. created new table')
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
	''')
	query = tuple(query)
	data = []
	for i in query:
		data.append({'id' : i[0],'latitude' : i[1],'longitude' : i[2]})
	data = json.dumps(data)
	conn.close()
	return data 

@app.route('/down',methods=['POST'])
def down_servo():
	ids = tuple(request.get_data())
	print(ids)
	conn = sqlite3.connect('./db/raspi.db')
	c = conn.cursor()
	query = func.down(c, ids)
	conn.close()
	return query

@app.route('/valus',methods=['GET'])
def valus():
	conn  = sqlite3.connect('./db/raspi.db')
	c = conn.cursor()
	targets = c.execute('''
	SELECT id
	FROM raspi
	WHERE status = 'wait'
	''')
	targets = tuple(targets)
	print(targets)
	for i,target in enumerate(targets):
		func.down(c, target)
	conn.commit()
	conn.close()
	return jsonify(status='false')

@app.route('/status', methods=['POST'])
def return_status_to_hard():
	conn = sqlite3.connect('./db/raspi.db')
	c = conn.cursor()
	mac_address = request.get_data().decode('utf-8')
	mac = (mac_address,)
	res = func.return_status(c, mac)
	#return jsonify(status='go')
	try:
		data = res[0]
		return jsonify(status=res[0])
	except:
		return jsonify(status='wait')

@app.route('/set', methods=['GET'])
def set():
	#json_dict = json.loads(request.data)
	#json_dict = ast.literal_eval(json_dict)
	conn = sqlite3.connect('./db/raspi.db')
	c = conn.cursor()
	#If there's not table
	try:
		func.create_table(c)
	except:
		pass
	func.insert_data(c)
	conn.commit()
	conn.close()
	return 'POST'


if __name__ == '__main__':
	app.run(host='0.0.0.0')
