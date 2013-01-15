package com.github.stkurilin.routes.example.guiceweb.services;

import com.google.inject.AbstractModule;

/**
 * @author Stanislav  Kurilin
 */
public class ServicesModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Foo.class).to(FooImpl.class);
    }
}
