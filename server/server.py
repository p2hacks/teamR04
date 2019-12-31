import sqlite3
from flask import Flask, request, jsonify
import ast
import func
import json
import db

app = Flask(__name__)
# arg: mac, latitude, longitude
# if mac id exist already: create new column
# else update latitude and longitude
@app.route('/set_info', methods=['POST'])
def set_from_hard():
	json_dict = json.loads(request.data)
	json_dict = ast.literal_eval(json_dict)
	num = db.get_id_for_mac(json_dict['mac_address'])
	if num == 0:
		db.create_new_column(json_dict)
	else:
		db.update_info(json_dict, num)
	print(json_dict)
	return 'ok'

# return table
@app.route('/get_json',methods=['GET'])
def get_from_app():
	return db.get_all()

# arg: int id
# down raspi from id
@app.route('/down',methods=['POST'])
def down_servo():
	ids = int(request.get_data())
	db.set_status_down(ids)
	return 'down'

# down all raspi
@app.route('/valus',methods=['GET'])
def valus():
	db.set_status_down(0)
	return 'valus'

# arg: mac
# return status to mac
@app.route('/status', methods=['POST'])
def return_status_to_hard():
	mac_address = request.get_data().decode('utf-8')
	res = db.get_status_for_mac(mac_address)
	return jsonify(status=res)

@app.route('/set', methods=['GET'])
def set():
	pass

if __name__ == '__main__':
	app.run(host='0.0.0.0')
