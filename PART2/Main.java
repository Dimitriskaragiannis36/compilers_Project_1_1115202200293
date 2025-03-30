import java_cup.runtime.*;
import java.io.*;

class OK2 {
    public static void main(String[] argv) throws Exception {
        System.out.println("Please type your string expression:");

        // Δημιουργία αρχείου εξόδου για την IR
        PrintWriter writerIR = new PrintWriter(new FileWriter("Translated.ir"));

        // 1ος Parser: Μετατροπή εισόδου σε IR
        Parser p1 = new Parser(new Scanner(new InputStreamReader(System.in)));

        try {
            Symbol result1 = p1.parse();  
            writerIR.println(result1.value);  // Αποθηκεύει την IR στο αρχείο
            System.out.println("Parsing to IR completed. Output saved to Translated.ir");
        } catch (Exception e) {
            System.err.println("Parsing to IR error: " + e.getMessage());
            return;
        } finally {
            writerIR.close();
        }

        // 2ος Parser: Μετατροπή IR σε Java
        BufferedReader readerIR = new BufferedReader(new FileReader("Translated.ir"));
        PrintWriter writerJava = new PrintWriter(new FileWriter("Translated.java"));

        Parser2 p2 = new Parser2(new ScannerIR(readerIR));  // ScannerIR = Lexer για την IR

        try {
            Symbol result2 = p2.parse();
            writerJava.println(result2.value);  // Αποθηκεύει τον Java κώδικα στο αρχείο
            System.out.println("Parsing to Java completed. Output saved to Translated.java");
        } catch (Exception e) {
            System.err.println("Parsing to Java error: " + e.getMessage());
        } finally {
            writerJava.close();
            readerIR.close();
        }
    }
}
