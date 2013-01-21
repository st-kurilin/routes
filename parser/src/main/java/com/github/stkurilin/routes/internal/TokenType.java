package com.github.stkurilin.routes.internal;

/**
 * @author Stanislav Kurilin
 */
public enum TokenType {
    IMPORT_KEYWORD,
    IMPORT_CLASS,
    MATCHER_START,
    MATCHER_END,
    MATCHER,
    LITERAL,
    SLASH,
    ACTION,
    INSTANCE_ID,
    INSTANCE_METHOD_SEPARATOR,
    METHOD_ID,
    ARGS_START,
    ARGS_END,
    ARG,
    BAD_CHARACTER,
    WHITE_SPACE
}
