#!/usr/bin/python3
from time import sleep
import send
import servo

def main():
	try:
		res = send.get_status()
		print(res.json()['status'])
		if res.json()['status'] == 'go':
			servo.down()
	except:
		sleep(1)
		continue

if __name__ == '__main__':
	servo.release()
	cnt = 0
	while cnt < 5:
		try: 
			send.send_json()
			cnt += 5
		except:
			sleep(1)
			cnt += 1
			print('try!!')
	main()
