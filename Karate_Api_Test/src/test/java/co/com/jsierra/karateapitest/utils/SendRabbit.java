package co.com.jsierra.karateapitest.utils;

import co.com.jsierra.karateapitest.models.Event;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class SendRabbit {
    private static final Logger logger = LoggerFactory.getLogger(SendRabbit.class);
    private static ConnectionFactory factory = new ConnectionFactory();

    public SendRabbit(Map<String, Object> config) {
        logger.info(config.toString());
        String host = (String) config.get("host");
        int port = (int) config.get("port");
        String username = (String) config.get("username");
        String password = (String) config.get("password");

        factory.setHost(host);
        factory.setPort(port);
        factory.setUsername(username);
        factory.setPassword(password);
    }


    public String send(String queueName, Event message) throws IOException, TimeoutException {
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        byte[] data = new ObjectMapper().writeValueAsBytes(message);
        channel.queueDeclare(queueName, false, false, true, null);
        channel.basicPublish("", queueName, null, data);
        logger.info("Enviando mensaje: {}", message);

        channel.close();
        connection.close();
        return message.toString();
    }
}