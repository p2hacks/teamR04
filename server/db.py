import mysql.connector
import json

conn = mysql.connector.connect(
	user = 'awa',
	password = 'mouse',
	port  = '3306',
	host = 'localhost',
	database = 'emp_system'
)

cur = conn.cursor()
DEBUG_MODE = False

def set_debug_mode(flag):
	DEBUG_MODE = flag
	print('debug mode = {}'.format(DEBUG_MODE))

def print_all():
	cur.execute("select * from raspies;")
	print('--- columns ---')
	for row in cur.fetchall():
		print(row[0],row[1],row[2],row[3],row[4])
	print('---------------')

def get_id_for_mac(mac_address):
	cur.execute("select id from raspies where mac = %s", (mac_address,))
	res = cur.fetchall()
	if DEBUG_MODE: print(len(res))
	if len(res) == 0:
		return 0
	else: return res[0]

def update_info(info, id_num):
	cur.execute("update raspies set latitude = %s, longitude = %s where id = %s;",info['latitude'], info['longitude'], id_num)
	if DEBUG_MODE: print('update info: id={}, latitude={}, longitude={}'.format(id_num, info['latitude'],info['longitude']))

def create_new_column(info):
	cur.execute("select MAX(id) from raspies")
	res = cur.fetchone()
	cur.execute("insert into raspies VALUES (%s, %s, 'wait', %s, %s)", (res[0], info[0],info[1], info[2],))
	if DEBUG_MODE: print('insert data! {}'.format(info))

def show_all():
	cur.execute("select * from raspies;")
	res = cur.fetchall()
	print('-'*15)
	for i in res:
		print(i)
	print('-'*15)

def debug_add():
	cur.execute("insert into raspies VALUES (3, %s, 'wait' , '40', '50')", ('a3',))

