package com.github.stkurilin.routes.serializers;

import com.github.stkurilin.routes.Rule;
import com.github.stkurilin.routes.internal.Response;
import com.github.stkurilin.routes.internal.ResponseProducer;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * @author Stanislav  Kurilin
 */
public class JacksonSerializer implements ResponseProducer {
    private final ObjectMapper objectMapper;

    public JacksonSerializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public JacksonSerializer() {
        this(new ObjectMapper());
    }

    @Override
    public Response apply(Rule.MatchingRule appliedRule, final Object result) {
        return new Response() {
            @Override
            public String toBody() {
                try {
                    return objectMapper.writeValueAsString(result);
                } catch (IOException e) {
                    return null;
                }
            }
        };
    }
}
