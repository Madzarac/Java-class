package hr.fer.oprpp1.custom.scripting.elems;

/**
 * class with a single read-only property value(double)
 * @author TeaMadzarac
 * @version 22/10/2022
 */
public class ElementConstantDouble extends Element {
	
	private double value;
	
	/**
	 * constructor
	 * @param value sets value property
	 */
	public ElementConstantDouble(double value) {
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
