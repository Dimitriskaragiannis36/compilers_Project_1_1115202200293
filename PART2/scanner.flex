import java_cup.runtime.*;

%%

%class Scanner
%line
%column
%cup
%unicode

%{
    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
    }
    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
    }
%}

LineTerminator = \r|\n|\r\n
WhiteSpace     = {LineTerminator} | [ \t\f]
Identifier     = [a-zA-Z_][a-zA-Z_0-9]*
/* Αυτούσιο literal με τα εισαγωγικά και τα escapes (\" \\ \n ...), ώστε
   να είναι ήδη έγκυρο Java string literal στην έξοδο */
StringLiteral  = \"([^\"\\\r\n]|\\.)*\"

%%

"if"        { return symbol(sym.IF); }
"else"      { return symbol(sym.ELSE); }
"prefix"    { return symbol(sym.PREFIX); }
"suffix"    { return symbol(sym.SUFFIX); }
"reverse"   { return symbol(sym.REVERSE); }
"="         { return symbol(sym.EQUAL); }
"+"         { return symbol(sym.CONCAT); }
"("         { return symbol(sym.LPAREN); }
")"         { return symbol(sym.RPAREN); }
"{"         { return symbol(sym.LBRACE); }
"}"         { return symbol(sym.RBRACE); }
","         { return symbol(sym.COMMA); }

{Identifier}    { return symbol(sym.ID, yytext()); }
{StringLiteral} { return symbol(sym.STRING_LITERAL, yytext()); }

{WhiteSpace}    { /* αγνόησε */ }

[^]             { throw new RuntimeException("Lexical error at line " + (yyline + 1)
                      + ", column " + (yycolumn + 1) + ": illegal character <" + yytext() + ">"); }