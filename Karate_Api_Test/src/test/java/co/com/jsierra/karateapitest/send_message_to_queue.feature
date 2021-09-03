Feature: Enviar mensajes a la cola RabbitMQ desde el api rest
  Background:
    * def config = { host: '#(host)' , port: '#(port)', username: '#(username)', password: '#(password)', queueName: '#(queueName)'}
  Scenario: Enviar petición post al api rest para escribir mensaje en la cola
