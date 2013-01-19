package com.github.stkurilin.routes;

import com.github.stkurilin.routes.internal.RuleCreator;
import com.github.stkurilin.routes.internal.TokenType;
import com.github.stkurilin.routes.internal._RulesLexer;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stanislav Kurilin
 */
public final class RulesReader {
    private final RuleCreator ruleCreator;

    public RulesReader(RuleCreator ruleCreator) {
        this.ruleCreator = ruleCreator;
    }

    public Iterable<Rule> apply(Reader source) {
        Method method = null;  //start rule
        UriSpec uriSpec = null;
        Class<? extends Object> clazz = null;
        String methodId = null;
        List<String> args = null;
        final ArrayList<Rule> result = new ArrayList<Rule>();
        final _RulesLexer lexer = new _RulesLexer(source);
        while (lexer.hasNext()) {
            final TokenType tokenType = lexer.next();
            final String text = lexer.yytext();
            System.out.println(String.format("%s:%s", tokenType, text));
            if (tokenType == null && method != null) {
                result.add(ruleCreator.apply(method, uriSpec, clazz, methodId, args));
                method = null;
                continue;
            }


            switch (tokenType) {
                case ACTION:
                    if (method != null) {
                        result.add(ruleCreator.apply(method, uriSpec, clazz, methodId, args));
                        method = null;
                    }
                    args = new ArrayList<String>();
                    method = method(text);
                    break;
                case URL:
                    uriSpec = new UriSpec() {
                        @Override
                        public Iterable<Item> path() {
                            return new ArrayList<Item>() {{
                                add(new Literal(text));
                            }};
                        }
                    };
                    break;
                case INSTANCE_ID:
                    clazz = findClass(text);
                    break;
                case INSTANCE_METHOD_SEPARATOR:
                    break;
                case METHOD_ID:
                    methodId = text;
                    break;
                case BAD_CHARACTER:
                    throw new RuntimeException(String.format("Error while parsing >%s< on ", text));
                case WHITE_SPACE:
                    break;
                default:
                    throw new AssertionError(tokenType);
            }
        }
        if (method != null)
            throw new RuntimeException("fuck");
        return result;
    }

    private Method method(String text) {
        return Method.valueOf(uppercaseFirstLatter(text.toLowerCase()));
    }

    private Class<Object> findClass(String text) {
        try {
            return (Class) Class.forName(text);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private String uppercaseFirstLatter(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
}
