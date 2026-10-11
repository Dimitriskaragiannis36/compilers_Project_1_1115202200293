#φάκελος
SRC_DIR = PART1

#Κύρια κλάση  
MAIN_CLASS = Parser

#Compilers
JAVAC = javac
JAVA = java

#Εντολή
TARGET = $(SRC_DIR)/Parser.class

#Default
all: Parser.class
	$(JAVA) $(MAIN_CLASS)

#Parser.java
Parser.class: Parser.java
	$(JAVAC) Parser.java

#Καθαρισμός
clean:
	rm -f *.class
 