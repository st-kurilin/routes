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
        return null;
    }

    public Person save(Person person) {
        if (person.isNew()) {
            person.setId(generateId());
            data.add(person);
            return person;
        } else {
            data.add(person);//it will replace old instance since provides same hashCode
            return person;
        }
    }

    public void delete(long id) {
        data.remove(find(id));
    }

    private synchronized long generateId() {
        long max = 0;
        for (Person each : data) max = Math.max(each.getId(), max);
        return max + 1;
    }
}
