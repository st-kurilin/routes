package com.github.stkurilin.routes.retrievers;

import org.codehaus.jackson.annotate.JsonProperty;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Stanislav  Kurilin
 */
public class JacksonRetrieverTest {
    @Test
    public void testDeserialization() throws Exception {
        assertEquals(new JacksonRetriever().apply("{\"bar\":\"yo\"}", Foo.class), new Foo("yo"));
    }

    public static class Foo {
        @JsonProperty
        String bar;

        public Foo(String bar) {
            this.bar = bar;
        }

        public Foo() {
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Foo foo = (Foo) o;

            if (bar != null ? !bar.equals(foo.bar) : foo.bar != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return bar != null ? bar.hashCode() : 0;
        }
    }
}
