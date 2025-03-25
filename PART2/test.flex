%%
%class test
%public
%unicode

WHITESPACE = [ \t\f\r\n]+
DIGIT = [0-9]+
%%
<YYINITIAL> {
    {WHITESPACE} { /* αγνόηση λευκών χαρακτήρων */ }
    {DIGIT} { System.out.println("Αριθμός: " + yytext()); }
}
