package co.com.jsierra.karateapitest.utils;

import co.com.jsierra.karateapitest.models.Event;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class ReceiveRabbit {
    private static final Logger LOGGER = LoggerFactory.getLogger(SendRabbit.class);
    private static ConnectionFactory FACTORY = new ConnectionFactory();

    public ReceiveRabbit(Map<String, Object> config) {
        LOGGER.info(config.toString());
        String host = (String) config.get("host");
        int port = (int) config.get("port");
        String username = (String) config.get("username");
        String password = (String) config.get("password");

        FACTORY.setHost(host);
        FACTORY.setPort(port);
        FACTORY.setUsername(username);
        FACTORY.setPassword(password);

    }

    public String readMessage(String queueName) throws IOException, TimeoutException {
        Connection connection = FACTORY.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(queueName, false, false, false, null);
        GetResponse message = channel.basicGet(queueName, true);
        ObjectMapper mapper = new ObjectMapper();
        Event event = mapper.readValue(message.getBody(), Event.class);
        LOGGER.info("Leyendo mensaje : {}", event);

        channel.close();
        connection.close();
        return event.toString();
    }
}