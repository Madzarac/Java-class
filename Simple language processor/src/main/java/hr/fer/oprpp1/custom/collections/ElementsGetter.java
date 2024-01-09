package hr.fer.oprpp1.custom.collections;

/**
 * ElementsGetter object returns object to user one by one
 * @author TeaMadzarac
 * @version 21/10/2022
 *
 */
public interface ElementsGetter {
	
	/**
	 * Checks if there is a next element
	 * @return true if there is a next element in the collection
	 */
	public boolean hasNextElement();
	
	/**
	 * Gets the next element
	 * @return Returns next element
	 */
	public Object getNextElement();
	
	/**
	 * Does given processor on elements of the collection that are still left
	 * @param p Processor
	 */
	public default void processRemaining(Processor p) {
		
		while(hasNextElement()) {
			
			p.process(getNextElement());
		}

		
	}

}
