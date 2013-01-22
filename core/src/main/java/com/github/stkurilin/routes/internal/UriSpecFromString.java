package com.github.stkurilin.routes.internal;

import com.github.stkurilin.routes.UriSpec;

import java.util.ArrayList;

/**
 * @author Stanislav  Kurilin
 */
public final class UriSpecFromString {
    public UriSpec apply(final String uri) {
        return new UriSpec() {
            @Override
            public Iterable<Item> path() {
                final ArrayList<Item> res = new ArrayList<Item>();
                for (String part : uri.split("/")) {
                    if (part.isEmpty()) continue;
                    if (part.startsWith("{")) {
                        res.add(new UriSpec.Matcher(part.substring(0, part.length() - 1)));
                    } else {
                        res.add(new Literal(part));
                    }
                }
                return res;
            }
        };
    }
}
