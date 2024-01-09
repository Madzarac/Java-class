package hr.fer.oprpp1.custom.scripting.elems;

/**
 * class with a single read-only property value(int)
 * @author TeaMadzarac
 * @version 22/10/2022
 */
public class ElementConstantInteger extends Element {

	private int value;
	
	/**
	 * constructor
	 * @param value sets value property
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}
	
	/**
	 * returns string representation of value property
	 */
	@Override
	public String asText() {
		return String.valueOf(this.value);
	}
}
