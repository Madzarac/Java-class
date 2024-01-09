package hr.fer.oprpp1.custom.collections;

/**
 * 
 * @author TeaMadzarac
 * @version 30/10/2022
 *
 * @param <K> key
 * @param <V> value
 */
public class Dictionary<K,V> {
	
	/**
	 * 
	 * @author TeaMadzarac
	 *
	 * @param <K> key
	 * @param <V> value
	 */
	private static class Pair<K,V> {
		K key;
		V value;
		
	
		public Pair(K key, V value) {
			
			if(key == null) {
				throw new NullPointerException();
			}
			this.key = key;
			this.value = value;
		}
		
		public K getKey() {
			return this.key;
		}
		
		public void setKey(K key) {
			if(key == null) {
				throw new NullPointerException();
			}
			this.key = key;
		}
		
		public V getValue() {
			return this.value;
		}
		
		public void setValue(V value) {
			this.value = value;
		}
	}
	
	private ArrayIndexedCollection<Pair> dictionary;
	
	
	/**
	 * constructor
	 */
	public Dictionary() {
		this.dictionary = new ArrayIndexedCollection<Pair>();
	}

	/**
	 * checks if dictionary is empty
	 * @return true if empty, otherwise false
	 */
	boolean isEmpty() {
		return this.dictionary.isEmpty();
	}
	
	
	/**
	 * checks what is size of the dictionary
	 * @return size of dictionary
	 */
	int size() {
		return this.dictionary.size();
	}
	
	/**
	 * empties the dictionary
	 */
	void clear() {
		this.dictionary.clear();
	}
	
	/**
	 * Adding a pair to the dictionary
	 * @param key  key that we will add in the dictionary if it isn't already there, if it is we will overwrite its value
	 * @param value value we want connected to the given key
	 * @return old value or null if the key previously wasn't in the dictionary
	 */
	V put(K key, V value) {
		
		Pair pair = new Pair(key, value);
		boolean found = false;
		V oldValue = null;
		
		for(int i = 0; i < this.dictionary.size() && !found; ++i) {
			if (this.dictionary.get(i).getKey().equals(key)) {
				found = true;
				oldValue = (V) this.dictionary.get(i).getValue();
				this.dictionary.remove(i);
				this.dictionary.insert(pair, i);
			}
		}
		
		if (!found) {
			this.dictionary.add(pair);
		}
		
		return oldValue;
	}
	
	/**
	 * gets value of given key
	 * @param key key whose value we want
	 * @return value of given key or null if key isn't in the dictionary
	 */
	V get(Object key) {
		
		boolean found = false;
		V val = null;
		for(int i = 0; i < this.dictionary.size() && !found; ++i) {
			if (this.dictionary.get(i).getKey().equals(key)) {
				val = (V) this.dictionary.get(i).getValue();
				found = true;
			}
		}
		
		return val;	
	}
	
	/**
	 * removes pair with given key 
	 * @param key key of the pair we want to remove
	 * @return value of key that was removed, or null if given key wasn't in dictionary
	 */
	V remove(K key) {

		boolean found = false;
		V val = null;
		
		for(int i = 0; i < this.dictionary.size() && !found; ++i) {
			if (this.dictionary.get(i).getKey().equals(key)) {
				found = true;
				val = (V) this.dictionary.get(i).getValue();
				this.dictionary.remove(i);
			}
		}
		
		return val;
	}
	
	
}
