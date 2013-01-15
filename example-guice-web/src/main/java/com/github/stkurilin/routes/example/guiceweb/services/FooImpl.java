package com.github.stkurilin.routes.example.guiceweb.services;

import java.util.Date;

/**
 * @author Stanislav  Kurilin
 */
public class FooImpl implements Foo {
    @Override
    public String bar(String arg) {
        return new Date().toString();
    }
}
