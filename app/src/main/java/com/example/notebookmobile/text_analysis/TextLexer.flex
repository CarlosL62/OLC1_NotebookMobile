package com.example.notebookmobile.text_analysis;

%%

%class TextLexer
%unicode
%cup

%%

[a-zA-Z]+              { return new java_cup.runtime.Symbol(sym.WORD, yytext()); }
[ \t\r\n]+             { /* Ignorar espacios en blanco */ }
<<EOF>>                { return new java_cup.runtime.Symbol(sym.EOF); }
