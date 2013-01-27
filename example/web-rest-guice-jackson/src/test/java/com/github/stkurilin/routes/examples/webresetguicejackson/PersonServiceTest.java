package com.github.stkurilin.routes.examples.webresetguicejackson;

import com.github.stkurilin.routes.AbstractRoutesTest;
import com.github.stkurilin.routes.examples.webresetguicejackson.entity.Person;
import com.github.stkurilin.routes.servlet.RoutesFilter;
import org.codehaus.jackson.map.ObjectMapper;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

/**
 * @author Stanislav  Kurilin
 */
public class PersonServiceTest extends AbstractRoutesTest {
    @Test
    public void testCreate() {
        assertLong(post("/persons", data("Alan", 100)));
    }

    @Test(dependsOnMethods = "testCreate")
    public void testFind() {
        final long id = Long.parseLong(post("/persons", data("Bob", 70)));

        final String fromServer = get(personUri(id));

        assertEquals(deserializePerson(fromServer), new Person(id, "Bob", 70));
    }

    @Test(dependsOnMethods = {"testCreate", "testFind"})
    public void testUpdate() {
        final long id = Long.parseLong(post("/persons", data("Bob", 70)));

        put(personUri(id), data("Bob", 71));

        final String fromServer = get(personUri(id));
        assertEquals(deserializePerson(fromServer), new Person(id, "Bob", 71));
    }

    @Test(dependsOnMethods = {"testCreate", "testFind"})
    public void testDelete() {
        final long id = Long.parseLong(post("/persons", data("Bob", 70)));

        delete(personUri(id));

        assertEquals(get(personUri(id)), "null"); //todo
    }

    private String personUri(long id) {
        return String.format("/persons/%s", id);
    }


    private String data(String name, int age) {
        return String.format("{\"name\":\"%s\", \"age\":\"%s\"}", name, age);
    }


    @Override
    protected void init() {
        filter(RoutesFilter.class, "/*");
        listener(new Config());
    }

    private void assertLong(String s) {
        try {
            Long.parseLong(s);
        } catch (NumberFormatException e) {
            fail("Could not parse", e);
        }
    }

    private Person deserializePerson(String fromServer) {
        try {
            return new ObjectMapper().readValue(fromServer, Person.class);
        } catch (IOException e) {
            fail("Error while person deserialize", e);
            return null;
        }
    }
}
