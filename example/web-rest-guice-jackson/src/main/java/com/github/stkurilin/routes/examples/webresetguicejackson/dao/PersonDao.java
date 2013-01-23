package com.github.stkurilin.routes.examples.webresetguicejackson.dao;

import com.github.stkurilin.routes.examples.webresetguicejackson.entity.Person;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * @author Stanislav  Kurilin
 */
public class PersonDao {
    private final Random random = new Random();
    private final Set<Person> data = new HashSet<Person>();

    {
        data.add(new Person(0, "Bob", 30));
        data.add(new Person(1, "Jane", 22));
    }

    public Person find(long id) {
        for (Person each : data)
            if (each.getId() == id)
                return each;
        throw new IllegalArgumentException();
    }

    public Person save(Person person) {
        person.setId(generateId());
        return person;
    }

    private long generateId() {
        return random.nextInt();
    }
}
