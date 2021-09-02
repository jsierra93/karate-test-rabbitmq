package co.com.jsierra.eventmessage.config;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Delivery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Configuration
public class RabbitMqConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMqConfig.class);

    @Value("${queue-params.durable}")
    private boolean isDurableQueue;

    @Value("${queue-params.queue}")
    public String queueName;

    @Value("${queue-params.autodelete}")
    public boolean autodelete;

    @Autowired
    Mono<Connection> connectionMono;
    @Autowired
    AmqpAdmin amqpAdmin;

    @Bean()
    Mono<Connection> connectionMono(RabbitProperties rabbitProperties) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(rabbitProperties.getHost());
        connectionFactory.setPort(rabbitProperties.getPort());
        connectionFactory.setUsername(rabbitProperties.getUsername());
        connectionFactory.setPassword(rabbitProperties.getPassword());
        return Mono.fromCallable(() -> connectionFactory.newConnection("reactor-rabbit")).cache();
    }

    @Bean
    Sender sender(Mono<Connection> connectionMono) {
        return RabbitFlux.createSender(new SenderOptions().connectionMono(connectionMono));
    }

    @Bean
    Receiver receiver(Mono<Connection> connectionMono) {
        return RabbitFlux.createReceiver(new ReceiverOptions().connectionMono(connectionMono));
    }

    @Bean
    Flux<Delivery> deliveryFlux(Receiver receiver) {
        return receiver.consumeNoAck(queueName);
    }

    @PostConstruct
    public void init() {
        amqpAdmin.declareQueue(new Queue(queueName, isDurableQueue, false, autodelete));
    }

    @PreDestroy
    public void close() throws Exception {
        connectionMono.block().close();
    }



}