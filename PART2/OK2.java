import java_cup.runtime.*;
import java.io.*;

class OK2 {
    public static void main(String[] argv) throws Exception {
        System.out.println("Please type your string expression:");

        // Δημιουργία αρχείου εξόδου για το IR
        PrintWriter writer = new PrintWriter(new FileWriter("Translated.ir"));

        // Δημιουργία του πρώτου parser και σύνδεση με τον scanner
        Parser p = new Parser(new Scanner(new InputStreamReader(System.in)));  // Scanner για τον πρώτο parser

        try {
            // Ανάλυση και αποθήκευση εξόδου στο αρχείο Translated.ir
            Symbol result = p.parse();
            System.out.println("Result value: " + result.value); // Debugging
            writer.println(result.value);  // Αποθηκεύει το αποτέλεσμα στο αρχείο IR
            System.out.println("Parsing completed. Output saved to Translated.ir");

        } catch (Exception e) {
            System.err.println("Parsing error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            writer.close();
        }

        // Έλεγχος αν το αρχείο IR γράφτηκε σωστά
        File file = new File("Translated.ir");
        System.out.println("File exists: " + file.exists() + ", Size: " + file.length());

        // Δημιουργία του δεύτερου parser για μετατροπή IR σε Java
        Scanner scanner2 = new Scanner(new FileReader("Translated.ir"));
        Symbol token;
        while ((token = scanner2.next_token()).sym != sym.EOF) {  
            System.out.println("Token: " + token);
        }
        PrintWriter writerJava = new PrintWriter(new FileWriter("Translated.java"));
        Parser2 p2 = new Parser2(new Scanner(new FileReader("Translated.ir")));  

        try {
            Symbol result2 = p2.parse();
            writerJava.println(result2.value);  // Αποθηκεύει τον Java κώδικα στο αρχείο
            System.out.println("Parsing to Java completed. Output saved to Translated.java");
        } catch (Exception e) {
            System.err.println("Parsing to Java error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            writerJava.close();
        }
    }
}
