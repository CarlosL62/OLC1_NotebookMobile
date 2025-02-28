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
%debug
%init{
    errorsList = new LinkedList<>();
    string = new StringBuilder();
%init}

/**************************** States **********************************/
%state STRING
%state PLOT_FUNCTION
%state FORMAT_FUNCTION
%state MATH_FUNCTION
%state MATH_EXP

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

    private int parenCount = 0; // Counter for parenthesis

    public List<String> getErrors(){
        if (this.errorsList == null) {
            this.errorsList = new LinkedList<>();
        }
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


    /*-------------------------------------------------------------
     *                      Default State
     --------------------------------------------------------------*/
    <YYINITIAL> {

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
        ","             { return symbol(sym.COMMA); }

        /* keywords */
        "print"       { return symbol(sym.PRINT); }
        "format"      { yybegin(FORMAT_FUNCTION); return symbol(sym.FORMAT); }
        "plot"        { yybegin(PLOT_FUNCTION); return symbol(sym.PLOT); }


        /* literals */
        {Identifier}    { return symbol( sym.ID, yytext() ); }

        {DecIntegerLiteral}    { return symbol(sym.FLOAT_LIT, Float.valueOf(yytext())); }

        {DecFloatLiteral}    { return symbol(sym.FLOAT_LIT, Float.valueOf(yytext())); }

        \"              { string.setLength(0); yybegin(STRING); }
    }

    <PLOT_FUNCTION> {
        "("             { yybegin(MATH_FUNCTION); return symbol(sym.LPAREN); }
    }

    <FORMAT_FUNCTION> {
        "("             { yybegin(MATH_EXP); return symbol(sym.LPAREN); }
    }

    <MATH_EXP> {
        "x"                     { string.append("x"); }
        "+"                     { string.append("+"); }
        "-"                     { string.append("-"); }
        "*"                     { string.append("*"); }
        "/"                     { string.append("/"); }
        "^"                     { string.append("^"); }
        "("                     { string.append("("); parenCount++; } // Aumentamos contador de paréntesis abiertos
        {DecFloatLiteral}       { string.append(yytext()); }
        {DecIntegerLiteral}     { string.append(yytext()); }

        ")" {
            string.append(")");
            if (parenCount > 0) {
                parenCount--; // Cerramos un paréntesis
            } else {
                yybegin(YYINITIAL); // Si ya no hay paréntesis abiertos, volvemos al estado inicial
                return symbol(sym.MATH_EXP, string.toString());
            }
        }
    }

    <MATH_FUNCTION> {
        "x"                     { string.append("x"); }
        "+"                     { string.append("+"); }
        "-"                     { string.append("-"); }
        "*"                     { string.append("*"); }
        "/"                     { string.append("/"); }
        "^"                     { string.append("^"); }
        "("                     { string.append("("); }
        ")"                     { string.append(")"); }
        {DecFloatLiteral}       { string.append(yytext()); }
        {DecIntegerLiteral}     { string.append(yytext()); }
        ","                     { yybegin(YYINITIAL); return symbol(sym.MATH_FUNCTION, string.toString()); }
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