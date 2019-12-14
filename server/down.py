import json
import sqlite3
from flask import Flask, request
import ast
import requests

def down(ids):
	conn = sqlite3.connect('./db/raspi.db')
	c = conn.cursor()
	query = c.execute('''
	SELECT ip_address 
	FROM raspi
	WHERE id = ?
	''',ids)
	query = tuple(query)
	print(query)
	return query
