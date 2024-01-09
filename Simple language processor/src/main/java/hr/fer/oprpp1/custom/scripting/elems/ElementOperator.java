package hr.fer.oprpp1.custom.scripting.elems;

/**
 * class with a single read-only property symbol(String)
 * @author TeaMadzarac
 * @version 22/10/2022
 */
public class ElementOperator extends Element {

	private String symbol;
	
	/**
	 * constructor
	 * @param name sets name property
	 */
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}
	
	/**
	public String getName() {
		return this.name;
	}*/
	
	/**
	 * returns symbol property
	 */
	@Override
	public String asText() {
		return this.symbol;
	}
}
