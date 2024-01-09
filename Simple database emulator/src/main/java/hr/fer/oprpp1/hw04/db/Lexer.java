package hr.fer.oprpp1.hw04.db;



/**
 * lexer
 * @author TeaMadzarac
 * @version 12/11/2022
 *
 */
public class Lexer {

	
	private char[] data;      // ulazni tekst 
	private Token token;      // trenutni token
	private int currentIndex; // indeks prvog neobrađenog znaka
	
	/**
	 * Constructor
	 * @param text that is going to be turned into tokens 
	 */
	public Lexer(String text) { 
		this.data = text.toCharArray();
		this.currentIndex = 0;
	}
	
	/**
	 * returns last generated token, it can be called multiple times, it doesn't start generation of next token
	 * @return returns current token
	 */
	public Token getToken() {
		return this.token;
	}
	
	
	/**
	 * generates and returns next token
	 * throws LexerException if error happens
	 * @return returns next token 
	 */
	public Token nextToken() {  
		generateNextToken();
		return getToken();
	}
	
	/**
	 * generates next token
	 */
	private void generateNextToken() {
		
				
		skipWhiteSpaces();
				
		//if there are no more chars left generate EOF
		if(this.currentIndex >= data.length) {
			this.token = new Token(TokenType.EOF, null);
			return;
		}
		
		//LIKE
		if(this.data[this.currentIndex] == 'L') {
			this.currentIndex++;
			if(this.data[this.currentIndex] == 'I' &&
			   this.data[this.currentIndex +1] == 'K' &&
			   this.data[this.currentIndex +2] == 'E') {
				
				this.currentIndex += 3;
				this.token = new Token(TokenType.OLIKE, "operator");
				
			} else {
				throw new IllegalArgumentException("Invalid query input.");
			}
			return;
		}
		
		
		//AND
		if(this.data[this.currentIndex] == 'a' || this.data[this.currentIndex] == 'A') {
			
			String s = "";
			s +=this.data[this.currentIndex++];
			s +=this.data[this.currentIndex++];
			s +=this.data[this.currentIndex++];
			
			if(s.equalsIgnoreCase("and")) {
				this.token = new Token(TokenType.AND, "and");
				
			} else {
				throw new IllegalArgumentException("Invalid query input.");
			}
			return;
		}
		
		
		//FieldValue
		if(Character.isLetter(this.data[this.currentIndex])) {
			String value = "";
			
			while(this.currentIndex < this.data.length && Character.isLetter(this.data[this.currentIndex]) && this.data[this.currentIndex] != 'L') {
				
				//if next character was a letter
				value += this.data[this.currentIndex++];
			}
			
			if(value.equals("firstName")) {
				this.token = new Token(TokenType.FIRSTNAME, "field");
				
			} else if(value.equals("lastName")) {
				this.token = new Token(TokenType.LASTNAME, "field");
				
			} else if(value.equals("jmbag")) {
				this.token = new Token(TokenType.JMBAG, "field");
				
			} else {
				throw new IllegalArgumentException("Invalid query input.");
			}
			
			return;
		}
		
		//String literal
		if(this.data[this.currentIndex] == '"' ) {
			
			String value = "";
			this.currentIndex++;
			
			while(this.currentIndex < this.data.length && this.data[this.currentIndex] != '"') {
				
				//if next character was a letter
				value += this.data[this.currentIndex++];
			}
			
			this.currentIndex++;
			this.token = new Token(TokenType.SLITERAL, value);
			return;
			
		}
		
		
		//operator
		if(this.data[this.currentIndex] == '>' ||
		   this.data[this.currentIndex] == '<' ||
		   this.data[this.currentIndex] == '=' ||
		   this.data[this.currentIndex] == '!') {
			
			String value = "";
			value += this.data[this.currentIndex++];
			
			if(this.data[this.currentIndex] == '=') {
				value += this.data[this.currentIndex++];
			}
			
			if(value.equals("<")) {
				this.token = new Token(TokenType.OLESS, "operator");
				
			} else if(value.equals(">")) {
				this.token = new Token(TokenType.OGREATER, "operator");
				
			} else if(value.equals(">=")) {
				this.token = new Token(TokenType.OGREATEREQ, "operator");
				
			} else if(value.equals("<=")) {
				this.token = new Token(TokenType.OLESSEQ, "operator");
				
			} else if(value.equals("=")) {
				this.token = new Token(TokenType.OEQ, "operator");
				
			} else if(value.equals("!=")) {
				this.token = new Token(TokenType.ONOT, "operator");
				
			} else {
				throw new IllegalArgumentException("Invalid query input.");
			}
			return;
		}
		
		throw new IllegalArgumentException("Invalid query input, not recognized.");
		
	}
	
	
	
	
	
	
	
	/**
	 * Removes all whitespace
	 */
	private void skipWhiteSpaces() {
		
		while(this.currentIndex < data.length) {
			char c = data[this.currentIndex];
			if(c == ' ' || c == '\t' || c == '\n' || c == '\r') {
				this.currentIndex++;
			} else {
				break;
			}
		}
	}


}
