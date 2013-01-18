package com.github.stkurilin.routes.internal;

import com.github.stkurilin.routes.Method;
import com.github.stkurilin.routes.TargetSpec;
import com.github.stkurilin.routes.UriSpec;

/**
 * @author Stanislav  Kurilin
 */
public interface RuleDescription {
    Method method();

    UriSpec uriSpec();

    TargetSpec targetSpec();
}
