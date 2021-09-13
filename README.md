# API Rest Spring (Dev) - RabbitMQ (broker) - Karate (Test) 

En este repositorio se tiene un API Rest construida en [Spring](https://spring.io/) el cuál permite insertar, leer y eliminar mensajes en una 
cola [RabbitMQ](https://www.rabbitmq.com/), por otro lado se tiene las pruebas de aceptación construida en [Karate Framework](https://github.com/intuit/karate) mediante el cual insertamos
mensajes en la cola y leemos mensajes para validar el correcto funcionamiento del API.

## Requisitos previos
Para correr los dos proyectos se requieren las siguientes herramientas

    - Docker
    - JDK 11
    - Gradle
    - Postman (opcional)
    - IDE de su preferencia

## Iniciar RabbitMQ y API Rest
1. Clonar proyecto e ingresar a este directorio via terminal o PowerShell.
2. Ejecutar archivo script.sh para iniciar de forma automática, en caso contrario continuar los pasos 3 en adelante.

3. ingresar al directorio Api_Event_Message y ejecutar el comando 
    `./gradlew clean build` (Mac / linux)

    `gradlew clean build` (windows)

4. Asegurarse que se halla creado el archivo jar en la siguiente ruta:
   **/build/libs/api-event-message-0.0.1-SNAPSHOT.jar**

5. volver a la raiz del repositorio "karate-test-rabbitmq"

6. Ejecutar el comando `docker-compose build && docker-compose up` , en caso de ser exitoso se visualizará asi:
    
<img src="https://github.com/jsierra93/karate-test-rabbitmq/blob/master/img_1.png" title="status up"/>

9. Para realizar pruebas sobre el servicio, importamos el archivo **api-event-message.postman_collection.json**
en postman y ejecutamos la petición Queue-Actuator el cual debe respondernos `{"status": "UP"}`.


## Ejecutar test con Karate Framework
En el directorio **Karate_Api_Test** se encuentra el proyecto con los acceptance test del Api Rest los cuales podemos 
ejecutar de la siguiente forma:

1. Importamos el proyecto  **Karate_Api_Test**.
2. Ejecutamos los test por medio de la clase **EventMessageRunner**, el cual se encargará de ejecutar los escenarios 
diseñados en cada ***.feature**
   `Karate_Api_Test/src/test/java/co/com/jsierra/karateapitest/EventMessageRunner.java`
3. También podemos ejecutar los test por medio de la terminal:

   `./gradlew clean build` (Mac / linux)

   `gradlew clean build` (windows)
4. En la carpeta target podemos visualizar los reportes de la ejecución ya sea exitosa o fallida.

   <img src="https://github.com/jsierra93/karate-test-rabbitmq/blob/master/img.png" title="cucumber report"/>

