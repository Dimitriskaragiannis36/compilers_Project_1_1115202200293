# Τα jar βρίσκονται στον ίδιο φάκελο με τα αρχεία (PART2).
# Οι εντολές κάτω από κάθε target ξεκινούν με ">" (όχι tab), ώστε να δουλεύει το copy-paste.
.RECIPEPREFIX = >

CUP_JAR         ?= java-cup-11b.jar
CUP_RUNTIME_JAR ?= java-cup-11b-runtime.jar
JFLEX_JAR       ?= jflex-full-1.9.1.jar

JFLEX ?= java -jar $(JFLEX_JAR)
CUP   ?= java -jar $(CUP_JAR)
CP     = .:$(CUP_RUNTIME_JAR)

# Αρχείο εισόδου για το "make run"
INPUT ?= input.txt

.NOTPARALLEL:
.PHONY: all run clean

all: Main.class

Scanner.java: scanner.flex
> $(JFLEX) scanner.flex

Parser.java: parser.cup
> $(CUP) -parser Parser -symbols sym parser.cup

# Ο δεύτερος parser ξαναγράφει το sym.java (ίδια terminals => ίδιο περιεχόμενο)
Parser2.java: parser2.cup Parser.java
> $(CUP) -parser Parser2 -symbols sym parser2.cup

Main.class: Main.java Ex.java Scanner.java Parser.java Parser2.java
> javac -encoding UTF-8 -cp "$(CP)" Main.java Ex.java Scanner.java Parser.java Parser2.java sym.java

# make run               : μεταφράζει το input.txt, μεταγλωττίζει και τρέχει το Translated.java
# make run INPUT=αρχείο  : το ίδιο για άλλο αρχείο εισόδου
run: all
> java -cp "$(CP)" Main < $(INPUT)
> javac -encoding UTF-8 Translated.java
> java Translated

clean:
> rm -f *.class sym.java Scanner.java Scanner.java~ Parser.java Parser2.java Translated.ir Translated.java