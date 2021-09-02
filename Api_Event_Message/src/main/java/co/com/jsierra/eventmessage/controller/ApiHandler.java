package co.com.jsierra.eventmessage.controller;

import co.com.jsierra.eventmessage.models.Event;
import co.com.jsierra.eventmessage.service.RabbitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ApiHandler {

    @Autowired
    RabbitService rabbitService;

    public Mono<ServerResponse> readMessage(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .body(rabbitService.readFirtsMessage(), Event.class);
    }

    public Mono<ServerResponse> sendMessage(ServerRequest serverRequest) {
        Mono<Event> event = serverRequest.bodyToMono(Event.class);
        return ServerResponse.ok()
                .body( event.flatMap(val -> rabbitService.sendMessage(val)),  Event.class);
    }

    public Mono<ServerResponse> purgeQueue(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .body(rabbitService.purgeQueue(), Void.class);
    }
}
