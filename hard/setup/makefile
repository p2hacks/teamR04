SCRIPT = ../script/
SERVICE = gps-client.service

.PHONY: install
install: $(SCRIPT) $(SERVICE)
	@sudo cp -r $(SCRIPT) /opt/
	@sudo chmod 0755 /opt/script/*.py
	@sudo cp $(SERVICE) /etc/systemd/system/
	@echo setup done

.PHONY: uninstall
uninstall: 
	sudo rm /opt/$(SCRIPT)

.PHONY: check
check: 
	sudo systemctl list-unit-files --type=service | grep gps

.PHONY: enable
enable:
	sudo systemctl enable gps-client

.PHONY: start
start:
	sudo systemctl start gps-client

.PHONY: stop
stop:
	sudo systemctl stop gps-client

.PHONY: restart
restart:
	sudo systemctl restart gps-client

.PHONY: reload
reload:
	sudo systemctl daemon-reload

.PHONY: status
status:
	sudo systemctl status gps-client
