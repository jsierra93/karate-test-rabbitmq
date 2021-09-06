Feature: Read messages from RabbitMQ queue from API Rest

  Background:
    * def config = { host: '#(host)' , port: '#(port)', username: '#(username)', password: '#(password)', queueName: '#(queueName)'}
    * url 'http://localhost:9001/v1/api'

  Scenario: Send request GET to rest api to read message from RabbitMQ queue
    * def event = {"application":"demo","type":"success","severity":"low","message":"test send message from karate"}
    * def sendRabbit = Java.type('co.com.jsierra.karateapitest.utils.SendRabbit')
   # karate.toBean permite convertir variables js a modelos java
    * def payload = karate.toBean(event, 'co.com.jsierra.karateapitest.models.Event')
    Given sendRabbit.send( config , payload)
    And path '/queue'
    When method get
    Then status 200
    And match response == event