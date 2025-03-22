package PART1;
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
            System.out.printf("αρχικό σύμβολο: %c\n", lookahead); //αλλιώς εμφανίζει ASCII 
            exp(); 
            /*if (lookahead == '\n' || lookahead == -1) {  
                System.out.println("τέλος δοκιμής");  
            } else {
                System.out.println("έως εδώ");
                error();  //συνάρτηση σφάλματος
                
            }*/
        } catch (IOException e) {  
            System.err.println("IO error: " + e.getMessage());  //εκτυπώνει το μήνυμα σφάλματος
        }
    }

    //Συνάρτηση για μετάβαση στον επόμενο χαρακτήρα (τρώει το 1;)
    private void consume(int expected) throws IOException {
        if (lookahead == expected) {
            System.out.printf("κατανάλωσα το %c\n", lookahead);
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
    private int exp() throws IOException {
        int value = term();   
        return exp2(value); 
    }

    //κανόνας (2,3,4) 
    private int exp2(int left) throws IOException {
        if (lookahead == '+' || lookahead == '-') {
            int op = lookahead;
            int value;
            consume(lookahead);  //καταναλώνει το σύμβολο + ή -
            int right = term();
            if (op == '+') {
                value = left + right;
            } else {
                value = left - right;
            } 
            return exp2(value);  // Αφαίρεση
        }
        return left;
    }

    //κανόνας (5)
    private int term() throws IOException {
        int value = factor();   
        return term2(value); 
    }

    //κανόνας (6)
    private int term2(int left) throws IOException {
        if (lookahead == '*') {
            consume('*');  //καταναλώνω το πρώτο *
            if (lookahead == '*') {
                consume('*');  //πρέπει να υπάρχει και δεύτερο *
                int right = factor();
                int pow = (int)Math.pow(left,right);  //int γιατί γκρινιάζει για double
                term2(pow);  
            } else {
                error();  //αν υπάρχει μόνο ένα *, είναι συντακτικό λάθος
            }
        }
        return left;
    }

    //κανόνας (8,9)   //δεν έβαλα ακόμα κανόνα 13-22
    private int factor() throws IOException {
        if (Character.isDigit(lookahead)) {
            int value = lookahead - '0';  //χαρακτήρας γίνεται αριθμός
            consume(lookahead);  //καταναλώνει το num
            return value;
        } else if (lookahead == '(') {
            consume('(');
            int value = exp();  //ακολουθεί exp
            consume(')'); 
            return value; 
        } else {
            error();  
            return 0; 
        }
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
