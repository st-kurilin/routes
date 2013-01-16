package com.github.stkurilin.routes.api;

/**
 * @author Stanislav  Kurilin
 */
public interface RuleDescription {
    Method method();

    UriSpec uriSpec();

    TargetSpec targetSpec();
}
