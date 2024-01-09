package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Simple HashTable
 * @author TeaMadzarac
 * @version 30/10/2022
 */
public class SimpleHashtable<K,V> implements Iterable<SimpleHashtable.TableEntry<K,V>> {
	
	private int size;
	private TableEntry<K,V>[] table;
	private int modificationCount;
	
	
	/**
	 * constructor
	 * @param capacity initial capacity of table
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {

		if(capacity < 1) {
			throw new IllegalArgumentException();
		}
		
		int num = 0;
		int counter = 0;
		while(num < capacity) {
			num = (int) Math.pow(2, counter);
			counter++;
		}
		this.size = 0;
		this.table = (TableEntry<K,V>[]) new TableEntry[num];
		this.modificationCount = 0;
	}
	
	/**
	 * constructor with default initial capacity, 16
	 */
    public SimpleHashtable() {
		this(16);
	}
	
	/**
	 * Table Entry, static inner class
	 * @author TeaMadzarac
	 *
	 * @param <K> key
	 * @param <V> value
	 */
	public static class TableEntry<K,V> {
		private K key;
		private V value;
		private TableEntry<K,V> next;
		
		
		public TableEntry(K key, V value, TableEntry next) {
			
			if(key == null) {
				throw new NullPointerException();
			}
			
			this.key = key;
			this.value = value;
			this.next = next;
		}
		
		public K getKey() {
			return key;
		}
		
		public V getValue() {
			return value;
		}
		
		public void setValue(V value) {
			this.value = value;
		}	
		
	}
	
	/**
	 * Add key in table
	 * @param key key
	 * @param value value that will be attached to given key
	 * @return value that was previously connected to given key
	 */
	public V put(K key, V value) {
		
		
		if(key == null) {
			throw new NullPointerException();
		}
		
		if((this.size / this.table.length) >= 0.75) {
			increaseTableSize();
		}
		
		
		V oldValue = null;
		boolean replace = false;
		int position = Math.abs(key.hashCode()) % table.length;
		TableEntry newEntry = new TableEntry(key, value, null);
		
		if(table[position] == null) {
			table[position] = newEntry;
			this.size++;
			modificationCount++;
			
		} else {
			TableEntry scroll = this.table[position];
			while(scroll.next != null) {
				if(key.equals(scroll.getKey())) {
					oldValue = (V) scroll.getValue();
					scroll.setValue(value);
					replace = true;
					break;
				}
				scroll = scroll.next;
			} 
			if(key.equals(scroll.getKey())) {
				oldValue = (V) scroll.getValue();
				scroll.setValue(value);
				replace = true;
			}
			if(!replace) {
				scroll.next = newEntry;
				this.size++;
				modificationCount++;
			}
			
		}
		
		return oldValue;
	}
	
	
	/**
	 * gets value of given key
	 * @param key key whose value we want
	 * @return value of given key or null if value is not in the table
	 */
	public V get(Object key) {
		
		if(key == null) {
			return null;
		}
		int position = Math.abs(key.hashCode()) % table.length;
		if(this.table[position] == null) {
			return null;
		}
		V oldValue = null;
		TableEntry scroll = this.table[position];
		while(scroll.next != null) {
			if(scroll.getKey().equals(key)) {
				oldValue = (V) scroll.getValue();
				break;
			}
			scroll = scroll.next;
		}
		if(scroll.getKey().equals(key)) {
			oldValue = (V) scroll.getValue();
		}
		
		return oldValue;
	}
	
	
	/**
	 * gets size of the table
	 * @return number of key-value pairs stored
	 */
	public int size() {
		return this.size;
	}
	
	
	/**
	 * checks if given key is contained in table 
	 * @param key key we want to find in the table
	 * @return true if key is in the table, otherwise false
	 */
	public boolean containsKey(Object key) {
		
		if(key == null) {
			return false;
		}
		
		int position = Math.abs(key.hashCode()) % table.length;
		if(this.table[position] == null) {
			return false;
		}
		TableEntry scroll = this.table[position];
		while(scroll.next != null) {
			if(scroll.getKey().equals(key)) {
				return true;
			}
			scroll = scroll.next;
		}
		if(scroll.getKey().equals(key)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * checks if given value is in the table
	 * @param value value we want to find in the table
	 * @return true if value is contained in the table, otherwise false
	 */
	public boolean containsValue(Object value) {
		
		for(int i = 0; i < this.table.length; i++) {
			
			if(this.table[i] != null) {
				TableEntry scroll = this.table[i];
				while(scroll.next != null) {
					if((scroll.getValue() == null && value == null) ||
							scroll.getValue().equals(value)) {
						return true;
					}
					scroll = scroll.next;
				}
				if((scroll.getValue() == null && value == null) || 
						scroll.getValue().equals(value)) {
					return true;
				}
			}
			
		}
		return false;
	}
	
	
	/**
	 * removes key from the table
	 * @param key key we want to remove
	 * @return value of removed key, if nothing was removed it returns null
	 */
	public V remove(Object key) {
		
		if(key == null) {
			return null;
		}
		V oldValue = null;
		int position = Math.abs(key.hashCode()) % table.length;
		if(this.table[position] == null) {
			return oldValue;
		}
		TableEntry scroll = this.table[position];
		TableEntry scrollPrevious = this.table[position];
		if(scroll.getKey().equals(key)) {                  //mice ako je clan u slotu
			oldValue = (V) scroll.getValue();
			this.table[position] = scroll.next;
			this.size--;
			modificationCount++;
			return oldValue;
		}                                                  
		while(scroll.next != null) {                    //trazi clana kojeg je potrbno maknuti po ul listi
			if(scroll.getKey().equals(key)) {
				oldValue = (V) scroll.getValue();
				scrollPrevious.next = scroll.next;
				this.size--;
				modificationCount++;
				return oldValue;
			}
			scrollPrevious = scroll;
			scroll = scroll.next;
		}
		if(scroll.getKey().equals(key)) {               //mice zadnjeg clana iz ul liste
			oldValue = (V) scroll.getValue();
			scrollPrevious.next = null;  //it was scroll.next
			this.size--;
			modificationCount++;
			return oldValue;
		}
		
		return null;
	}
	
	/**
	 * checks if table is empty
	 * @return true if table is empty, otherwise false
	 */
	public boolean isEmpty() {
		if(this.size == 0) {
			return true;
		}
		return false;
	}

	/**
	 * string representation of table
	 */
	public String toString() {
		String s = "[";
		boolean first = true;
		for(int i = 0; i < this.table.length; i++) {
					
					if(this.table[i] != null) {
						TableEntry scroll = this.table[i];
						while(scroll.next != null) {
							if(first) {
								s = s + scroll.getKey().toString() + "=" + scroll.getValue().toString();
								first = false;
							} else {
								s = s + ", " + scroll.getKey().toString() + "=" + scroll.getValue().toString();
							}
							scroll = scroll.next;
						}
						
               
						if(first) {
							s = s + scroll.getKey().toString() + "=" + scroll.getValue().toString();
							first = false;
						} else {
							s = s + ", " + scroll.getKey().toString() + "=" + scroll.getValue().toString();
						}
						
					}
					
		}

		s += "]";
		return s;
	}
	
	/**
	 * places values of table in an array
	 * @return array representation of table
	 */
	public TableEntry<K,V>[] toArray() {
		
		TableEntry<K,V>[] a = (TableEntry<K,V>[]) new TableEntry[this.size()];
		
		int counter = 0;
		for(int i = 0; i < this.table.length; i++) {
			
			if(this.table[i] != null) {
				TableEntry<K,V> scroll = this.table[i];
				while(scroll.next != null) {
					
					a[counter++] = scroll;
					scroll = scroll.next;
				}
				
				a[counter++] = scroll;
			}	
		}
		return a;
	}
	
	/**
	 * deletes all pairs from collection
	 */
	public void clear() {
		this.size = 0;
		modificationCount++;
		for(int i = 0; i < this.table.length; i++) {
			this.table[i] = null;
		}
	}
	
	
	/**
	 * increases size of the table
	 */
	private void increaseTableSize() {
		TableEntry<K,V>[] increasedSize = (TableEntry<K,V>[]) new TableEntry[this.table.length * 2];
		
		TableEntry<K,V>[] a = (TableEntry<K,V>[]) new TableEntry[this.size];
		a = toArray();
		
		
		for(int i = 0; i < a.length; i++) {
			
			int position = Math.abs(a[i].getKey().hashCode()) % increasedSize.length;
			TableEntry<K,V> newEntry = new TableEntry<K,V>(a[i].getKey(), a[i].getValue(), null);
			
			if(increasedSize[position] == null) {
				increasedSize[position] = newEntry;
				
			} else {
				TableEntry<K,V> scroll = increasedSize[position];
				while(scroll.next != null) {
					scroll = scroll.next;
				}
				scroll.next = newEntry; 
			}
		}
		this.table = increasedSize;
		return;
	}

	@Override
	public Iterator<SimpleHashtable.TableEntry<K,V>> iterator() {
		
		return new IteratorImpl();
	}

	/**
	 * Iterator implementation
	 * @author TeaMadzarac
	 * @version 30/10/2022
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K,V>> {
		
		private int currentPosition;
		private boolean nextCalled;
		private int modificationCountIterator;
		
		/**
		 * constructor
		 * sets current position to 0, gets modification count
		 */
		public IteratorImpl() {
			this.currentPosition = 0;
			this.nextCalled = false;
			this.modificationCountIterator = SimpleHashtable.this.modificationCount;
		}
		
		/**
		 * checks if there is next element
		 */
		public boolean hasNext() {  
			
			if(this.modificationCountIterator != SimpleHashtable.this.modificationCount) {
				throw new  ConcurrentModificationException();
			}
			
			if(this.currentPosition < SimpleHashtable.this.size) {
				return true;
			}
			return false;
		}
		
		/**
		 * returnes next element and moves for one spot
		 */
		public SimpleHashtable.TableEntry next() { 
			
			if(this.modificationCountIterator != SimpleHashtable.this.modificationCount) {
				throw new  ConcurrentModificationException();
			}
			
			
			if(this.currentPosition < SimpleHashtable.this.size) {
				this.nextCalled = true;
				return SimpleHashtable.this.toArray()[this.currentPosition++];
				
			} else {
				throw new NoSuchElementException();
			}	
			
		}
		
		
		/**
		 * removes specified element, but only if next() was called before
		 */
		public void remove() { 
			
			if(this.modificationCountIterator != SimpleHashtable.this.modificationCount) {
				throw new  ConcurrentModificationException();
			}
			
			if(this.nextCalled == false) {
				throw new IllegalStateException();
				
			} else {
				
				this.nextCalled = false;
				K removeKey = SimpleHashtable.this.toArray()[--this.currentPosition].getKey();
				SimpleHashtable.this.remove(removeKey);
				this.modificationCountIterator++;
			}
			
		}
		
	}

	
	
	
	
	
}
