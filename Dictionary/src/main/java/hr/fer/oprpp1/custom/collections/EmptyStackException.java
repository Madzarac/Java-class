package hr.fer.oprpp1.custom.collections;

/**
 * Exception for the case that stack is empty when we try to pop an element from it
 * @author TeaMadzarac
 * @version 17/10/2022
 */
public class EmptyStackException extends RuntimeException {

	/**
	 * shows message that the stack is empty
	 * @param message stack is empty
	 */
	public EmptyStackException(String message) {
        super(message);
    }
	
}
