##==============================================================================
## Jotto
##
## @description: Makefile for Jotto.java
## @author: Elisha Lai
## @version: 1.0 05/11/2015
##==============================================================================

all:
	@echo "Compiling..."
	javac -Xlint:unchecked *.java

run: all
	@echo "Running..."
	java Jotto

.PHONY: clean

clean:
	@echo "Cleaning..."
	find . -name "*.class" -delete
