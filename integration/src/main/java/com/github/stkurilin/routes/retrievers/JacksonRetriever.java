package com.github.stkurilin.routes.retrievers;

import com.github.stkurilin.routes.internal.Retriever;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * @author Stanislav  Kurilin
 */
public class JacksonRetriever implements Retriever {
    private final ObjectMapper objectMapper;

    public JacksonRetriever(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public JacksonRetriever() {
        this(new ObjectMapper());
    }

    @Override
    public Object apply(Object input, Class<?> target) {
        try {
            return objectMapper.readValue(input.toString(), target);
        } catch (IOException e) {
            return null;
        }
    }
}
