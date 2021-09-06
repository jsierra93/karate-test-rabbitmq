package co.com.jsierra.karateapitest.utils;

import co.com.jsierra.karateapitest.models.Event;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.GetResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import static co.com.jsierra.karateapitest.utils.ConectionRabbit.channelRabbit;
import static co.com.jsierra.karateapitest.utils.ConectionRabbit.connectionRabbit;

public class UtilsRabbit {
    private static final Logger LOGGER = LoggerFactory.getLogger(UtilsRabbit.class);

    public static String send(Map<String, Object> config, Event message) throws IOException, TimeoutException {
        String queueName = (String) config.get("queueName");
        Connection connection = connectionRabbit(config);
        Channel channel = channelRabbit(config);

        byte[] data = new ObjectMapper().writeValueAsBytes(message);
        channel.queueDeclare(queueName, false, false, true, null);
        channel.basicPublish("", queueName, null, data);
        LOGGER.info("Enviando mensaje: {}", message);

        channel.close();
        connection.close();
        return message.toString();
    }

    public static Event receive(Map<String, Object> config) throws IOException, TimeoutException {
        String queueName = (String) config.get("queueName");
        Connection connection = connectionRabbit(config);
        Channel channel = channelRabbit(config);
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
        String queueName = (String) config.get("queueName");
        Connection connection = connectionRabbit(config);
        Channel channel = channelRabbit(config);
        int numMessage = (int) channel.messageCount(queueName);

        LOGGER.info("Numero Mensajes : {}", numMessage);

        channel.close();
        connection.close();
        return numMessage;
    }
}
