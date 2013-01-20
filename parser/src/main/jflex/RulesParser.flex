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
ACTION= "GET" | "POST" | "PUT" | "DELETE" | "HEAD"

URL=[a-zA-Z/0-9$]+

IMPORT_KEYWORD="import"
%state AFTER_IMPORT_KEYWORD
%state AFTER_IMPORT_KEYWORD_DELIMITER
IMPORT_CLASS=[a-zA-Z0-9$\.]+
%state AFTER_IMPORT_CLASS_DELIMITER

INSTANCE_ID=[a-zA-Z0-9$\.]+
/*INSTANCE_METHOD_SEPARATOR=[\.]*/
INSTANCE_METHOD_SEPARATOR=[#]
METHOD_ID=[a-zA-Z0-9$]+
WHITE_SPACE_CHAR=[\ \n\r\t\f]

%state AFTER_ACTION
%state AFTER_ACTION_DELIMITER
%state AFTER_URL
%state AFTER_URL_DELIMITER
%state AFTER_INSTANCE_ID
%state AFTER_INSTANCE_ID_DELIMITER

%%
<YYINITIAL> {IMPORT_KEYWORD} { yybegin(AFTER_IMPORT_KEYWORD); return TokenType.IMPORT_KEYWORD; }
<AFTER_IMPORT_KEYWORD> {WHITE_SPACE_CHAR}+ {  yybegin(AFTER_IMPORT_KEYWORD_DELIMITER); return TokenType.WHITE_SPACE; }
<AFTER_IMPORT_KEYWORD_DELIMITER> {IMPORT_CLASS} {  yybegin(AFTER_IMPORT_CLASS_DELIMITER); return TokenType.IMPORT_CLASS; }
<AFTER_IMPORT_CLASS_DELIMITER> {WHITE_SPACE_CHAR}+ {  yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }

<YYINITIAL> {ACTION} { yybegin(AFTER_ACTION); return TokenType.ACTION; }
<AFTER_ACTION> {WHITE_SPACE_CHAR}+ {  yybegin(AFTER_ACTION_DELIMITER); return TokenType.WHITE_SPACE; }
<AFTER_ACTION_DELIMITER> {URL} { yybegin(AFTER_URL); return TokenType.URL; }
<AFTER_URL> {WHITE_SPACE_CHAR}+ {  yybegin(AFTER_URL_DELIMITER); return TokenType.WHITE_SPACE; }
<AFTER_URL_DELIMITER> {INSTANCE_ID} { yybegin(AFTER_INSTANCE_ID); return TokenType.INSTANCE_ID; }
<AFTER_INSTANCE_ID> {INSTANCE_METHOD_SEPARATOR} { yybegin(AFTER_INSTANCE_ID_DELIMITER); return TokenType.INSTANCE_METHOD_SEPARATOR; }
<AFTER_INSTANCE_ID_DELIMITER> {METHOD_ID} { yybegin(YYINITIAL); return TokenType.METHOD_ID; }
{WHITE_SPACE_CHAR}+ { return TokenType.WHITE_SPACE; }
. { return TokenType.BAD_CHARACTER; }
