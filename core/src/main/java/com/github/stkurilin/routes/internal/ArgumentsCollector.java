package com.github.stkurilin.routes.internal;

import com.github.stkurilin.routes.TargetSpec;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author Stanislav  Kurilin
 */
public class ArgumentsCollector {
    public Iterable<String> apply(TargetSpec targetSpec, Map<String, String> available) {
        final ArrayList<String> res = new ArrayList<String>();
        for (String each : targetSpec.args()) {
            if (!available.containsKey(each)) throw new IllegalStateException("Could not find: " + each);
            res.add(available.get(each));
        }
        return res;
    }
}
