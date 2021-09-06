package co.com.jsierra.karateapitest.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class ConectionRabbit {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConectionRabbit.class);

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

        return factory.newConnection();
    }

    public static Channel channelRabbit(Map<String, Object> config) throws IOException, TimeoutException {
        return connectionRabbit(config).createChannel();
    }
}
