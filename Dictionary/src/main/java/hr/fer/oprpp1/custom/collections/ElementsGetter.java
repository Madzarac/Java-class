package hr.fer.oprpp1.custom.collections;

/**
 * ElementsGetter object returns object to user one by one
 * @author TeaMadzarac
 * @version 30/10/2022
 *
 */
public interface ElementsGetter<E> {
	
	/**
	 * Checks if there is a next element
	 * @return true if there is a next element in the collection
	 */
	public boolean hasNextElement();
	
	/**
	 * Gets the next element
	 * @return Returns next element
	 */
	public E getNextElement();
	
	/**
	 * Does given processor on elements of the collection that are still left
	 * @param p Processor
	 */
	public default void processRemaining(Processor<E> p) {
		
		while(hasNextElement()) {
			
			p.process(getNextElement());
		}

		
	}

}
