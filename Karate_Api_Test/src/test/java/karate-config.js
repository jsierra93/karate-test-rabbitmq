function init(){

config =  {
    host: '127.0.0.1',
    port: 5672,
    queueName: 'event-message',
    username: 'dev1234',
    password: 'dev1234',
}

karate.log('get variable from karate-config => ', config)

return config;
}
