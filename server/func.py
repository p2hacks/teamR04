import json
import sqlite3
from flask import Flask, request
import ast

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
	# count the mac address 
	cnt = c.execute('SELECT COUNT(mac_address = ?) FROM raspi',info)
	if tuple(cnt)[0][0]:
		return True
	return False

#all delete
def delete(c, json_dict):
	c.execute('DELETE FROM raspi')


