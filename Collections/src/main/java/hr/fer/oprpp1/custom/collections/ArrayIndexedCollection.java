package hr.fer.oprpp1.custom.collections;

/**
 * 
 * @author TeaMadzarac
 * @version 17/10/2022
 *
 */
public class ArrayIndexedCollection extends Collection {

	/**
	 * size of the collection
	 * elements of the collection
	 */
	private int size;
	private Object[] elements;
	
	/**
	 * constructor that accepts other collection and initial capacity
	 * adds all elements from other into this collection
	 * @param other
	 * @param initialCapacity
	 */
    public ArrayIndexedCollection(Collection other, int initialCapacity) {
		
		if(other == null) {
			throw new NullPointerException();
		}
		
		if (initialCapacity < 1) {
			throw new IllegalArgumentException();
		}
		if(initialCapacity < other.size()) {
			initialCapacity = other.size();
		}

		this.elements = new Object[initialCapacity];
		addAll(other);

		
	}
    
    /**
     * constructor that accepts other collection
     * calls another constructor with 16 as initial capacity
     * @param other
     */
    public ArrayIndexedCollection(Collection other) {
		this(other, 16);
	}
    
    /**
     * constructor for empty collection with initial capacity
     * @param initialCapacity
     */
	public ArrayIndexedCollection(int initialCapacity) {
		
		if (initialCapacity < 1) {
			throw new IllegalArgumentException();
		}
		this.size = 0;
		this.elements = new Object[initialCapacity];
		
	}
	
	/**
	 * construvtor for empty collection, initial capacity is put to 16
	 */
	public ArrayIndexedCollection() {
		this(16);
	}
	
	/**
	 * moves all elements that are right of the element on the position i for one position to the left
	 * @param i
	 */
    private void moveToLeft(int i) {
		
		for(; i < this.size - 1; i++) {
			this.elements[i] = this.elements[i + 1];
		}
		if(this.elements.length == this.size) {
			this.elements[i] = null;
		} else {
			this.elements[i] = this.elements[i + 1];
		}
	}
    
    /**
     * fills an array with the content of this collection
     * @param empty
     */
    private void fillArray(Object[] empty) {
		
		for(int i = 0; i < this.size; i++) {
			empty[i] = this.elements[i];
		}
	}
    
    /**
     * allocates a new space for the collection
     */
    private void increaseArraySize() {
    	Object[] temp = new Object[this.size * 2];
		fillArray(temp);
		this.elements = temp;
    }
	
    /**
     * returns size of the collection
     */
	@Override
	public int size() {
		return this.size;
	}

	/**
	 * adds a value into the collection
	 */
	@Override
	public void add(Object value) {
		
		if(value == null) {
			throw new NullPointerException();
		}
		
		if(this.size == this.elements.length) {
			increaseArraySize();
			
		} 
		
		this.elements[this.size] = value;
		this.size++;
		
		return;
	}
	
	/**
	 * returns true if collection contains a given value, othervise false
	 */
	@Override
	public boolean contains(Object value)  {
		
		if(this.size == 0) {
			return false;
		}
		
		for(int i = 0; i < this.size; i++) {
			if(this.elements[i].equals(value)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * removes object with a given value from the collection, it object is successfully removed it returns true
	 */
	@Override
	public boolean remove(Object value) {
		
		for(int i = 0; i < this.size; i++) {
			if(this.elements[i].equals(value)) {
				moveToLeft(i);
				this.size--;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * returns an array with the collection content
	 */
	@Override
	public Object[] toArray() {
		
		Object[] a = new Object[this.size];
		fillArray(a);
		
		return a;
	}
	
	/**
	 * goes through all elements of an array and does a given process over them
	 */
	@Override
    public void forEach(Processor processor) {
		
		for(int i = 0; i < this.size; i++) {
			processor.process(this.elements[i]);
		}
		
	}
	
	/**
	 * returns object att a given index from collection
	 * @param index from which we want the object
	 * @return
	 */
	public Object get(int index) {
		
		if(index < 0 || index >= this.size) {
			throw new IndexOutOfBoundsException();
		}
		
		return this.elements[index];
	}
	
	/**
	 * removes all elements from the collection
	 */
	public void clear() {
		for(int i = 0; i < this.size; i++) {
			this.elements[i] = null;
		}
		this.size = 0;
	}
	
	/**
	 * inserts a given value into a giiven position of the collection
	 * @param value we want to add in the collection
	 * @param position where in the collection we want to add the value
	 */
	void insert(Object value, int position) {
		
		 if (position < 0 || position > this.size) {
			 throw new IndexOutOfBoundsException();
			 
		 } else if(position == this.size) {
			 add(value);
			 
		 } else  {
			 if (this.size == this.elements.length) {
				 increaseArraySize();
			 }
			 for(int i = this.size; i >= position; i--) {
				 this.elements[i +1] = this.elements[i];
			 }
			 this.size++;
			 this.elements[position] = value;
			 
		 } 
	}
	
	/**
	 * returns index of the first instance of given object in a collection
	 * if collection doeasn't have a required value it returns -1
	 * @param value
	 * @return
	 */
	public int indexOf(Object value) {
		
		if(value == null) {
			return -1;
		}
		
		for(int i = 0; i < this.size; i++) {
			if(this.elements[i].equals(value)) {
				return i;
			}
		}
		
		return -1;
	}
	
	/**
	 * removes object that is on the given position
	 * if the given index is out of bounds of the collection throws IndexOutOfBoundsException
	 * @param index position of the objectt we want to remove
	 */
	public void remove(int index) {
		
		if(index < 0 || index > this.size - 1) {
			throw new IndexOutOfBoundsException();

		} else if(index == 0 && this.size == 1) {
			this.elements[index] = null;
			this.size--;
		} else {
			moveToLeft(index);
			this.size--;
		}
		

	}
}
