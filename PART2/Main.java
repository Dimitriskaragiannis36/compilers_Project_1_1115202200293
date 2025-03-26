import java.io.FileReader;
import java_cup.runtime.Symbol;

public class Main {
    public static void main(String[] args) {
        try {
            // Άνοιγμα αρχείου εισόδου
            FileReader reader = new FileReader("input.txt");

            // Δημιουργία lexer και parser
            Lexer lexer = new Lexer(reader);
            Parser parser = new Parser(lexer);

            // Εκτέλεση ανάλυσης
            Symbol result = parser.parse();
            System.out.println("Parsing completed successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
