import java.io.*;

import java_cup.parser;
import java_cup.runtime.*;

public class ParserTest {
    public static void main(String[] args) {
        try {
            //Ανάγνωση από stdin 
            Reader input;
            if (args.length > 0) {
               input = new InputStreamReader(System.in);
            } 

            //Δημιουργία lexer & parser όπως στην τάξη
            Lexer lexer = new Lexer(input);
            parser parser = new parser(lexer);

            //Εκτέλεση του parser
            Symbol result = parser.parse();
            System.out.println("όλα κομπλέ");

        } catch (Exception e) {
            System.err.println("Parsing failed: " + e.getMessage());
        }
    }
}
