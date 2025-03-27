import java_cup.runtime.*;

%%
/* ----------------- Options and Declarations Section----------------- */

/*
   The name of the class JFlex will create will be Scanner.
   Will write the code to the file Scanner.java.
*/
%class Scanner

/*
  The current line number can be accessed with the variable yyline
  and the current column number with the variable yycolumn.
*/
%line
%column

/*
   Will switch to a CUP compatibility mode to interface with a CUP
   generated parser.
*/
%cup
%unicode

/*
  Declarations

  Code between %{ and %}, both of which must be at the beginning of a
  line, will be copied letter to letter into the lexer class source.
  Here you declare member variables and functions that are used inside
  scanner actions.
*/

%{
    /**
        The following two methods create java_cup.runtime.Symbol objects
    **/
    StringBuffer stringBuffer = new StringBuffer();
    private Symbol symbol(int type) {
       return new Symbol(type, yyline, yycolumn);
    }
    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
    }
%}

/*
  Macro Declarations

  These declarations are regular expressions that will be used latter
  in the Lexical Rules Section.
*/

/* A line terminator is a \r (carriage return), \n (line feed), or
   \r\n. */
LineTerminator = \r|\n|\r\n
Identifier     = [a-zA-Z_][a-zA-Z_0-9]*
StringChar     = [^\n\r\"\\]

/* White space is a line terminator, space, tab, or line feed. */
WhiteSpace     = {LineTerminator} | [ \t\f]

%state STRING

%%
/* ------------------------Lexical Rules Section---------------------- */

<YYINITIAL> {
/* Keywords */   
"if"       { return new Symbol(sym.IF); }
"else"     { return new Symbol(sym.ELSE); }
"and"      { return new Symbol(sym.AND); }

/* operators */
"+"        { return new Symbol(sym.CONCAT); }
"~"        { return new Symbol(sym.REVERSE); }
"("        { return new Symbol(sym.LPAREN); }
")"        { return new Symbol(sym.RPAREN); }
"{"        { return new Symbol(sym.LBRACE); }
"}"        { return new Symbol(sym.RBRACE); }
","        { return new Symbol(sym.COMMA); }
";"        { return new Symbol(sym.SEMI); }

/* Identifiers */
{Identifier} { return symbol(sym.ID, yytext()); }

/* String literals */
\"         { yybegin(STRING); stringBuffer.setLength(0); }

}

{WhiteSpace} { /* just skip what was found, do nothing */ }

<STRING> {
      \"                             { yybegin(YYINITIAL);
                                       return symbol(sym.STRING_LITERAL, stringBuffer.toString()); }
      {StringChar}+                    { stringBuffer.append( yytext() ); }
      \\t                            { stringBuffer.append('\t'); }
      \\n                            { stringBuffer.append('\n'); }

      \\r                            { stringBuffer.append('\r'); }
      \\\"                           { stringBuffer.append('\"'); }
      \\\\                           { stringBuffer.append('\\'); }
}

/* No token was found for the input so through an error.  Print out an
   Illegal character message with the illegal character that was found. */
[^]                    { throw new Error("Illegal character <"+yytext()+">"); }



