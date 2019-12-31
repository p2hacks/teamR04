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
DEBUG_MODE = True

#conecction is server to db
def set_debug_mode(flag):
	DEBUG_MODE = flag
	print('debug mode = {}'.format(DEBUG_MODE))

def show_all():
	cur.execute("select * from raspies;")
	print('--- columns ---')
	for row in cur.fetchall():
		print(row[0],row[1],row[2],row[3],row[4])
	print('---------------')

def get_all():
	cur.execute("select id, latitude, longitude from raspies")
	res = cur.fetchall()
	json_array = []
	for i in res:
		json_array.append({'id': i[0], 'latitude': i[1], 'longitude': i[2]})
	json_array = json.dumps(json_array)
	return json_array

def get_id_for_mac(mac_address):
	cur.execute("select id from raspies where mac = %s", (mac_address,))
	res = cur.fetchall()
	if DEBUG_MODE: print(len(res))
	if len(res) == 0:
		return 0
	else: return res[0]

def update_info(info, id_num):
	try:
		cur.execute("update raspies set latitude = %s, longitude = %s, status=%s where id = %s;",info['latitude'], info['longitude'], 'wait', id_num)
		conn.commit()
	except:
		conn.rollback()
	if DEBUG_MODE: print('update info: id={}, latitude={}, longitude={}'.format(id_num, info['latitude'],info['longitude']))

def create_new_column(info):
	cur.execute("select MAX(id) from raspies")
	res = cur.fetchone()
	try:
		cur.execute("insert into raspies VALUES (%s, %s, 'wait', %s, %s)", (res[0]+1, info['mac_address'],info['latitude'], info['longitude'],))
		conn.commit()
	except:
		conn.rollback()
	if DEBUG_MODE: print('insert data! {}'.format(info))

def set_status_down(num):
	try:
		if num == 0:
			cur.execute("update raspies set status='go'")
		else:
			cur.execute("update raspies set status='go' where id=%s", (num,))
		conn.commit()
	except:
		conn.rollback()

def get_status_for_mac(mac):
	cur.execute("select status from raspies where mac = %s", (mac,))
	res = cur.fetchone()
	if res == None:
		return False
	else:
		return res[0]

def debug_add():
	cur.execute("insert into raspies VALUES (3, %s, 'wait' , '40', '50')", ('a3',))

def debug_delete_all():
	cur.execute("delete from raspies;")

def debug_delete(n):
	cur.execute("delete from raspies where id=%s", (n,))
