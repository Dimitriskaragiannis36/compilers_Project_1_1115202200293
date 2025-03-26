import java_cup.runtime.*;

%%  // Ρυθμίσεις & Ορισμοί

// Ρυθμίσεις JFlex
%class Lexer
%public
%final       // δεν μπορεί να κάνει extend
%unicode     // για χαρακτήρες unicode (A=U+0041)
%cup         // για java_cup

// Ορισμός χαρακτήρων
LineTerminator = \r|\n|\r\n
WhiteSpace     = {LineTerminator} | [ \t\f]  
Identifier     = [a-zA-Z_][a-zA-Z_0-9]*
StringLiteral  = \"([^\"\\]|\\.)*\"  

%%    // Κανόνες Ανάλυσης

// Λέξεις κλειδία (Δεσμευμένες λέξεις)
"if"       { return new Symbol(sym.IF); }
"else"     { return new Symbol(sym.ELSE); }
"prefix"   { return new Symbol(sym.PREFIX); }
"and"      { return new Symbol(sym.AND); }

// Τελεστές
"+"        { return new Symbol(sym.CONCAT); }
"~"        { return new Symbol(sym.REVERSE); }

// Σύμβολα
"("        { return new Symbol(sym.LPAREN); }
")"        { return new Symbol(sym.RPAREN); }
"{"        { return new Symbol(sym.LBRACE); }
"}"        { return new Symbol(sym.RBRACE); }
","        { return new Symbol(sym.COMMA); }
";"        { return new Symbol(sym.SEMI); }

// Αναγνωριστικά & λεκτικά
{Identifier}    { return new Symbol(sym.ID, yytext()); }
{StringLiteral} { return new Symbol(sym.STRING, yytext()); }

// Αγνόηση κενών
{WhiteSpace}    { /* skip */ }

// Λάθος χαρακτήρας
. { System.err.println("Unexpected character: " + yytext()); }