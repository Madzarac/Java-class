package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * linked list-backed collection of objects, extends class Collection
 * @author TeaMadzarac
 * @version 30/10/2022
 */
public class LinkedListIndexedCollection<E> implements List<E>{
	
/**
 * has pointers to next and previous node and additional reference for value
 * @author TeaMadzarac
 * @version 17/10/2022
 */
	private static class ListNode<E> {
		
		ListNode<E> next;
		ListNode<E> previous;
		E value;
		
	}
	
	/**
	 * size of the collection
	 * first and last nodes that are in the collection
	 */
	private int size;
	private ListNode<E> first;
	private ListNode<E> last;
	private long modificationCount = 0;
	
	/**
	 * constructor that generates an empty collection
	 */
	public LinkedListIndexedCollection()  {
		this.first = new ListNode<E>();
		this.last = this.first;
		this.size = 0;
	}
	
	/**
	 * constructor that adds all elements from other to this collection
	 * @param other collection whose elements are going to be copied into the newly made collection
	 */
	public LinkedListIndexedCollection(Collection<? extends E> other) {
		this.first = new ListNode<E>();
		this.last = new ListNode<E>();
		this.last = this.first;
		this.size = 0;
		addAll(other);
	}

	/**
	 * returns size of the collection
	 */
	@Override
	public int size() {
		return this.size;
	}

	/**
	 * adds given value in the collection, at the end
	 * if value is null it throws NullPointerException
	 */
	@Override
	public void add(E value) {

		if(value.equals(null)) {
			throw new NullPointerException();
		}

		if(this.size == 0) {
			this.first.value = value;
			this.first.previous = null;
			this.first.next = null;
			this.last = this.first;
			this.size++;
			this.modificationCount++;
		} else {
			ListNode<E> newNode = new ListNode<E>();
			newNode.value = value;
			newNode.previous = this.last;
			newNode.next = null;
			this.last.next = newNode;
			this.last = newNode;
			this.size++;
			this.modificationCount++;
		}


	}

	/**
	 * checks if the collection contains given value
	 */
	@Override
	public boolean contains(Object value) {
		ListNode node = new ListNode();
		node = this.first;
		for(int i = 0; i < this.size; i++ ) {
			if(node.value.equals(value)) {
				return true;
			}
			node = node.next;
		}
		return false;
	}


	/**
	 * removes object with a given value from the collection, if the object was successfully removed it returns true
	 */
	@Override
	public boolean remove(Object value) {
		int index;
		index = indexOf(value);
		if(index == -1) {
			return false;
		} else {
			remove(index);
			return true;
		}
	}


	/**
	 * returns an array with values copied from the collection
	 */
	@Override
	public E[] toArray() {
		E[] a = (E[])new Object[this.size];
		ListNode<E> node = new ListNode<E>();
		node = this.first;
		for(int i = 0; i < this.size; i++ ) {
			a[i] = node.value;
			node = node.next;
		}
		return a;
	}


	/**
	 * goes through all elements of the collection does a given command over them
	 
	@Override
	public void forEach(Processor processor) {

		ListNode node = new ListNode();
		node = this.first;
		for(int i = 0; i < this.size; i++) {
			processor.process(node.value);
			node = node.next;
		}
	}
	*/

	/**
	 * returns an object that in a given position
	 * @param index position of the object we want
	 * @return object at the given index of the list
	 */
	@Override
	public E get(int index) {

		if(index < 0 || index > size -1) {
			throw new IndexOutOfBoundsException();
		}

		ListNode<E> node = new ListNode<E>();
		node = this.first;
		for(int i = 0; i < index; i++) {
			node = node.next;
		}
		return node.value;
	}

	/**
	 * removes all objects from the collection
	 */
	public void clear() {
		this.first = new ListNode<E>();
		this.last = this.first;
		this.size = 0;
	}

	/**
	 * /**
	 * inserts a given value into a given position of the collection
	 * @param value we want to add in the collection
	 * @param position where in the collection we want to add the value
	 */
	@Override
	public void insert(E value, int position) {

		if(position < 0 || position > this.size) {
			throw new IndexOutOfBoundsException();

		} else if (position == this.size) {
			add(value);

		} else if (position == 0) {
			ListNode<E> insertNode = new ListNode<E>();
			insertNode.value = value;
			insertNode.previous = null;
			this.first.previous = insertNode;
			insertNode.next = this.first;
			this.first = insertNode;
			this.size++;
			this.modificationCount++;

		} else {
			ListNode<E> node = new ListNode<E>();
			node = this.first;
			for(int i = 0; i < position; i++) {
				node = node.next;
			}
			ListNode<E> insertNode = new ListNode<E>();
			insertNode.value = value;
			insertNode.previous = node.previous;
			insertNode.next = node;
			node.previous.next = insertNode;
			node.previous = insertNode;
			this.size++;
			this.modificationCount++;
		}

	}

	/**
	 * returns index of the first instance of given object in a collection
	 * if collection doeasn't have a required value it returns -1
	 * @param value value for which we want to know the index
	 * @return index of the first occurrence of the given value, or -1 if value isn't found
	 */
	@Override
	public int indexOf(Object value) {

		if(value == null) {
			return -1;
		}
		ListNode<E> node = new ListNode<E>();
		node = this.first;
		for(int i = 0; i < this.size -1; i++) {
			if(node.value.equals(value)) {
				return i;
			}
			node = node.next;
		}
		if(node.value.equals(value)) {
			return this.size -1;
		} else {
			return -1;
		}
	}

	/**
	 * removes object that is on the given position
	 * if the given index is out of bounds of the collection throws IndexOutOfBoundsException
	 * @param index position of the object we want to remove
	 */
	@Override
	public void remove(int index) {

		if(index < 0 || index > this.size -1) {
			throw new IndexOutOfBoundsException();
		}

		if(this.size == 1) {
			 clear();

		} else if(index == this.size - 1) {
			ListNode<E> nodeRem = new ListNode<E>();
			nodeRem = this.last;
			nodeRem.previous.next = null;
			nodeRem.previous = null;
			this.size--;
			this.modificationCount++;
		} else if (index == 0) {
			ListNode<E> nodeRem = new ListNode<E>();
			nodeRem = this.first;
			this.first = nodeRem.next;
			this.first.previous = null;
			nodeRem.next = null;
			this.size--;
			this.modificationCount++;

		} else {
			ListNode<E> nodeRem = new ListNode<E>();
			nodeRem = this.first;
			for(int i = 0; i < index; i++) {
				nodeRem = nodeRem.next;
			}
			nodeRem.previous.next = nodeRem.next;
			nodeRem.next.previous = nodeRem.previous;
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
		
		private LinkedListIndexedCollection<E> l;
		private int current;
		private long savedModificationCount;
		private ListNode<E> node;
		
		
		
		/**
		 * constructor
		 * @param l LinkedListIndexedCollection
		 */
		public ElG(LinkedListIndexedCollection<E> l) {
			this.l = l;
			this.current = 0;
			this.savedModificationCount = l.modificationCount;
			this.node = l.first;
			
		}
		

		/**
		 * checks if there is next element
		 */
		public boolean hasNextElement() {
			
			if(this.savedModificationCount != this.l.modificationCount) {
				throw new ConcurrentModificationException();
				
			} else if(this.l.size > current) {
				return true;
			}
			return false;
		}
		
		/**
		 * returns current element
		 */
		public E getNextElement() {
			
			if(this.savedModificationCount != this.l.modificationCount) {
				throw new ConcurrentModificationException();
				
			} else if(current >= this.l.size) {
				throw new NoSuchElementException();
				
			} else {
				current++;
				
				if(this.node.next != null) {
					this.node = this.node.next;
					return this.node.previous.value;

				} else {
					return this.node.value;
				}
				
				
			}			
		}
		
		
	}
	

}

