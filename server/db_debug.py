import sqlite3

print('---')
conn = sqlite3.connect('./db/raspi.db')
c = conn.cursor()
for row in c.execute('SELECT * FROM raspi ORDER BY id'):
	print(row)
conn.close()
print('---')
