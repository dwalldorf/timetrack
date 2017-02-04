build:
	mvn clean install -P docker-build -DskipTests
	docker-compose build

up:
	docker-compose up -d

run-env: up
	docker-compose stop -t 1 backend

down:
	docker-compose stop -t 3
	docker-compose down

mongo-connect:
	docker-compose exec mongo mongo timetrack
mongo-drop-database:
	docker-compose exec mongo mongo timetrack --eval "db.dropDatabase()"