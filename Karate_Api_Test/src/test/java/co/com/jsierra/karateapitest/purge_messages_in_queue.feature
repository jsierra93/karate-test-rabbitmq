Feature: Purge messages from the RabbitMQ queue from the API Rest

  Background:
    * def config = { host: '#(host)' , port: '#(port)', username: '#(username)', password: '#(password)', queueName: '#(queueName)'}
    * url 'http://localhost:9001/v1/api'

  Scenario: Send request DELETE to API Rest to purge queue
    * def utilsRabbit = Java.type('co.com.jsierra.karateapitest.utils.UtilsRabbit')
    Given path '/queue'
    When method delete
    Then status 200
    * def nMsg = utilsRabbit.count(config)
    And match nMsg == 0