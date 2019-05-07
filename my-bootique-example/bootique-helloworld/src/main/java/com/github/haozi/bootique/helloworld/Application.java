package com.github.haozi.bootique.helloworld;

import com.google.inject.Module;
import io.bootique.BQCoreModule;
import io.bootique.Bootique;
import io.bootique.jersey.JerseyModule;

/**
 * @author wanghao
 * @Description
 * @date 2019-05-06 11:50
 */
public class Application {
    public static void main(String[] args) {
        Module module = binder -> {
            JerseyModule.extend(binder).addResource(HelloResource.class);
            BQCoreModule.extend(binder)
                    .addConfig("classpath:myconfig.yml");
        };

        Bootique
                .app(args)
                .module(module)
                .autoLoadModules()
                .exec()
                .exit();
    }
}
