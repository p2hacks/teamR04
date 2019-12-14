import json
import sqlite3
from flask import Flask, request
import ast
import requests

def create_table(c):
	c.execute('''
	CREATE TABLE raspi(
	id INTEGER PRIMARY KEY, mac_address TEXT, ip_address TEXT,
	latitude TEXT, longitude TEXT, status TEXT
	)''')

def insert_data(c, json_dict):
	info = (json_dict['mac_address'],json_dict['ip_address'],json_dict['latitude'],json_dict['longitude'],'wait')
	c.execute('''
		INSERT INTO raspi(mac_address, ip_address, latitude, longitude, status) 
		VALUES(?,?,?,?,?)
		''',info)
	print('insert')

def update_data(c, json_dict):
	info = (json_dict['latitude'],json_dict['longitude'],json_dict['mac_address'])
	c.execute('''
		UPDATE raspi
		SET latitude = ?, longitude = ?
		WHERE mac_address = ?
		''',info)
	print('update')

def exist_data(c, json_dict):
	info = (json_dict['mac_address'],)
	cnt = c.execute('SELECT COUNT(CASE WHEN mac_address = ? THEN "1" ELSE NULL END) FROM raspi;',info)
	cnt = tuple(cnt)[0][0]
	print('cnt>>{}'.format(cnt))
	return True if cnt else False

#all delete
def delete(c, json_dict):
	c.execute('DELETE FROM raspi')


