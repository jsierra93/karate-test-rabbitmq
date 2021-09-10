function init(){
var env = karate.env;
config =  {
    host: '127.0.0.1',
    port: 5672,
    queueName: 'event-message',
    username: 'dev1234',
    password: 'dev1234',
}

 if (!env) {
    config.host = '127.0.0.1';
  }
  if (env == 'qa') {
      config.host = '180.10.20.1';
    } else if (env == 'pdn') {
      config.host = '182.10.20.1';
    }

karate.configure('connectTimeout', 5000);
karate.configure('readTimeout', 5000);

karate.log('get variable from karate-config => ', config)
return config;
}
