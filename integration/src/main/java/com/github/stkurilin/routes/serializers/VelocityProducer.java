package com.github.stkurilin.routes.serializers;

import com.github.stkurilin.routes.Rule;
import com.github.stkurilin.routes.internal.Response;
import com.github.stkurilin.routes.internal.ResponseProducer;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.StringWriter;

/**
 * @author Stanislav Kurilin
 */
public class VelocityProducer implements ResponseProducer {
    @Override
    public Response apply(Rule.MatchingRule appliedRule, final Object result) {
        if (appliedRule.getTemplate().equals("")) return new Response() {
            @Override
            public String toBody() {
                return result.toString();
            }
        };
        final VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();
        /*  next, get the Template  */
        Template t = ve.getTemplate("./" + appliedRule.getTemplate());
        /*  create a context and add data */
        VelocityContext context = new VelocityContext();
        context.put("$", result);
        /* now render the template into a StringWriter */
        StringWriter writer = new StringWriter();
        t.merge(context, writer);
        /* show the World */
        final String res = writer.toString();
        return new Response() {
            @Override
            public String toBody() {
                return res;
            }
        };
    }
}
