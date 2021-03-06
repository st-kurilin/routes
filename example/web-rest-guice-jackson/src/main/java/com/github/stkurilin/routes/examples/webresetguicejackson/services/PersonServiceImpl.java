package com.github.stkurilin.routes.examples.webresetguicejackson.services;

import com.github.stkurilin.routes.examples.webresetguicejackson.dao.PersonDao;
import com.github.stkurilin.routes.examples.webresetguicejackson.entity.Person;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Stanislav  Kurilin
 */
@Singleton
public class PersonServiceImpl implements PersonService {

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
    public Person read(long id) {
        return dao.find(id);
    }

    @Override
    public void update(long id, Person person) {
        final Person loaded = dao.find(id);
        loaded.setName(person.getName());
        loaded.setAge(person.getAge());
        dao.save(loaded);
    }

    @Override
    public void delete(long id) {
        dao.delete(id);
    }
}
