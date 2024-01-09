package hr.fer.oprpp1.hw04.db;

/**
 * 
 * @author TeaMadzarac
 * @version 12/11/2022
 *
 */
public class Token {

	
	private TokenType tokenType;
	private Object value;
	
	/**
	 * Constructor
	 * @param type type of token
	 * @param value value of token
	 */
	public Token(TokenType type, Object value) {
		this.tokenType = type;
		this.value = value;
	}
	
	/**
	 * Value getter
	 * @return returns value of token
	 */
	public Object getValue() {
		return this.value;
	}
	
	/**
	 * Type getter
	 * @return returns type of token
	 */
	public TokenType getType() {
		return this.tokenType;
	}

}
