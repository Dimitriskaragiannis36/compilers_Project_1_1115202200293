%{     //Προκαταρκτικός κώδικας
    package PART2;
    
    //κάνω import για τo sym (tokens που θα ορίσουμε)
    import java_cup.runtime.Symbol;    //jflex ->java_cup σύμβολα
    import parser.sym;   //για javacup συμβολικά ονόματα
%}

%%     //Ρυθμίσεις & Ορισμοί

//Ρυθμίσεις JFlex
%class Lexer
%public
%final       //δεν μπορεί να κάνει extend
%unicode     //για χαρακτήρες unicode (A=U+0041)
%cup         //για java_cup

//Ορισμός χαρακτήρων
LineTerminator = \r|\n|\r\n
WhiteSpace     = {LineTerminator} | [ \t\f]  \\f=form feed
Identifier     = [a-zA-Z_][a-zA-Z_0-9]*
StringLiteral  = \"([^\"\\]|\\.)*\"  
//θυμήσου ^=not, escape \

//Tokens
%%    //Κανόνες Ανάλυσης

//Λέξεις κλειδία (Δεσμευμένες λέξεις)
"if"       { return new Symbol(sym.IF); }
"else"     { return new Symbol(sym.ELSE); }
"prefix"   { return new Symbol(sym.PREFIX); }
"suffix"   { return new Symbol(sym.SUFFIX); }

//Τελεστές
"+"        { return new Symbol(sym.CONCAT); }
"~"        { return new Symbol(sym.REVERSE); }
"="        { return new Symbol(sym.EQ); }

//Identifiers και λεκτικά (yytext η τιμή)
{Identifier}    { return new Symbol(sym.ID, yytext()); }
{StringLiteral} { return new Symbol(sym.STRING, yytext()); }

//Κενά (αγνοούνται)
{WhiteSpace}    { /* skip */ }  // /* skip */=καμία ενέργεια

//Χαρακτήρες που δεν αναγνωρίζονται
. { System.err.println("Unexpected character: " + yytext()); }
//. οποιοσδήποτε χαρακτήρας