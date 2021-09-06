package co.com.jsierra.eventmessage.service;

import co.com.jsierra.eventmessage.models.Event;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class RabbitService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitService.class);


    @Autowired
    @Qualifier("rabbitTemplateConfig")
    private AmqpTemplate rabbitTemplate;

    @Value("${queue-params.queue}")
    public String queueName;

    private ObjectMapper mapper = new ObjectMapper();

    public Mono<Event> sendMessage(Event message) {
        rabbitTemplate.convertAndSend("", queueName, message);
        LOGGER.info("Enviando mensaje : {}", message);
        return Mono.just(message);
    }

    public Mono<Event> readFirtsMessage() {
        Event message;
        Mono<Event> response;
        try {
            message = mapper.readValue(rabbitTemplate.receive(queueName).getBody(), Event.class);
            LOGGER.info("Leyendo mensaje : {}", message);
            response = Mono.just(message);
        } catch (Exception ex) {
            LOGGER.info("No hay mensajes en la cola,{} ");
            response = Mono.empty();
        }
        return response;
    }

    public Mono<Void> purgeQueue() {
        boolean flag = true;
        while (flag) {
            flag = rabbitTemplate.receive(queueName) != null;
            LOGGER.info("mensaje eliminado");
        }
        return Mono.empty();
    }
}
