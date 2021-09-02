# karate-test-rabbitmq
Proyecto de ejemplo sobre el consumo de Broker RabbitMQ con Karate Framework.


Requisitos:

    - Docker
    - JDK 11
    - Gradle
    - Postman (opcional)

1. Clonar proyecto
2. ingresar al directorio Api_Event_Message y ejecutar el comando
    ./gradlew clean build (Mac / linux)

    gradlew clean build (windows)

3. Asegurarse que se halla creado el archivo jar en la siguiente ruta:
   /build/libs/api-event-message-0.0.1-SNAPSHOT.jar

4. volver a la raiz del repositorio "karate-test-rabbitmq"

5. Ejecutar el comando docker-compose up, en caso de ser exitoso se visualizará asi:
    pantallazo spring

6. Para realizar pruebas sobre el servicio, importamos el archivo api-event-message.postman_collection.json
en postman y ejecutamos la petición Queue-Actuator el cual debe respondernos "UP".

7. ------------------ En construcción ----------