package hr.fer.oprpp1.custom.collections;

/**
 * List interface
 * @author TeaMadzarac
 * @version 21/10/2022
 */
public interface List extends Collection{

	/**
	 * abstract method
	 * @param index position of the element we want to get
	 * @return element on the given position
	 */
	public Object get(int index);
	
	/**
	 * abstract method
	 * @param value value we want to insert
	 * @param position position on which we want to insert the value
	 */
	public void insert(Object value, int position);
	
	/**
	 * abstract method
	 * @param value value whose position we want to find
	 * @return position of the value in the collection
	 */
	public int indexOf(Object value);
	
	/**
	 * abstract method
	 * @param index position from which we want to remove the element
	 */
	public void remove(int index);

	
}
