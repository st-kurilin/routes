package com.github.stkurilin.routes.examples.webresetguicejackson.services;

import com.github.stkurilin.routes.examples.webresetguicejackson.entity.Person;

/**
 * @author Stanislav  Kurilin
 */
public interface PersonService {
    long create(Person person);

    Person read(long id);

    void update(long id, Person person);

    void delete(long id);
}
