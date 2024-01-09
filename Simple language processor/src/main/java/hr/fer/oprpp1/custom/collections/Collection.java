package hr.fer.oprpp1.custom.collections;

/**
 * General collection of object
 * @author TeaMAdzarac
 * @version 17/10/2022
 */
public interface Collection {

	
	/**
	 * default method that checks if the collection is empty
	 * @return Returns true if collection is empty, otherwise false
	 */
	default boolean isEmpty() {

		if(this.size() == 0) {
			return true;
		}
		return false;
		
	}
	
	/**
	 * abstract method that returns number of currently stored objects in this collection, here returns zero
	 * @return number of currently stored objects in this collection
	 */
	public int size();
	
	/**
	 * abstract method that adds element to the end of the collection, here it's empty
	 * @param value that will be added to the collection
	 */
	public void add(Object value);
	
	/**
	 * abstract method that checks if collection contains given value, here it always returns false
	 * @param value which would be searched for in the collection
	 * @return true if value is contained in the collection, otherwise returns false
	 */
	public boolean contains(Object value);
	
	/**
	 * abstract method that removes object from the collection, here it always returns false
	 * @param value object which we want to remove
	 * @return true if object is successfully removed, otherwise false
	 */
	public boolean remove(Object value);
	
	/**
	 * abstract method that returns an array with the collection contents, here it throws UnsupportedOperationException
	 * @return array with the collection content
	 */
	public Object[] toArray();
	
	/**
	 * abstract method that goes through all elements of a collection and calls processor.process
	 * @param processor from which process is called
	 */
	public default void forEach(Processor processor) {
		
		ElementsGetter getter = this.createElementsGetter();
		
		while (getter.hasNextElement()) {
			
			processor.process(getter.getNextElement());
			
			}
		
		
	}
	
	/**
	 * default method that adds all elements of other collection into this collection
	 * @param other collection whose elements we want to add to this collection
	 */
	default void addAll(Collection other) {
		
        class LocalClass implements Processor {
        	
			public void process(Object value) {
				add(value);	
			}	
		}
        other.forEach(new LocalClass());
		
	}
	
	/**
	 * abstract method that removes all elements from this collection, here it's empty
	 */
	public void clear();
	
	/**
	 * abstract method that creates ElementsGetter 
	 * @return ElementsGetter 
	 */
	public ElementsGetter createElementsGetter();
	
	/**
	 * Adds all elements from col that are accepted by tester to this collection
	 * @param col other collection whose elements we want to add
	 * @param tester test for picking which elements will be added
	 */
	public default void addAllSatisfying(Collection col, Tester tester) {
		
		ElementsGetter getter = col.createElementsGetter();
		Object el;
		
		while (getter.hasNextElement()) {
			
			el = getter.getNextElement();
			if(tester.test(el)) {
				this.add(el);
			}
		}
	}
	
}
