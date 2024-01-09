package hr.fer.oprpp1.custom.collections;

import java.lang.reflect.Array;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Resizable array-backed collection of objects
 * @author TeaMadzarac
 * @version 30/10/2022
 */
public class ArrayIndexedCollection<E> implements List<E>{

	/**
	 * size of the collection
	 * elements of the collection
	 */
	private int size;
	private E[] elements;
	private long modificationCount = 0;
	
	/**
	 * constructor that accepts other collection and initial capacity
	 * adds all elements from other into this collection
	 * @param other collection whose elements are going to be copied into the newly made collection
	 * @param initialCapacity initial capacity of the collection we want to make
	 */
    public ArrayIndexedCollection(Collection<? extends E> other, int initialCapacity) {
		
		if(other == null) {
			throw new NullPointerException();
		}
		
		if (initialCapacity < 1) {
			throw new IllegalArgumentException();
		}
		if(initialCapacity < other.size()) {
			initialCapacity = other.size();
		}

		this.elements = (E[])new Object[initialCapacity];
		addAll(other);

		
	}
    
    /**
     * constructor that accepts other collection
     * calls another constructor with 16 as initial capacity
     * @param other collection whose elements are going to be copied into the newly made collection
     */
    public ArrayIndexedCollection(Collection<? extends E> other) {
		this(other, 16);
	}
    
    /**
     * constructor for empty collection with initial capacity
     * @param initialCapacity initial capacity of the collection we want to make
     */
	public ArrayIndexedCollection(int initialCapacity) {
		
		if (initialCapacity < 1) {
			throw new IllegalArgumentException();
		}
		this.size = 0;
		this.elements = (E[])new Object[initialCapacity];
		
	}
	
	/**
	 * constructor for empty collection, initial capacity is put to 16
	 */
	public ArrayIndexedCollection() {
		this(16);
	}
	
	/**
	 * moves all elements that are right of the element on the position i for one position to the left
	 * @param i position from which all elements have to be moved one position to the right
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
     * @param empty empty array which will be filled with elements from this collection
     */
    private void fillArray(E[] empty) {
		
		for(int i = 0; i < this.size; i++) {
			empty[i] = this.elements[i];
		}
	}
    
    /**
     * allocates a new space for the collection
     */
    private void increaseArraySize() {
    	E[] temp = (E[])new Object[this.size * 2];
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
	 * adds a value into the collection, at the last position
	 */
	@Override
	public void add(E value) {
		
		if(value == null) {
			throw new NullPointerException();
		}
		
		if(this.size == this.elements.length) {
			increaseArraySize();
			
		} 
		
		this.elements[this.size] = value;
		this.size++;
		this.modificationCount++;
		
		return;
	}
	
	/**
	 * checks if this collection contains given value
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
				this.modificationCount++;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * returns an array with the collection content
	 */
	@Override
	public E[] toArray() {
		
		E[] a = (E[])new Object[this.size];
		fillArray(a);
		
		return a;
	}
	
	/**
	 * goes through all elements of an array and does a given process over them
	 
	@Override
    public void forEach(Processor processor) {
		
		for(int i = 0; i < this.size; i++) {
			processor.process(this.elements[i]);
		}
		
	}
	*/
	
	/**
	 * returns object at a given index from collection
	 * @throws IndexOutOfBoundsException when given index is less than 0 or bigger than array size
	 * @param index index from which we want the object
	 * @return object from collection that is at a given index
	 */
	@Override
	public E get(int index) {
		
		if(index < 0 || index > this.size -1) {
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
		this.modificationCount++;
	}
	
	/**
	 * inserts a given value into a given position of the collection
	 * @param value we want to add in the collection
	 * @param position where in the collection we want to add the value
	 */
	@Override
	public void insert(E value, int position) {
		
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
		     this.modificationCount++;
			 
		 } 
	}
	
	/**
	 * returns index of the first instance of given object in a collection
	 * if collection doeasn't have a required value it returns -1
	 * @param value for which index is returned
	 * @return index of the first occurrence of the given value, or -1 if collection doesn't contain given value
	 */
	@Override
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
	 * @param index position of the object we want to remove
	 */
	@Override
	public void remove(int index) {
		
		if(index < 0 || index > this.size - 1) {
			throw new IndexOutOfBoundsException();

		} else if(index == 0 && this.size == 1) {
			this.elements[index] = null;
			this.size--;
			this.modificationCount++;
		} else {
			moveToLeft(index);
			this.size--;
			this.modificationCount++;
		}
	}
	
	
	/**
	 * method that creates ElementsGetter 
	 * @return ElementsGetter 
	 */
	@Override
	public ElementsGetter<E> createElementsGetter() {
		
		return new ElG(this);
	}
	
	/**
	 * static inner class that implements Element getter
	 * @author TeaMadzarac
	 * @version 21/10/2022
	 */
	private static class ElG<E> implements ElementsGetter<E> {
		
		private ArrayIndexedCollection<E> a;
		private int current;
		private long savedModificationCount;
		
		/**
		 * constructor
		 * @param a ArrayIndexedCollection
		 */
		public ElG(ArrayIndexedCollection a) {
			this.a = a;
			this.current = 0;
			this.savedModificationCount = a.modificationCount;
		}
		

		/**
		 * checks if there is next element
		 */
		public boolean hasNextElement() {
			
			if(this.savedModificationCount != this.a.modificationCount) {
				throw new ConcurrentModificationException();
				
			} else if(this.a.size > current) {
				return true;
			}
			return false;
		}
		
		/**
		 * returns current element
		 */
		public E getNextElement() {
			
			if(this.savedModificationCount != this.a.modificationCount) {
				throw new ConcurrentModificationException();
				
			} else if(current >= this.a.size) {
				throw new NoSuchElementException();
				
			} else {
				return this.a.elements[current++];
			}
		}
		
	}
	
	
}
