package com.github.stkurilin.routes.conf;

/**
 * @author Stanislav  Kurilin
 */
public interface UriSpec {
    Iterable<Item> path();

    class Item {

    }

    interface ItemVisitor {

    }
}
