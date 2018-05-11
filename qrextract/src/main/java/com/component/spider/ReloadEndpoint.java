package com.component.spider;

import org.springframework.beans.BeansException;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

/**
 * @author wanghao
 * @Description
 * @date 2018-05-11 16:11
 */
@RestController
public class ReloadEndpoint implements ApplicationContextAware {

    private static final Map<String, String> NO_CONTEXT_MESSAGE = Collections
            .unmodifiableMap(
                    Collections.singletonMap("message", "No context to shutdown."));

    private static final Map<String, String> SHUTDOWN_MESSAGE = Collections
            .unmodifiableMap(
                    Collections.singletonMap("message", "Reloading..."));

    private ConfigurableApplicationContext context;

    @RequestMapping(value = "/reload", method = RequestMethod.GET)
    public Map<String, String> reload() {
        if (this.context == null) {
            return NO_CONTEXT_MESSAGE;
        }
        try {
            return SHUTDOWN_MESSAGE;
        } finally {
            Thread thread = new Thread(this::performReload);
            thread.setContextClassLoader(getClass().getClassLoader());
            thread.start();
        }
    }

    private void performReload() {
        try {
            Thread.sleep(500L);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        this.context.refresh();
//        this.context.stop();
//        this.context.start();
    }


    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        if (context instanceof ConfigurableApplicationContext) {
            this.context = (ConfigurableApplicationContext) context;
        }
    }
}
