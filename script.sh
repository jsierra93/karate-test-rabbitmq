#!/bin/bash
cd Api_Event_Message
echo "Construyendo proyecto ..."
chmod +x gradlew
./gradlew clean build
cd ..
echo "Creando contenedores RabbitMQ y API Rest ..."
docker-compose down && docker-compose build && docker-compose up -d
echo "Inicializando servicios ..."
for ((i = 0; i < 20; i++)); do
  sleep 1
  echo "..."
done
echo "Actuator Health: "
curl --request GET -sL \
  --url 'http://localhost:9001/actuator/health'
echo "
Servicios desplegados exitosamente ..."