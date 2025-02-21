package com.example.notebookmobile.code_analysis;

%%

%class CodeLexer
%unicode
%cup

%%

"int"                 { return new java_cup.runtime.Symbol(sym.INT_KEYWORD); }
[a-zA-Z_][a-zA-Z0-9_]* { return new java_cup.runtime.Symbol(sym.IDENTIFIER, yytext()); }
"="                   { return new java_cup.runtime.Symbol(sym.ASSIGN); }
[0-9]+                { return new java_cup.runtime.Symbol(sym.NUMBER, Integer.parseInt(yytext())); }
[ \t\r\n]+            { /* Ignorar espacios en blanco */ }
<<EOF>>               { return new java_cup.runtime.Symbol(sym.EOF); }
