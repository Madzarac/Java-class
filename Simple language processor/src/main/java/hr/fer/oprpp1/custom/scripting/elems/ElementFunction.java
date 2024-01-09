package hr.fer.oprpp1.custom.scripting.elems;

/**
 * class with a single read-only property name(String)
 * @author TeaMadzarac
 * @version 22/10/2022
 */
public class ElementFunction extends Element {

	private String name;
	
	/**
	 * constructor
	 * @param name sets name property
	 */
	public ElementFunction(String name) {
		this.name = name;
	}
	
	/**
	public String getName() {
		return this.name;
	}*/
	
	/**
	 * returns name property
	 */
	@Override
	public String asText() {
		return this.name;
	}
}
