import java.io.*;
import java_cup.runtime.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Δημιουργία lexer
            Lexer lexer = new Lexer(new InputStreamReader(System.in));

            // Δημιουργία parser και σύνδεση με lexer
            parser p = new parser(lexer);

            // Εκκίνηση της ανάλυσης
            p.parse();
            
            System.out.println("Parsing completed successfully!");
        } catch (Exception e) {
            System.err.println("Parsing failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
