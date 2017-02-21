build:
	mvn clean install -P docker-build -DskipTests
	docker-compose build

up:
	docker-compose up -d

run-env:
	docker-compose up -d frontend elasticsearch mongo redis

stop:
	docker-compose stop -t 3
down: stop
	docker-compose down

mongo-connect:
	docker-compose exec mongo mongo timetrack
mongo-drop-database:
	docker-compose exec mongo mongo timetrack --eval "db.dropDatabase()"

version:
	@read -p "Enter version: " version; \
	mvn versions:set -DnewVersion=$$version