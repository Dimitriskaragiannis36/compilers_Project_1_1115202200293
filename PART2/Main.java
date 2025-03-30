import java.io.InputStreamReader;

class Main {
    Main() {
    }

    public static void main(String[] var0) throws Exception {
        System.out.println("Please type your string expression OK2:");
        
        // Δημιουργούμε το Scanner και τον Parser
        Scanner2 scanner = new Scanner2(new InputStreamReader(System.in));
        
        java_cup.runtime.Symbol token;
        
        // Διαβάζουμε και εκτυπώνουμε τα tokens
        while ((token = scanner.next_token()).sym != sym.EOF) {
            System.out.println("Token: " + sym.terminalNames[token.sym] + " | Value: " + token.value);
        }

        // Όταν φτάσουμε στο EOF, το token θα έχει τύπο sym.EOF
        System.out.println("EOF reached. Token: " + sym.terminalNames[token.sym] + " | Value: " + token.value);
    
    
        
        /*// Εκτελούμε τον parser για να αναλύσει την είσοδο
        Parser parser = new Parser(scanner);
        try {
            parser.parse();
        } catch (Exception e) {
            System.err.println("Parsing error: " + e.getMessage());
            e.printStackTrace();
        }*/
        
    }
}
