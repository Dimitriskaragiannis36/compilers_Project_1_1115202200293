import java_cup.runtime.*;
import java.io.*;

class OK2 {
    public static void main(String[] argv) throws Exception{
        System.out.println("Please type your string expression:");
        Parser p = new Parser(new Scanner(new InputStreamReader(System.in)));
        p.parse();
    }
}
