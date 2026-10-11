import java.io.IOException;
import java.io.InputStream;

public class Parser {
    private final InputStream in;
    private int lookahead;

    public Parser(InputStream in) throws IOException {
        this.in = in;
        this.lookahead = in.read();
    }

    public void parse() {
        try {
            skipWhitespace();
            int result = exp();
            skipWhitespace();
            // Αν φτάσαμε στο τέλος της έκφρασης με επιτυχία
            if (lookahead == -1 || lookahead == '\n' || lookahead == '\r') {
                System.out.println(result);
            } else {
                error(); // Αν περισσεύουν "σκουπίδια" στο τέλος (π.χ. 1 + 2 a)
            }
        } catch (IOException e) {
            System.err.println("IO error: " + e.getMessage());
        }
    }

    private void skipWhitespace() throws IOException {
        while (Character.isWhitespace(lookahead) && lookahead != '\n' && lookahead != '\r') {
            lookahead = in.read();
        }
    }

    private void consume(int expected) throws IOException {
        skipWhitespace();
        if (lookahead == expected) {
            lookahead = in.read();
            if (lookahead == '\r') { // Διαχείριση Windows CRLF
                lookahead = in.read();
            }
        } else {
            error();
        }
    }

    private void error() {
        System.err.println("parse error");
        System.exit(1);
    }

    private int exp() throws IOException {
        int value = term();
        return exp2(value);
    }

    private int exp2(int left) throws IOException {
        skipWhitespace();
        if (lookahead == '+' || lookahead == '-') {
            int op = lookahead;
            consume(lookahead);
            int right = term();
            if (op == '+') {
                left += right;
            } else if (op == '-') {
                left -= right;
            }
            return exp2(left);
        }
        // ΔΙΟΡΘΩΣΗ ΕΔΩ: Αλλαγή σε == για να ελέγχει σωστά το FOLLOW(exp2)
        if (lookahead == -1 || lookahead == ')' || lookahead == '\n' || lookahead == '\r') {
            return left;
        }
        error();
        return 0;
    }

    private int term() throws IOException {
        int value = factor();
        return term2(value);
    }

    private int term2(int left) throws IOException {
        skipWhitespace();
        if (lookahead == '*') {
            consume('*');
            if (lookahead == '*') {
                consume('*');
                int right = factor();
                right = term2(right); // Υπολογίζει όλη τη δεξιά μεριά (Right associativity)
                return (int) Math.pow(left, right); // ΔΙΟΡΘΩΣΗ ΕΔΩ: Επιστρέφουμε απευθείας το αποτέλεσμα
            } else {
                error();
            }
        }
        // Σωστός έλεγχος του FOLLOW(term2)
        if (!(lookahead == '+' || lookahead == '-' || lookahead == ')' || lookahead == -1 || lookahead == '\n' || lookahead == '\r')) {
            error();
        }
        return left;
    }

    private int factor() throws IOException {
        skipWhitespace();
        if (Character.isDigit(lookahead)) {
            return num();
        } else if (lookahead == '(') {
            consume('(');
            int value = exp();
            skipWhitespace();
            if (lookahead == ')') {
                consume(')');
            } else {
                error();
            }
            return value;
        } else {
            error();
            return 0;
        }
    }

    private int num() throws IOException {
        int value = digit();
        return num2(value);
    }

    private int num2(int left) throws IOException {
        skipWhitespace();
        if (Character.isDigit(lookahead)) {
            left = left * 10 + digit();
            return num2(left);
        }
        return left;
    }

    private int digit() throws IOException {
        skipWhitespace();
        if (!Character.isDigit(lookahead)) {
            error();
        }
        int value = lookahead - '0';
        consume(lookahead);
        return value;
    }

    public static void main(String[] args) {
        try {
            Parser parser = new Parser(System.in);
            parser.parse();
        } catch (IOException e) {
            System.err.println("IO error: " + e.getMessage());
        }
    }
}