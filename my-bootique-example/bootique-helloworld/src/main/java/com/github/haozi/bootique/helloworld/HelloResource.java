package com.github.haozi.bootique.helloworld;

import com.github.haozi.bootique.helloworld.service.ConfigService;
import com.github.haozi.bootique.helloworld.service.MyService;
import com.google.inject.Inject;
import io.bootique.annotation.Args;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.Arrays;

import static java.util.stream.Collectors.joining;

/**
 * @author wanghao
 * @Description
 * @date 2019-05-06 11:52
 */

@Path("/")
public class HelloResource {

    @Inject
    @Args
    private String[] args;

    @Inject
    private MyService myService;

    @Inject
    private ConfigService configService;

    private static final Logger loggger = LoggerFactory.getLogger(HelloResource.class);

    @GET
    @Path("/world")
    public String world() {
        loggger.debug("Hello, world!");
        return "Hello, world!";
    }

    @GET
    @Path("/args")
    public String args() {
        String allArgs = Arrays.asList(args).stream().collect(joining(" "));
        return "Hello, world! The app was started with the following arguments: " + allArgs;
    }

    @GET
    @Path("/myServiceName")
    public String myServiceName() {
        return myService.serviceName();
    }

    @GET
    @Path("/testConfig")
    public String testConfig() {
        return configService.testConfig();
    }
}
