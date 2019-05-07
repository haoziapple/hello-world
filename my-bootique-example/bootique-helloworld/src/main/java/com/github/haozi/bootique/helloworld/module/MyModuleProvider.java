package com.github.haozi.bootique.helloworld.module;

import com.google.inject.Module;
import io.bootique.BQCoreModule;
import io.bootique.BQModuleProvider;

import java.util.Collection;
import java.util.Collections;

/**
 * @author wanghao
 * @Description
 * @date 2019-05-07 11:11
 */
public class MyModuleProvider implements BQModuleProvider {
    @Override
    public Module module() {
        return new MyModule();
    }

    // provides human-readable name of the module
    @Override
    public String name() {
        return "myModule";
    }

    // a collection of modules whose services are overridden by this module
    @Override
    public Collection<Class<? extends Module>> overrides() {
        return Collections.singleton(BQCoreModule.class);
    }
}
