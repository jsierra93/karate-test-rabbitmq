function fn(){

config =  {
    urlQueue: 'localhost',
    port: 5672,
    nameQueue: 'queue-demo',
    username: 'dev1234',
    password: 'dev1234'
}

karate.log('get variable from karate-config ', config)

return config;
}
