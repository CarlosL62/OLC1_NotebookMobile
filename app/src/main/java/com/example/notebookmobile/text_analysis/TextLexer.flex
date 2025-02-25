/******************** USERS CODE *******************************/
package com.example.notebookmobile.text_analysis;

import java_cup.runtime.*;
import java.util.*;

%%

/**************** Options and declarations ******************/
%public
%unicode
%class TextLexer
%cup
%line
%column
%init{
    errorsList = new LinkedList<>();
    string = new StringBuilder();
%init}

/**************************** States **********************************/
%state STRING
%state HEADER6, HEADER5, HEADER4, HEADER3, HEADER2, HEADER1
%state BOLD_ITALIC, BOLD, ITALIC
%state ORDERED_LIST, NOT_ORDERED_LIST

/**************************** macros **********************************/
LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]

WhiteSpace = {LineTerminator} | [ \t\f]
Identifier = [:jletter:] ([:jletterdigit:]|_)*
SimpleBoolean = 0|1
DecIntegerLiteral = [0-9]+
DecFloatLiteral = {DecIntegerLiteral}\.{DecIntegerLiteral}
StringCharacter = [^\r\n\"\\]
OctDigit = [0-7]

%{
    StringBuilder string;

    /*-----------------------------------------------------------------
    Error handling
    -------------------------------------------------------------------*/
    private List<String> errorsList = new LinkedList<>();
    public List<String> symbols = new ArrayList<>();

    public List<String> getErrors() {
        return this.errorsList;
    }

    /*----------------------------------------------------------------
     Parser code
    ------------------------------------------------------------------*/
    private Symbol symbol(int type) {
        symbols.add(yytext());
        return new Symbol(type, yyline+1, yycolumn+1);
    }

    private Symbol symbol(int type, Object value) {
        symbols.add(value.toString());
        return new Symbol(type, yyline+1, yycolumn+1, value);
    }

    private void error(String message) {
        String errorMessage = "Error en la línea " + (yyline+1) + ", columna " + (yycolumn+1) + " : " + message;
        errorsList.add(errorMessage);
        System.out.println(errorMessage);
    }

%}

/* Regular Definitions */

NUMBER = [0-9]+
DOT = \.

%%

/* Lexic Rules */

<YYINITIAL> {
    // Headers
    "###### " { string.setLength(0); yybegin(HEADER6); }
    "##### " { string.setLength(0); yybegin(HEADER5); }
    "#### " { string.setLength(0); yybegin(HEADER4); }
    "### " { string.setLength(0); yybegin(HEADER3); }
    "## " { string.setLength(0); yybegin(HEADER2); }
    "# " { string.setLength(0); yybegin(HEADER1); }

    // Bold and Italic
    "***" { string.setLength(0); yybegin(BOLD_ITALIC); }
    "**" { string.setLength(0); yybegin(BOLD); }
    "*" { string.setLength(0); yybegin(ITALIC); }

    // Ordered list
    {NUMBER}{DOT} { string.setLength(0); yybegin(ORDERED_LIST); }

    // Not ordered list
    "+" { string.setLength(0); yybegin(NOT_ORDERED_LIST); }

    // Text
    . { string.setLength(0); yybegin(STRING); }
}

/* Headers */
<HEADER6> {
    {LineTerminator} {
        yybegin(YYINITIAL);
        return symbol(sym.HEADER6, string.toString());
    }
    . { string.append(yytext()); }
}
<HEADER5> {
    {LineTerminator} {
        yybegin(YYINITIAL);
        return symbol(sym.HEADER5, string.toString());
    }
    . { string.append(yytext()); }
}
<HEADER4> {
    {LineTerminator} {
        yybegin(YYINITIAL);
        return symbol(sym.HEADER4, string.toString());
    }
    . { string.append(yytext()); }
}
<HEADER3> {
    {LineTerminator} {
        yybegin(YYINITIAL);
        return symbol(sym.HEADER3, string.toString());
    }
    . { string.append(yytext()); }
}
<HEADER2> {
    {LineTerminator} {
        yybegin(YYINITIAL);
        return symbol(sym.HEADER2, string.toString());
    }
    . { string.append(yytext()); }
}
<HEADER1> {
    {LineTerminator} {
        yybegin(YYINITIAL);
        return symbol(sym.HEADER1, string.toString());
    }
    . { string.append(yytext()); }
}

/* Bold, Italic, and Bold-Italic */
<BOLD_ITALIC> {
    "***" { yybegin(YYINITIAL); return symbol(sym.BOLD_ITALIC, string.toString()); }
    . { string.append(yytext()); }
}
<BOLD> {
    "**" { yybegin(YYINITIAL); return symbol(sym.BOLD, string.toString()); }
    . { string.append(yytext()); }
}
<ITALIC> {
    "*" { yybegin(YYINITIAL); return symbol(sym.ITALIC, string.toString()); }
    . { string.append(yytext()); }
}

/* Ordered and Not Ordered Lists */
<ORDERED_LIST> {
    {LineTerminator} { yybegin(YYINITIAL); return symbol(sym.ORDERED_LIST_ITEM, string.toString()); }
    . { string.append(yytext()); }
}
<NOT_ORDERED_LIST> {
    {LineTerminator} { yybegin(YYINITIAL); return symbol(sym.NOT_ORDERED_LIST_ITEM, string.toString()); }
    . { string.append(yytext()); }
}


/* Strings */
<STRING> {
    \" { yybegin(YYINITIAL); return symbol(sym.STRING_LIT, string.toString()); }
    {StringCharacter}+ { string.append(yytext()); }
    "\\b" { string.append('\b'); }
    "\\t" { string.append('\t'); }
    "\\n" { string.append('\n'); }
    "\\f" { string.append('\f'); }
    "\\r" { string.append('\r'); }
    "\\\"" { string.append('\"'); }
    "\\'" { string.append('\''); }
    "\\\\" { string.append('\\'); }
    \\[0-3]?{OctDigit}?{OctDigit} {
        char val = (char) Integer.parseInt(yytext().substring(1), 8);
        string.append(val);
    }
    . { error("Secuencia ilegal de escape \"" + yytext() + "\""); }
    {LineTerminator} { error("Literal de cadena sin terminar al final de la línea"); }
}

/* Ignored whitespace */
{WhiteSpace} { /* Ignorar */ }

/* Error handling */
. { error("Símbolo inválido <" + yytext() + ">"); }
<<EOF>> { return symbol(sym.EOF); }
