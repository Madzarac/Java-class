package hr.fer.oprpp1.custom.scripting.elems;

/**
 * class with a single read-only property value(String)
 * @author TeaMadzarac
 * @version 22/10/2022
 */
public class ElementString extends Element {
	
	private String value;
	
	/**
	 * constructor
	 * @param value sets value property
	 */
	public ElementString(String value) {
		this.value = value;
	}
	
	/**
	 * returns value property
	 */
	@Override
	public String asText() {
		return this.value;
	}

}

