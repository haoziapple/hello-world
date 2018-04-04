package com.example.demo;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author wanghao
 * @Description
 * @date 2018-04-02 13:27
 */
@Service
public class HelloWorldHandler {

    public Mono<ServerResponse> helloWorld(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromObject("hello world"));
    }

    public Mono<ServerResponse> helloWorld2(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromObject("hello world2"));
    }
}
