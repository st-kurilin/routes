package com.github.stkurilin.routes.routes;

import com.github.stkurilin.routes.*;
import com.github.stkurilin.routes.internal.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Stanislav Kurilin
 */
final class TestHelper {
    static Rule rule(Method method, Iterable<UriSpec.Item> items) {
        final UriSpec uriSpec = mock(UriSpec.class);
        when(uriSpec.path()).thenReturn(items);
        return new RuleCreator().apply(method, uriSpec, mock(TargetSpec.class));
    }

    static Request request(Method method, String path) {
        final Request request = mock(Request.class);
        when(request.method()).thenReturn(method);
        when(request.path()).thenReturn(path);
        return request;
    }

    static UriSpec.Item literal(final String value) {
        return new UriSpec.Item() {
            @Override
            public <R> R apply(UriSpec.ItemVisitor<R> visitor) {
                return visitor.literal(value);
            }
        };
    }

    static UriSpec.Item matcher(final String name) {
        return new UriSpec.Item() {
            @Override
            public <R> R apply(UriSpec.ItemVisitor<R> visitor) {
                return visitor.matcher(name);
            }
        };
    }
}
