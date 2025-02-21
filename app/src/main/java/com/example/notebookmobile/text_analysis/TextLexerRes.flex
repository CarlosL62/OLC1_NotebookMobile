
%%

%class TextLexer
%unicode
%public

%%

// Whitespaces
[ \t\n\r\f]+ { /* ignore */ }

// Headers
"######" { System.out.println("HEADER6"); }
"#####" { System.out.println("HEADER5"); }
"####" { System.out.println("HEADER4"); }
"###" { System.out.println("HEADER3"); }
"##" { System.out.println("HEADER2"); }
"#" { System.out.println("HEADER1"); }

// Bold
"**" { System.out.println("BOLD"); }

// Italic
"*" { System.out.println("ITALIC"); }

// Lists
"+" { System.out.println("LIST"); }

// Text
. { System.out.println("TEXT"); }