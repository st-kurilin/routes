package com.github.stkurilin.routes.examples.webresetguicejackson.services;

import com.google.inject.AbstractModule;

/**
 * @author Stanislav  Kurilin
 */
public class ServicesModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(PersonService.class).to(PersonServiceImpl.class);
    }
}
