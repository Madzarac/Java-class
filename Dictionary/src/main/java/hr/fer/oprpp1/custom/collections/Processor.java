package hr.fer.oprpp1.custom.collections;

/**
 * model of an object  of performing some operation on the passed object 
 * @author TeaMadzarac
 * @version 30/10/2022
 */
public interface Processor<E> {
	
	
	/**
	 * method that accepts an object value, has an empty body here
	 * @param value over which process will be done
	 */
	public void process(E value);

	

}
