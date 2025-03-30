import java_cup.runtime.*;
import java.io.*;

class OK2 {
    public static void main(String[] argv) throws Exception {
        System.out.println("Please type your string expression:");

        // Δημιουργία αρχείου εξόδου για το IR
        PrintWriter writer = new PrintWriter(new FileWriter("Translated.ir"));

        // Δημιουργία του πρώτου parser και σύνδεση με τον scanner
        Parser p = new Parser(new Scanner(new InputStreamReader(System.in)));

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

        // Διαβάζουμε το IR και το μετατρέπουμε σε Java
        StringBuilder irContent = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader("Translated.ir"));
        String line;
        while ((line = br.readLine()) != null) {
            irContent.append(line).append("\n");
        }
        br.close();

        System.out.println("IR content read from file: \n" + irContent.toString());

        // Εξασφάλιση ότι δεν υπάρχουν κενά
        String irString = irContent.toString().trim();
        System.out.println("Scanner2 Input (Trimmed):\n" + irString);

        // Δημιουργία του δεύτερου scanner και εκτύπωση των tokens που αναγνωρίζει
        Scanner2 scannerIR = new Scanner2(new StringReader(irString));
        java_cup.runtime.Symbol token;
        System.out.println("Tokens from Translated.ir:");
        try {
            while ((token = scannerIR.next_token()).sym != 0) {
                System.out.println("Token_scanner2: " + sym.terminalNames[token.sym] + " | Value: " + token.value);
            }
        } catch (Exception e) {
            System.err.println("Scanner2 error: " + e.getMessage());
        }

        // Δημιουργία του δεύτερου parser για μετατροπή IR σε Java
        PrintWriter writerJava = new PrintWriter(new FileWriter("Translated.java"));
        Parser2 p2 = new Parser2(scannerIR);

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