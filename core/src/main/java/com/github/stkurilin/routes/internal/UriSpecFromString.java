package com.github.stkurilin.routes.internal;

import com.github.stkurilin.routes.UriSpec;

import java.util.ArrayList;

/**
 * @author Stanislav  Kurilin
 */
public final class UriSpecFromString {
    public UriSpec apply(final String uri){
        return new UriSpec() {
            @Override
            public Iterable<Item> path() {
                //todo: implement;
                return new ArrayList();
            }
        };
    }
}
