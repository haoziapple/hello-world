package com.github.haozi.bootique.helloworld.module;

import com.github.haozi.bootique.helloworld.factory.ConfigFactory;
import com.github.haozi.bootique.helloworld.service.ConfigService;
import com.github.haozi.bootique.helloworld.service.MyService;
import com.github.haozi.bootique.helloworld.service.MyServiceImpl;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import io.bootique.ConfigModule;
import io.bootique.config.ConfigurationFactory;

/**
 * @author wanghao
 * @Description
 * @date 2019-05-07 11:05
 */
public class MyModule extends ConfigModule implements Module {
    @Override
    public void configure(Binder binder) {
        binder.bind(MyService.class).to(MyServiceImpl.class);
    }

    @Provides
    public ConfigService createConfigService(
            ConfigurationFactory configFactory,
            MyService myService) {

        return configFactory
                .config(ConfigFactory.class, configPrefix)
                .createConfigService(myService);
    }
}
