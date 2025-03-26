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
            System.out.println("Εκκίνηση αριθμομηχανής");
            skipWhitespace(); //μπορεί να έχω κενά στην αρχή
            int result = exp();
            skipWhitespace(); //μπορεί να έχω κενά και στο τέλος 
            if (lookahead == -1 || lookahead == '\n'|| lookahead == '\r') { //για EOF σύμφωνα με project (newline or EOF)
                System.out.println("Τέλος υπολογισμού");
                System.out.println(result);  
            } else {
                System.out.println("Σφάλμα");
                error();  //συνάρτηση σφάλματος
                
            }
        } catch (IOException e) {  
            System.err.println("IO error: " + e.getMessage());  //εκτυπώνει το μήνυμα σφάλματος
        }
    }

    //Συνάρτηση προσπέρασης κενών
    private void skipWhitespace() throws IOException {
        while (Character.isWhitespace(lookahead) && lookahead != '\n' && lookahead != '\r') {
            lookahead = in.read(); //για να τερματίζει ακόμα και με κενό στο τέλος
        }
    }

    //Συνάρτηση για μετάβαση στον επόμενο χαρακτήρα 
    private void consume(int expected) throws IOException {
        skipWhitespace();
        if (lookahead == expected) {
            System.out.printf("κατανάλωσα το %c\n", lookahead);
            lookahead = in.read(); // δίνει το επόμενο τοκεν
            if (lookahead == '\r') {  //αν είναι \r (Windows), διάβασε και το \n
                lookahead = in.read(); //δίνει το επόμενο τοκεν
            }    
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
        skipWhitespace();
        if (lookahead == '+' || lookahead == '-') {
            int op = lookahead;
            consume(lookahead);  //καταναλώνει το σύμβολο + ή -
            int right = term();
            if (op == '+') {
                left += right;
            } else if (op == '-') {
                left -= right;
            } 
            return exp2(left);
        }
        if (lookahead == -1 || lookahead == ')' || lookahead != '\n' || lookahead != '\r') {  //για το FOLLOW
            return left;
        }
        error();
        return 0;
    }

    //κανόνας (5)
    private int term() throws IOException {
        int value = factor();   
        return term2(value); 
    }

    //κανόνας (6,7)
    private int term2(int left) throws IOException {
        skipWhitespace();
        if (lookahead == '*') {
            consume('*');  //καταναλώνω το πρώτο *
            if (lookahead == '*') {
                consume('*');  //πρέπει να υπάρχει και δεύτερο *
                int right = factor();  
                right = term2(right); //για δεξιά προτεραιότητα
                int pow = (int) Math.pow(left, right); //int γιατί γκρινιάζει 
                return term2(pow);                
            } else {
                error();  //αν υπάρχει μόνο ένα *, είναι συντακτικό λάθος
            }
        }
        if (!(lookahead == '+' || lookahead == '-' || lookahead == ')' || lookahead == -1 || lookahead == '\n' || lookahead == '\r')) {
            error();
        }
        
        return left;
    }

    //κανόνας (8,9)   
    private int factor() throws IOException {
        skipWhitespace();
        if (Character.isDigit(lookahead)) {
            return num();
        } else if (lookahead == '(') {
            consume('(');
            int value = exp();  //ακολουθεί exp
            skipWhitespace();
            if (lookahead == ')') {
                consume(')');
            } else {
                error(); //δεν είναι σίγουρο αν θα κλείσει η παρένθεση
            } 
            return value; 
        } else {
            error();  
            return 0; 
        }
    }

    //κανόνας (10) 
    private int num() throws IOException {
        int value = digit(); 
        return num2(value);  
    }

    //κανόνας (11,12) 
    private int num2(int left) throws IOException {
        skipWhitespace();
        if (Character.isDigit(lookahead)) {  
            left = left * 10 + digit(); //παράθεση ψηφίων
            return num2(left);  
        }
        return left; 
    }

    //κανόνας (13-22)
    private int digit() throws IOException {
        skipWhitespace();
        if (!Character.isDigit(lookahead)) {
            error();  
        }
        int value = lookahead - '0'; //μετατροπή συμβόλου σε αριθμό 
        consume(lookahead);  
        return value;
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
