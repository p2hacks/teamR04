from flask import Flask, request

app = Flask(__name__)

@app.route('/', methods=['GET','POST'])
def root():
	if request.method == 'GET':
		return 'kotti ha GET'
	else:
		print(request.get_data())
		return 'kotti ha POST'

if __name__ == '__main__':
	app.run(host='0.0.0.0')
