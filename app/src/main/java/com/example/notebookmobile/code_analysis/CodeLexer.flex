/******************** USERS CODE *******************************/
package com.example.notebookmobile.code_analysis;

import java_cup.runtime.*;
import java.util.*;

%%

/**************** Options and declarations ******************/
%public
%unicode
%class CodeLexer
%cup
%line
%column
%init{
    errorsList = new LinkedList<>();
    string = new StringBuilder();
%init}

/**************************** States **********************************/
%state STRING

/**************************** macros **********************************/
LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]

WhiteSpace = {LineTerminator} | [ \t\f]
Identifier = [:jletter:] ([:jletterdigit:]|_)*
SimpleBoolean = 0|1
DecIntegerLiteral = [0-9]+
DecFloatLiteral = {DecIntegerLiteral}\.{DecIntegerLiteral}
StringCharacter = [^\r\n\"\\]
OctDigit          = [0-7]

%{
    StringBuilder string;
  /*-----------------------------------------------------------------
    Error handling
  -------------------------------------------------------------------*/
    private List<String> errorsList;
    public List<String> symbols = new ArrayList();

    public List<String> getErrors(){
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
        errorsList.add("Error en la linea: " + (yyline+1) + ", columna: " + (yycolumn+1) + " : " + message);
    }

%}

%%

/* Lexic Rules */

    /*-------------------------------------------------------------------------------
                                Text Marks
    ----------------------------------------------------------------------------------*/
    /* Operators */
        "+"             { return symbol(sym.PLUS); }
        "-"             { return symbol(sym.MINUS); }
        "*"             { return symbol(sym.TIMES); }
        "/"             { return symbol(sym.DIV); }
        "^"             { return symbol(sym.POWER); }
        "="             { return symbol(sym.EQUALS); }

    /* delimitators */
        "("             { return symbol(sym.LPAREN); }
        ")"             { return symbol(sym.RPAREN); }


    /*-------------------------------------------------------------
     *                      Default State
     --------------------------------------------------------------*/
    <YYINITIAL> {
        /* keywords */
        "print"       { return symbol(sym.PRINT); }
        "format"      { return symbol(sym.FORMAT); }
        "plot"        { return symbol(sym.PLOT); }


        /* literals */
        {Identifier}    { return symbol( sym.ID, yytext() ); }

        {DecIntegerLiteral}    { return symbol(sym.INTEGER_LIT, Float.valueOf(yytext())); }

        {DecFloatLiteral}    { return symbol(sym.FLOAT_LIT, Float.valueOf(yytext())); }

        \"              { string.setLength(0); yybegin(STRING); }
    }

    <STRING> {
        \"                             { yybegin(YYINITIAL); return symbol(sym.STRING_LIT, string.toString()); }

        {StringCharacter}+             { string.append( yytext() ); }

        /* escape sequences */
        "\\b"                          { string.append( '\b' ); }
        "\\t"                          { string.append( '\t' ); }
        "\\n"                          { string.append( '\n' ); }
        "\\f"                          { string.append( '\f' ); }
        "\\r"                          { string.append( '\r' ); }
        "\\\""                         { string.append( '\"' ); }
        "\\'"                          { string.append( '\'' ); }
        "\\\\"                         { string.append( '\\' ); }
        \\[0-3]?{OctDigit}?{OctDigit}  { char val = (char) Integer.parseInt(yytext().substring(1),8);
                                                string.append( val ); }

        /* error cases */
        \\.                            { error("Secuencia ilegal de escape \""+yytext()+"\""); }
        {LineTerminator}               { error("Literal de cadena sin terminar al final de la línea"); }
    }

    /*lo ignorado*/
    {WhiteSpace}     {/* ignoramos */}

    /* error fallback */
    .               { error("Simbolo invalido <"+ yytext()+">");}
    <<EOF>>         { return symbol(sym.EOF); }