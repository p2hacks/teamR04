from urllib.parse import urlparse
import mysql.connector

url = urlparse('mysql://user:pass@localhost:3306/raspi_info')

conn = mysql.connector.connect(
	host = url.hostname or 'localhost',
	port = url.port or 3306,
	user = url.username or 'root',
	password = url.password or '',
	database = url.path[1:],
)
