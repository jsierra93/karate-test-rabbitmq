package co.com.jsierra.eventmessage.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ApiRouter {

    public static final String PATH = "/v1/api/queue";
    @Bean
    RouterFunction<ServerResponse> router(ApiHandler handler){
        return route(GET(PATH).and(accept(MediaType.APPLICATION_JSON)), handler::readMessage)
                .andRoute(POST(PATH).and(accept(MediaType.APPLICATION_JSON)), handler::sendMessage)
                .andRoute(DELETE(PATH).and(accept(MediaType.APPLICATION_JSON)), handler::purgeQueue);

    }
}
