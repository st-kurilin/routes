package com.github.stkurilin.routes.examples.webresetguicejackson.services;

import com.github.stkurilin.routes.examples.webresetguicejackson.dao.PersonDao;
import com.github.stkurilin.routes.examples.webresetguicejackson.entity.Person;

import javax.inject.Inject;

/**
 * @author Stanislav  Kurilin
 */
class PersonServiceImpl implements PersonService {

    private final PersonDao dao;

    @Inject
    public PersonServiceImpl(PersonDao dao) {
        this.dao = dao;
    }


    @Override
    public long create(Person person) {
        final Person entity = dao.save(person);
        return entity.getId();
    }

    @Override
    public Person find(long id) {
        return dao.find(id);
    }
}
