package com.github.stkurilin.routes.internal;

import java.io.IOException;
import java.util.Iterator;

%%
%public
%final
%class _RulesLexer
%implements Iterator<TokenType>

%unicode
%standalone
%type TokenType
%eofval{
    return null;
%eofval}
%{

    public boolean hasNext(){
        return !zzAtEOF;
    }

    public TokenType next() {
        try {
            return yylex();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void remove() {
        throw new  UnsupportedOperationException("Read only");
    }
%}

CRLF= \n | \r | \r\n

/* import start */
IMPORT_KEYWORD="import"
%state AFTER_IMPORT_KEYWORD
%state AFTER_IMPORT_KEYWORD_DELIMITER
IMPORT_CLASS=[a-zA-Z0-9$\.]+
%state AFTER_IMPORT_CLASS_DELIMITER
/* import end */

/* action start */ ACTION= "GET" | "POST" | "PUT" | "DELETE" | "HEAD"
%state AFTER_ACTION
/* action end */

/* url start */
%state IN_URL
SLASH="/"
MATCHER=[a-zA-Z0-9]+
MATCHER_START="{"
%state AFTER_MATCHER_START
%state AFTER_MATCHER
MATCHER_END="}"
LITERAL=[a-zA-Z0-9%]+

%state AFTER_URL
%state AFTER_URL_DELIMITER
/* url end */

/* target start */
INSTANCE_ID=[a-zA-Z0-9$\.]+
%state AFTER_INSTANCE_ID
INSTANCE_METHOD_SEPARATOR=[#]
%state AFTER_INSTANCE_ID_DELIMITER
METHOD_ID=[a-zA-Z0-9$]+
%state AFTER_TARGET
/* target end */

/* args start */
%state IN_ARGS
%state AFTER_ARG
%state AFTER_ARGS

%state AFTER_ARG

/* args end */

/* template start */
TEMPLATE = [a-zA-Z0-9$\./]+
%state AFTER_TEMPLATE
/* template end */

WHITE_SPACE_CHAR=[\ \n\r\t\f]

%%
/*import*/
<YYINITIAL> {IMPORT_KEYWORD}                                    { yybegin(AFTER_IMPORT_KEYWORD); return TokenType.IMPORT_KEYWORD; }
<AFTER_IMPORT_KEYWORD> {WHITE_SPACE_CHAR}+                      { yybegin(AFTER_IMPORT_KEYWORD_DELIMITER); return TokenType.WHITE_SPACE; }
<AFTER_IMPORT_KEYWORD_DELIMITER> {IMPORT_CLASS}                 { yybegin(AFTER_IMPORT_CLASS_DELIMITER); return TokenType.IMPORT_CLASS; }
<AFTER_IMPORT_CLASS_DELIMITER> {WHITE_SPACE_CHAR}+              { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }

/*action*/
<YYINITIAL, AFTER_TARGET, AFTER_ARGS, AFTER_TEMPLATE> {ACTION}  { yybegin(AFTER_ACTION); return TokenType.ACTION; }
<AFTER_ACTION> {WHITE_SPACE_CHAR}+                              { yybegin(IN_URL); return TokenType.WHITE_SPACE; }

/*url*/
<IN_URL> {
    {SLASH}                                                     { return TokenType.SLASH; }
    {LITERAL}                                                   { return TokenType.LITERAL; }
    {MATCHER_START}                                             { yybegin(AFTER_MATCHER_START); return TokenType.MATCHER_START; }
    {WHITE_SPACE_CHAR}+                                         { yybegin(AFTER_URL_DELIMITER); return TokenType.WHITE_SPACE; }
}

<AFTER_MATCHER_START> {MATCHER}                                 { yybegin(AFTER_MATCHER); return TokenType.MATCHER; }
<AFTER_MATCHER> {MATCHER_END}                                   { yybegin(IN_URL); return TokenType.MATCHER_END; }

<AFTER_URL_DELIMITER> {INSTANCE_ID}                             { yybegin(AFTER_INSTANCE_ID); return TokenType.INSTANCE_ID; }

/*target*/
<AFTER_INSTANCE_ID> {INSTANCE_METHOD_SEPARATOR}                 { yybegin(AFTER_INSTANCE_ID_DELIMITER); return TokenType.INSTANCE_METHOD_SEPARATOR; }
<AFTER_INSTANCE_ID_DELIMITER> {METHOD_ID}                       { yybegin(AFTER_TARGET); return TokenType.METHOD_ID; }
<AFTER_TARGET> "("                                              { yybegin(IN_ARGS); return TokenType.ARGS_START; }
<IN_ARGS> [a-zA-Z0-9]+                                          { yybegin(AFTER_ARG); return TokenType.ARG; }
<AFTER_ARG> ","                                                 { yybegin(IN_ARGS); return TokenType.ARG_SEPARATOR; }
<IN_ARGS> ")"                                                   { yybegin(AFTER_ARGS); return TokenType.ARGS_END; }
<AFTER_ARG> ")"                                                 { yybegin(AFTER_ARGS); return TokenType.ARGS_END; }

<AFTER_TARGET,  AFTER_ARGS> {TEMPLATE}                          { yybegin(AFTER_TEMPLATE); return TokenType.TEMPLATE; }

{WHITE_SPACE_CHAR}+                                             { return TokenType.WHITE_SPACE; }
. { return TokenType.BAD_CHARACTER; }
