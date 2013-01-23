package com.github.stkurilin.routes.examples.webresetguicejackson.services;

import com.github.stkurilin.routes.examples.webresetguicejackson.entity.Person;

/**
 * @author Stanislav  Kurilin
 */
public interface PersonService {
    long create(Person person);

    Person find(long id);
}
