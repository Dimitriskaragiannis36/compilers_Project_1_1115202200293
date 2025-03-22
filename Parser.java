import java.io.IOException;  //βιβλιοθήκη για εξαίρεση-αν δεν την βάλω θα έχω θέμα
import java.io.InputStream;  //βιβλιοθήκη για εισαγωγή από πληκτρολόγιο

public class Parser {  //κυρίαρχη κλάση=όνομα αρχείου
    private InputStream in;  //μεταβλητή εισόδου
    private int lookahead;  //int γιατί το in.read() διαβάζει μόνο int

    public Parser(InputStream in) throws IOException {  //ο κατασκευαστής 
        this.in = in;  //το this είναι της κλάσης ενώ το δεξιά in το όρισμα
        this.lookahead = in.read();  
    }

    //Ο συντακτικός αναλυτής
    public void parse() {
        try {
            System.out.println("Ξεκινάμε...");  
            System.out.printf("αρχικό σύμβολο: %d\n", lookahead); 
            exp(); 
            if (lookahead == '\n' || lookahead == -1) {  
                System.out.println("τέλος δοκιμής");  
            } else {
                error();  //συνάρτηση σφάλματος
            }
        } catch (IOException e) {  
            System.err.println("IO error: " + e.getMessage());  //εκτυπώνει το μήνυμα σφάλματος
        }
    }

    // Μέθοδος για μετάβαση στον επόμενο χαρακτήρα
    private void consume(int expected) throws IOException {
        if (lookahead == expected) {
            lookahead = in.read();
        } else {
            error();
        }
    }

    //Συνάρτηση σφάλαμτος
    private void error() {
        System.err.println("parse error"); 
        System.exit(1);  
    }

    //κανόνας (1)
    private void exp() throws IOException {
        while (lookahead != '\n' && lookahead != -1) {  
            System.out.print(lookahead);  
            lookahead = in.read();  
        }
        term();   
        exp2();  
    }

    //κανόνας (2,3,4) 
    private void exp2() throws IOException {
        //έπεται
    }

    //κανόνας (5)
    private void term() throws IOException {
        //έπεται
    }

    //main
    public static void main(String[] args) {
        try {
            Parser parser = new Parser(System.in);  // Δημιουργεί ένα νέο αντικείμενο Parser με είσοδο από το πληκτρολόγιο.
            parser.parse();  //καλώ την συνάρτηση του parser
        } catch (IOException e) {  
            System.err.println("Πρόβλημα στην εκκίνηση του parser: " + e.getMessage());  
        }
    }
}
