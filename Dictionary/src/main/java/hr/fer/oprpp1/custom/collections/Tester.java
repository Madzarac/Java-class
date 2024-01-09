package hr.fer.oprpp1.custom.collections;

/**
 * Tests if the object is acceptable or not
 * @author TeaMadzarac
 * @version 30/10/2022
 */
public interface Tester<E> {
	
	boolean test(E obj);

}
