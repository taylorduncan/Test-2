package Analyzer;


/** 
 * Creating the lexical and syntax analyzer for a programming language that will be defined
in this problem. This language will be able to create variables, assign them value,
calculate basic mathematic operations and relational operations for integers of different
types, as well as variables that can be either.
This program should be able to not only recognize the following operations but have a
proper evaluation order that conforms to the real-life principles of mathematics for in
order operations:
a. Addition
b. Subtraction
c. Multiplication
d. Division
e. Module
f. Less than
g. Greater than
h. Less than Equal To
i. Greater than Equal To
j. Equal To
k. Not Equal To
l. Assignment
m. (There must also be a way to break precedence, this is usually done with the use
of parenthesis)
 * - 
 *
 */


import java.util.*;

import java.util.Scanner;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Analyzer {

	/* Global declarations */
	/* Variables */
	public static String charClass;
	public static char[] lexeme = new char[100];
	public static char nextChar;
	public static int lexLen;
	public static String token;
	public static String nextToken;
	static int index = 0;
	static String line;
	public String[] Keywords_ = {"for","while","int"};
	
	
	/* character classes */
	public static final String LETTER = "LETTER";
	public static final String DIGIT = "DIGIT";
	public static final String UNKNOWN = "UNKOWN";
	
	
	/* Token codes */
	public static final String INT_LIT = "0";
	public static final String IDENT = "1";
	public static final String ASSIGN_OP = "2";
	public static final String ADD_OP = "3";
	public static final String SUB_OP = "4";
	public static final String MULT_OP = "5";
	public static final String DIV_OP = "6";
	public static final String MOD_OP = "7";
	public static final String LESSTHAN_OP = "8";
	public static final String GRTRTHAN_OP = "10";
	public static final String LESSTHANE_OP = "9";
	public static final String WHILE_loop = "while";
	public static final String FOR_loop = "for";
	public static final String LEFT_PAREN = "11";
	public static final String RIGHT_PAREN = "12";
	public static final String EOF = "EOF";
	
	
	/* RULES 
	 * start with <stmt> --> <if_stmt> | <while_stmt> | <as_s> | <block>  
	 * 
	 * 
	 */
		

		/* main driver */
		public static void main(String[] args) throws IOException {
			try {
				System.out.println("Taylor Duncan");
				System.out.println("Test 2 \n");
				/* Open the input data file and process its contents 
				 * Paths for files: 
				 * 
				 * /Users/Alexandria/Documents/Fall2022/Test2/peerfect1.txt
				 * /Users/Alexandria/Documents/Fall2022/Test2/peerfect2.txt
				 * /Users/Alexandria/Documents/Fall2022/Test2/lexicalerrors.txt
				 * /Users/Alexandria/Documents/Fall2022/Test2/syntaxerrors.txt
				 * 
				 * practice 
				 * /Users/Alexandria/Documents/Fall2022/Example/src/lexicalAnalyzerPackage/tfile.txt
				 * */
				
				BufferedReader inputFile = new BufferedReader(new FileReader("/Users/Alexandria/Documents/Fall2022/Test2/peerfect1.txt"));
				while ((line = inputFile.readLine()) != null) {
					index = 0;
					System.out.println(("Input: ") + line);
					
					getChar();
					
					do {
						lex();
					} while (nextToken != EOF);
					System.out.println("	NEXT LINE IN TEXT FILE"); // 80
				} // end of while loop

			} // end of try loop

			catch (IOException e) {
				System.out.println("ERROR - cannot open file");
			}
			System.out.printf("Neext token is: %-15s Next lexeme is ", nextToken);
			System.out.println(lexeme);
			System.out.println("Done!");
		}// end of main
		
		/* lookup: lookup operators and parentheses, token*/
		static String lookup(char ch) {
			switch (ch) {
			case '(':
				addChar();
				nextToken = LEFT_PAREN;
				break;
			case ')':
				addChar();
				nextToken = RIGHT_PAREN;
				break;
			case '+':
				addChar();
				nextToken = ADD_OP;
				break;
			case '-':
				addChar();
				nextToken = SUB_OP;
				break;
			case '*':
				addChar();
				nextToken = MULT_OP;
				break;
			case '/':
				addChar();
				nextToken = DIV_OP;
				break;
			case '%':
				addChar();
				nextToken = MOD_OP;
				break;	
			case '<':
				addChar();
				nextToken = LESSTHAN_OP;
				break;	
			case '>': 
				addChar();
				nextToken = GRTRTHAN_OP;
				break;		
			case '=':
				addChar();
				nextToken = ASSIGN_OP;
				break;
			case ' ': 
				System.out.print("Syntax INVALID: ");
				break;
			default:
				System.out.print("INVALID: doesnt start with a digit, letter, operator, or keyword");
				addChar();
				nextToken = EOF;
				break;
				
				
			}
			
			
			return nextToken;
		}

		
		// addChar - add nextChar to lexeme
		static void addChar() {
			if (lexLen <= 98) {
				lexeme[lexLen++] = nextChar;
				lexeme[lexLen] = 0;
			} else
				System.out.println("Error - lexeme is too long \n");
		}

		
		/*
		 * getChar - get next char of input and see if letter, digit, unknown
		 */
		static void getChar() throws IOException {
			if (checkIfExist()) {
				if (Character.isLetter(nextChar))
					charClass = LETTER;
					
				else if (Character.isDigit(nextChar))
					charClass = DIGIT;
				
				else
		
					charClass = UNKNOWN;
				//System.out.println("here"); 	
			} else
				
				charClass = EOF;
		}// end of getChar
		
	
		private static boolean checkIfExist() throws java.lang.StringIndexOutOfBoundsException {
			try {
				nextChar = line.charAt(index);
				index++;
				return true;
			} catch (java.lang.StringIndexOutOfBoundsException e) {
				return false;
			}
		}

		
		/*
		 * getNonBlank - call getChar until it returns a non-whitespace character
		 * 
		 */
		static void getNonBlank() throws IOException {
			while (Character.isWhitespace(nextChar))
				getChar();
		}
		
		
		// lex - a simple lexical analyzer for arithmetic
		static int lex() throws IOException {
			List<Integer> list = new ArrayList<Integer> ();
			lexLen = 0;
			lexeme = new char[100]; 
			getNonBlank();
			switch (charClass) {
			
			/* Parse identifiers */
			case LETTER:
				addChar();
				getChar();
			//	list.add()
				while (charClass == LETTER || charClass == DIGIT) {
					addChar();
					getChar();
					
				}
				
				/* */
				nextToken = IDENT;

			//String stmt=String.valueOf(lexeme);
			//System.out.println(nextToken);
		//	System.out.println(stmt);
				break;
			/* Parse integer literals */
			case DIGIT:
				addChar();
				getChar();
				while (charClass == DIGIT) {
					addChar();
					getChar();
				}
				nextToken = INT_LIT;
				break;
			/* Parentheses and operators */
			case UNKNOWN:
				lookup(nextChar);
				getChar();
				break;
			/* EOF */
			case EOF:
				
				nextToken = EOF;
				lexeme[0] = 'E';
				lexeme[1] = 'O';
				lexeme[2] = 'F';
				lexeme[3] = 0;
				break;
			} /* End of switch */
			
			if (nextToken != EOF) {
				System.out.printf("token code is: %-15s Next lexeme is ", nextToken);
				System.out.println(lexeme);
			}
			return 0;
		} // End of function lex
		
		

}