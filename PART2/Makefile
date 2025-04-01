CUP_JAR=C:\Users\dimit\Downloads\java-cup-11b.jar
CUP_RUNTIME_JAR=C:\Users\dimit\Downloads\java-cup-11b-runtime.jar
JFLEX_JAR=C:\Users\dimit\Downloads\jflex-1.9.1\lib\jflex-full-1.9.1.jar

#προφανώς έχω βάλει το δικό μου μονοπάτι στα .flex και .cup
all: lexer parser parser2 compile

lexer:
	java -jar $(JFLEX_JAR) scanner.flex

parser:
	java -jar $(CUP_JAR) -parser Parser -symbols sym parser.cup

parser2:
	java -jar $(CUP_JAR) -parser Parser2 -symbols sym parser2.cup

compile:
	javac -cp "$(CUP_RUNTIME_JAR);." *.java

run:
	java -cp "$(CUP_RUNTIME_JAR);." Main

clean:
	del /Q *.class sym.java Scanner.java Parser.java Parser2.java 2 Translated.ir Translated.java> NUL