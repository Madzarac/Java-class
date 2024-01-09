package hr.fer.oprpp1.custom.collections;

/**
 * 
 * @author TeaMAdzarac
 * @version 17/10/2022
 *
 */
public class Collection {

	/**
	 * class Collection
	 */
	protected Collection() {
	}
	
	/**
	 * Returns true if collection is empty, othervise false
	 * @return
	 */
	public boolean isEmpty() {

		if(this.size() == 0) {
			return true;
		}
		return false;
		
	}
	
	/**
	 * doesn't have a functionality in this class, always returns 0
	 * @return
	 */
	public int size() {
		return 0;
	}
	
	/**
	 * doesn't have a functionality in this class
	 * @param value
	 */
	public void add(Object value) {
		return;
	}
	
	/**
	 * doesn't have a functionality in this class
	 * @param value
	 * @return
	 */
	public boolean contains(Object value)  {
		return false;
	}
	
	/**
	 * doesn't have a functionality in this class
	 * @param value
	 * @return
	 */
	public boolean remove(Object value) {
		return false;
	}
	
	/**
	 * doesn't have a functionality in this class
	 * @return
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * doesn't have a functionality in this class
	 * @param processor
	 */
	public void forEach(Processor processor) {
		
	}
	
	/**
	 * adds all elements of other collection into this collection
	 * @param other
	 */
	public void addAll(Collection other) {
		
        class LocalClass extends Processor {
        	
			public void process(Object value) {
				add(value);	
			}	
		}
        other.forEach(new LocalClass());
		
	}
	
	/**
	 * doesn't have a functionality in this class
	 */
	public void clear() {
		
	}
	
}
