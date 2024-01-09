package hr.fer.oprpp1.custom.scripting.lexer;

import hr.fer.oprpp1.custom.scripting.elems.ElementString;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;


/**
 * SmartScriptLexer
 * @author TeaMadzarac
 * @version 22/10/2022
 */
public class SmartScriptLexer {
	
	private char[] data;      
	private int currentIndex; 
	private SmartLexerState state;
	private SmartToken smartToken;
	
	/**
	 * Constructor
	 * @param text that is going to be turned into tokens 
	 */
	public SmartScriptLexer(String text) { 
		checkInputText(text);
		this.data = text.toCharArray();
		this.currentIndex = 0;
		this.state = SmartLexerState.BASIC;
	}
	
	
	/**
	 * generates and returns next token
	 * throws ParserException if error happens
	 * @return returns next token 
	 */
	public SmartToken nextSmartToken() {  
		generateNextSmartToken();
		return getSmartToken();
	}
	
	
	/**
	 * returns last generated token, it can be called multiple times, it doesn't start generation of next token
	 * @return returns current token
	 */
	public SmartToken getSmartToken() {
		return this.smartToken;
	}
	
	
	/**
	 * setter
	 * @param state LexerState state
	 */
	public void setState(SmartLexerState state) {
		if(state == null) {
			throw new SmartScriptParserException("SmartLexerState can't be null!");
		}
		this.state = state;
	}
	
	/**
	 * generates tokens
	 */
	private void generateNextSmartToken() {
		
		//if we already reached EOF, if asked to generate next token throw new LexerException
				if(this.smartToken != null && this.smartToken.getSmartTokenType() == SmartTokenType.EOF) {
					throw new SmartScriptParserException("No more tokens avaliable!");
				}
				
				
				
				//if there are no more chars left generate EOF
				if(this.currentIndex >= this.data.length) {
					this.smartToken = new SmartToken(SmartTokenType.EOF, null);
					return;
				}
				
				
				
				//for basic lexer state (only token is TEXT)  ------------------------------------------
				if(this.state.equals(SmartLexerState.BASIC)) {
					
					String textVal = "";
					while(this.currentIndex < this.data.length) {
						
						
						//checks escaping  
						if(this.data[this.currentIndex] == '\\') {
							
							this.currentIndex++;					
							if(this.currentIndex == this.data.length) {                //invalid escape
								throw new SmartScriptParserException("Invalid escape!");
							}
							
							if(this.data[this.currentIndex] == '\\' ||                 //valid escaping
							   this.data[this.currentIndex] == '{') {      
								
								textVal += this.data[this.currentIndex++];
								continue;
								
							} else {                                                   //invalid escape
								throw new SmartScriptParserException("Invalid escape!");
							}
						}  
						
						
						//checks start of TAG
						if(this.data[this.currentIndex] == '{' && this.data[this.currentIndex + 1] == '$') {
							setState(SmartLexerState.TAG);
							break;
						}
						
						//if next character was part of text
						textVal += this.data[this.currentIndex++];
					}
					//generates token
					this.smartToken = new SmartToken(SmartTokenType.TEXT, textVal);
					return;
					
					
					
			    //when lexer is in state TAG -----------------------------------------------------------
				} else if(this.state.equals(SmartLexerState.TAG)){ 
					
					//start of tag
					if(this.data[this.currentIndex] == '{' && this.data[this.currentIndex + 1] == '$') {
						String tagStart = "{$";
						this.smartToken = new SmartToken(SmartTokenType.TAGSTART, tagStart);
						this.currentIndex += 2;
						return;
						
					}
					
					skipWhiteSpaces();
					
					//end of tag
					if(this.data[this.currentIndex] == '$' && this.data[this.currentIndex + 1] == '}') {
						String tagEnd = "$}";
						this.smartToken = new SmartToken(SmartTokenType.TAGEND, tagEnd);
						this.currentIndex += 2;
						setState(SmartLexerState.BASIC);
						return;
						
					}
					
					//tag name (=/FOR/END)
					if(this.data[this.currentIndex] == '=') {
						this.smartToken = new SmartToken(SmartTokenType.TAGNAME, Character.toString(this.data[this.currentIndex]));
						this.currentIndex++;
						return;
					}
					String mybTagName = "";
					if(Character.isLetter(this.data[this.currentIndex])  && 
					   Character.isLetter(this.data[this.currentIndex + 1]) && 
					   Character.isLetter(this.data[this.currentIndex + 2])) {
						
						mybTagName += Character.toString(this.data[this.currentIndex]) + 
					            Character.toString(this.data[this.currentIndex + 1]) + 
					            Character.toString(this.data[this.currentIndex + 2]);
									            
			            if(mybTagName.equalsIgnoreCase("FOR") || mybTagName.equalsIgnoreCase("END")) {
							this.smartToken = new SmartToken(SmartTokenType.TAGNAME, mybTagName);
							this.currentIndex += 3;
							return;
			            }
					}
					
					//function name (starts with @)
					if(this.data[this.currentIndex] == '@' && Character.isLetter(this.data[this.currentIndex + 1])) {
						String str = "@";
						this.currentIndex++;
						
						while(this.currentIndex < this.data.length && 
							  (Character.isLetter(this.data[this.currentIndex]) ||
				  			   Character.isDigit(this.data[this.currentIndex]) ||
									  this.data[this.currentIndex] == '_')) {
							
							str += this.data[this.currentIndex++];
						
						}
						this.smartToken = new SmartToken(SmartTokenType.FUNCTION, str);
						return;
					}
					
					
					//operators
					if(this.data[this.currentIndex] == '+' ||
					   this.data[this.currentIndex] == '-' ||
					   this.data[this.currentIndex] == '*' ||
					   this.data[this.currentIndex] == '/' ||
					   this.data[this.currentIndex] == '^') {
						
						this.smartToken = new SmartToken(SmartTokenType.OPERATOR, Character.toString(this.data[this.currentIndex++]));
						return;
					}
					
					
					//string
					if(this.data[this.currentIndex] == '"') {
						String str = "\"";
						this.currentIndex++;
						while(this.currentIndex < this.data.length) {
							
							//escaping
							if(this.data[this.currentIndex] == '\\') {
								this.currentIndex++;
								if(this.data[this.currentIndex] == '\"') {
									str += this.data[this.currentIndex++];
									continue;
								} else if(this.data[this.currentIndex] == '\\') {
									str += this.data[this.currentIndex++];
									continue;
								} else {
									throw new SmartScriptParserException("Invalid escape!");
								}
								
							}
							
						    //otherwise
							str += this.data[this.currentIndex++];
							if(this.data[this.currentIndex -1] == '"') {
								break;
							}
						}
						if(this.data[this.currentIndex - 1] != '"') {
							throw new SmartScriptParserException("String doesn't have an end!");
						}
						this.smartToken = new SmartToken(SmartTokenType.STRING, str);
						return;
					} 
					
					
					//int or double
					if(Character.isDigit(this.data[this.currentIndex]) ||
					   this.data[this.currentIndex] == '-' && Character.isDigit(this.data[this.currentIndex +1])) {
						
						boolean negative = false;
						if(this.data[this.currentIndex] == '-') {
							negative = true;
							this.currentIndex++;
						}
						
						int dec = 0;
						int number = (int)this.data[this.currentIndex] - (int)'0';
						double decNumber = 0;
						int count = 0;
						this.currentIndex++;
						while(this.currentIndex < this.data.length && 
								(Character.isDigit(this.data[this.currentIndex]) || this.data[this.currentIndex] == '.')) {
							
							if(this.data[this.currentIndex] == '.') {
								dec++;
								this.currentIndex++;
								continue;
							}
							
							if(dec >= 2) {
								throw new SmartScriptParserException("Invalid Number, has more than one dot!");
								
							} else if(dec == 1) {  //double
								
								decNumber *= 10;
								decNumber += (int)data[this.currentIndex] - (int)'0';
								this.currentIndex++;
								count++;
								
							} else {  //int
								
								number *= 10;
								number += (int)data[this.currentIndex] - (int)'0';
								this.currentIndex++;
							}
							
							
						}
						
						if(this.data[this.currentIndex -1] == '.') {
							throw new SmartScriptParserException("Invalid Number, ends with dot!");
						}
						
						if(dec == 0) {
							this.smartToken = new SmartToken(SmartTokenType.INT, number);
							return;
						}
						
						decNumber = decNumber * Math.pow(0.1, count);
						decNumber += number;
						this.smartToken = new SmartToken(SmartTokenType.DOUBLE, decNumber);
						return;
					}
					
					
					//variable (starts by letter and follows zero or more letters digits or underscores)
					if(Character.isLetter(this.data[this.currentIndex])) {
						String str = "";
						
						
						while(this.currentIndex < this.data.length &&
							  (Character.isLetter(this.data[this.currentIndex]) ||
							  Character.isDigit(this.data[this.currentIndex]) ||
							  this.data[this.currentIndex] == '_'  )) {
							
							str += this.data[this.currentIndex++];
						}
						
						this.smartToken = new SmartToken(SmartTokenType.VARIABLE, str);
						return;
					}
					
					
				}
				
				throw new SmartScriptParserException("Input not recognized!");
	}
	
	
	
	/**
	 * Checks if input string is valid
	 * @param text text that will be passed to Lexer
	 */
	private void checkInputText(String text) {
		
		if(text == null) {
			throw new SmartScriptParserException("Input is invalid!");
		}
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
