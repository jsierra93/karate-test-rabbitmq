Feature: Send messages to RabbitMQ queue from API Rest

  Background:
    * def config = { host: '#(host)' , port: '#(port)', username: '#(username)', password: '#(password)', queueName: '#(queueName)'}
    * url 'http://localhost:9001/v1/api'

  Scenario: Send request POST to the API Rest to write message in the queue
    * def event = {"application":"demo","type":"success","severity":"low","message":"test send message from karate"}
    * def utilsRabbit = Java.type('co.com.jsierra.karateapitest.utils.UtilsRabbit')
    Given request event
    And path '/queue'
    When method post
    Then status 200
    * def msg = utilsRabbit.receive(config)
    And match response == karate.toJson(msg)