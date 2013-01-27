package com.github.stkurilin.routes.internal;

import com.github.stkurilin.routes.TargetSpec;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author Stanislav  Kurilin
 */
public class ArgumentsCollector {
    public Iterable<Object> apply(TargetSpec targetSpec, Map<String, Object> available) {
        final ArrayList<Object> res = new ArrayList<Object>();
        for (String each : targetSpec.args()) {
            if (!available.containsKey(each)) throw new IllegalStateException("Could not find: " + each);
            res.add(available.get(each));
        }
        return res;
    }
}
