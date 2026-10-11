import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

/**
 * Διαβάζει πρόγραμμα από stdin, γράφει το IR στο Translated.ir
 * και τον Java κώδικα στο Translated.java.
 */
public class Main {
    public static void main(String[] argv) {
        try {
            // Στάδιο 1: πηγαία γλώσσα -> IR
            Reader in = new InputStreamReader(System.in, StandardCharsets.UTF_8);
            Parser p1 = new Parser(new Scanner(in));
            String ir = (String) p1.parse().value;
            Files.write(Paths.get("Translated.ir"), (ir + "\n").getBytes(StandardCharsets.UTF_8));

            // Στάδιο 2: IR -> Java (διαβάζεται από το αρχείο που μόλις γράφτηκε)
            Reader irIn = Files.newBufferedReader(Paths.get("Translated.ir"), StandardCharsets.UTF_8);
            Parser2 p2 = new Parser2(new Scanner(irIn));
            String java = (String) p2.parse().value;
            Files.write(Paths.get("Translated.java"), java.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}