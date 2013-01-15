package com.github.stkurilin.routes.conf;

/**
 * @author Stanislav  Kurilin
 */
public interface RuleDescription {
    Method method();

    UriSpec uriSpec();

    TargetSpec targetSpec();
}
