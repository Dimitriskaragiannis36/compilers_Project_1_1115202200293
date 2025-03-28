import java_cup.runtime.*;
import java.io.*;

class OK2 {
    public static void main(String[] argv) throws Exception {
        System.out.println("Please type your string expression:");

        // Δημιουργία αρχείου εξόδου
        PrintWriter writer = new PrintWriter(new FileWriter("Translated.ir"));

        // Δημιουργία parser
        Parser p = new Parser(new Scanner(new InputStreamReader(System.in)));

        try {
            // Ανάλυση και αποθήκευση εξόδου
            Symbol result = p.parse();  
            writer.println(result.value);  // Αποθηκεύει το αποτέλεσμα στο αρχείο
            System.out.println("Parsing completed. Output saved to Translated.ir");
        } catch (Exception e) {
            System.err.println("Parsing error: " + e.getMessage());
        } finally {
            writer.close();
        }
    }
}
