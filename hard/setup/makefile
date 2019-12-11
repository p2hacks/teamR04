SCRIPT = ../script/server.py ../script/send.py
SERVICE = gps-client.service

.PHONY: install
install: $(SCRIPT) $(SERVICE)
	@cp $(SCRIPT) /opt
	@cp $(SERVICE) /etc/systemd/system/
	@echo setup done

.PHONY: check
check: 
	systemctl list-unit-files --type=service | grep gps

.PHONY: enable
enable:
	systemctl enable gps-client

.PHONY: start
start:
	systemctl start gps-client

.PHONY: status
status:
	systemctl status gps-client
