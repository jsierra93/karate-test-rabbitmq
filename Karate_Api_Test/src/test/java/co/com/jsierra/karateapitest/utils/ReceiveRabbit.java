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

    public static Event receive(Map<String, Object> config) throws IOException, TimeoutException {
        LOGGER.info(config.toString());
        String host = (String) config.get("host");
        int port = (int) config.get("port");
        String username = (String) config.get("username");
        String password = (String) config.get("password");
        String queueName = (String) config.get("queueName");

        FACTORY.setHost(host);
        FACTORY.setPort(port);
        FACTORY.setUsername(username);
        FACTORY.setPassword(password);

        Connection connection = FACTORY.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(queueName, false, false, true, null);
        GetResponse message = channel.basicGet(queueName, true);
        ObjectMapper mapper = new ObjectMapper();
        Event event = mapper.readValue(message.getBody(), Event.class);
        LOGGER.info("Leyendo mensaje : {}", event);

        channel.close();
        connection.close();
        return event;
    }

    public static int count(Map<String, Object> config) throws IOException, TimeoutException {
        LOGGER.info(config.toString());
        String host = (String) config.get("host");
        int port = (int) config.get("port");
        String username = (String) config.get("username");
        String password = (String) config.get("password");
        String queueName = (String) config.get("queueName");

        FACTORY.setHost(host);
        FACTORY.setPort(port);
        FACTORY.setUsername(username);
        FACTORY.setPassword(password);

        Connection connection = FACTORY.newConnection();
        Channel channel = connection.createChannel();
        int numMessage = (int) channel.messageCount(queueName);

        LOGGER.info("Numero Mensajes : {}", numMessage);

        channel.close();
        connection.close();
        return numMessage;
    }
}
