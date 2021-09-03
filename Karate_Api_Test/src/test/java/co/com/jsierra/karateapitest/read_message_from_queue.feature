Feature: Leer mensajes de la cola RabbitMQ desde el api rest

  Background:
    * def config = { host: '#(host)' , port: '#(port)', username: '#(username)', password: '#(password)', queueName: '#(queueName)'}
    * url 'http://localhost:9001/v1/api'
  Scenario: Enviar petición get al api rest para leer mensaje de la cola
    * def event = {"application":"demo","type":"success","severity":"low","message":"conection success karate"}
    * def sendRabbit = Java.type('co.com.jsierra.karateapitest.utils.SendRabbit')
    * def queue = new sendRabbit(config)
   # karate.toBean permite convertir variables js a modelos java
   * def payload = karate.toBean(event, 'co.com.jsierra.karateapitest.models.Event')
    Given def msg = queue.send( config.queueName, payload)
    And path '/queue'
    When method get
    Then status 200
    And match response == event