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
%state HEADER6, HEADER5, HEADER4, HEADER3, HEADER2, HEADER1
%state BOLD_ITALIC, BOLD, ITALIC
%state ORDERED_LIST, NOT_ORDERED_LIST
%state STRING_LIT

/**************************** macros **********************************/
LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]

WhiteSpace = {LineTerminator} | [ \t\f]
Identifier = [:jletter:] ([:jletterdigit:]|_)*
SimpleBoolean = 0|1
DecIntegerLiteral = [0-9]+
DecFloatLiteral = {DecIntegerLiteral}\.{DecIntegerLiteral}
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

    // Text (todo lo demás es texto normal)
    . { string.setLength(0); string.append(yytext()); yybegin(STRING_LIT); }
}

/* Headers */
<HEADER6, HEADER5, HEADER4, HEADER3, HEADER2, HEADER1> {
    [^\r\n]+ { string.append(yytext()); }

    {LineTerminator}? {
        int type =
            yystate() == HEADER6 ? sym.HEADER6 :
            yystate() == HEADER5 ? sym.HEADER5 :
            yystate() == HEADER4 ? sym.HEADER4 :
            yystate() == HEADER3 ? sym.HEADER3 :
            yystate() == HEADER2 ? sym.HEADER2 :
            sym.HEADER1;

        yybegin(YYINITIAL);
        return symbol(type, string.toString().trim()); // Se asegura de eliminar espacios extra
    }

    <<EOF>> {
          int type =
              yystate() == HEADER6 ? sym.HEADER6 :
              yystate() == HEADER5 ? sym.HEADER5 :
              yystate() == HEADER4 ? sym.HEADER4 :
              yystate() == HEADER3 ? sym.HEADER3 :
              yystate() == HEADER2 ? sym.HEADER2 :
              sym.HEADER1;

              yybegin(YYINITIAL);
              return symbol(type, string.toString().trim()); // Se asegura de eliminar espacios extra
      }
}

/* Bold, Italic, and Bold-Italic */
<BOLD_ITALIC> {
    "***" { yybegin(YYINITIAL); return symbol(sym.BOLD_ITALIC, string.toString().trim()); }
        [^\r\n] { string.append(yytext()); }
        {LineTerminator} { string.append("\n"); }
}
<BOLD> {
    "**" { yybegin(YYINITIAL); return symbol(sym.BOLD, string.toString().trim()); }
    [^\r\n] { string.append(yytext()); }
    {LineTerminator} { string.append("\n"); }
}
<ITALIC> {
    "*" { yybegin(YYINITIAL); return symbol(sym.ITALIC, string.toString().trim()); }
    [^\r\n] { string.append(yytext()); }
    {LineTerminator} { string.append("\n"); }
}

/* Ordered and Not Ordered Lists */
<ORDERED_LIST> {
    {LineTerminator} { yybegin(YYINITIAL); return symbol(sym.ORDERED_LIST_ITEM, string.toString()); }
    <<EOF>> { yybegin(YYINITIAL); return symbol(sym.ORDERED_LIST_ITEM, string.toString()); }
    . { string.append(yytext()); }
}
<NOT_ORDERED_LIST> {
    {LineTerminator} { yybegin(YYINITIAL); return symbol(sym.NOT_ORDERED_LIST_ITEM, string.toString()); }
     <<EOF>> { yybegin(YYINITIAL); return symbol(sym.NOT_ORDERED_LIST_ITEM, string.toString()); }
    . { string.append(yytext()); }
}

<STRING_LIT> {
    [^\r\n]+ { string.append(yytext()); }
    {LineTerminator} {
        yybegin(YYINITIAL);
        return symbol(sym.STRING_LIT, string.toString().trim());
    }
    <<EOF>> {
        yybegin(YYINITIAL);
        return symbol(sym.STRING_LIT, string.toString().trim());
    }
}

/* Ignored whitespace */
{WhiteSpace} { /* Ignorar */ }

/* Error handling */
. { error("Símbolo inválido <" + yytext() + ">"); }
<<EOF>> { return symbol(sym.EOF); }
