package co.com.jsierra.karateapitest.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class ConectionRabbit {
    public static Connection connectionRabbit(Map<String, Object> config) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        String host = (String) config.get("host");
        int port = (int) config.get("port");
        String username = (String) config.get("username");
        String password = (String) config.get("password");

        factory.setHost(host);
        factory.setPort(port);
        factory.setUsername(username);
        factory.setPassword(password);
        factory.setConnectionTimeout(5000);

        return factory.newConnection();
    }

    public static Channel channelRabbit(Map<String, Object> config) throws IOException, TimeoutException {
        return connectionRabbit(config).createChannel();
    }
}
