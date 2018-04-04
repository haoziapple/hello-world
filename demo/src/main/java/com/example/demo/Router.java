package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

/**
 * @author wanghao
 * @Description
 * @date 2018-04-02 13:30
 */
@Configuration
public class Router {

    @Bean
    public RouterFunction<?> routerFunction(HelloWorldHandler helloWorldHandler){
        return RouterFunctions.route(RequestPredicates.GET("/hello"), helloWorldHandler::helloWorld)
                .andRoute(RequestPredicates.GET("/hello2"), helloWorldHandler::helloWorld2);
    }
}
