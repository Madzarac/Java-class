package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;


public class LinkedListIndexedCollectionTest {
	
	
	@Test
	public void testFirstConstructorSize() {
		
		LinkedListIndexedCollection l = new LinkedListIndexedCollection();
		
		assertEquals(0, l.size());
	}
	
	
	@Test
	public void testSecondConstructor() {
		
		LinkedListIndexedCollection l1 = new LinkedListIndexedCollection();
		l1.add("Hi");
		l1.add("Bye");
		LinkedListIndexedCollection l2 = new LinkedListIndexedCollection(l1);
		
		assertArrayEquals(l1.toArray(), l2.toArray());
	}
	
	
	@Test
	public void testNullPointerExceptionInAdd() {
		
		assertThrows(NullPointerException.class, new Executable() {
			public void execute() throws Throwable {
				LinkedListIndexedCollection l = new LinkedListIndexedCollection();
				l.add(null);
			}
		});
	}
	
	
	@Test
	public void testAddGet() {
		
		LinkedListIndexedCollection l = new LinkedListIndexedCollection();
		l.add("Hello");
		
		assertEquals("Hello", l.get(0));
	}
	
	
	@Test
	public void testIndexOutOfBoundsExceptionInGetHigher() {
		
		assertThrows(IndexOutOfBoundsException.class, new Executable() {
			public void execute() throws Throwable {
				LinkedListIndexedCollection l = new LinkedListIndexedCollection();
				l.add("Hello");
				l.get(1);
			}
		});
	}
	
	
	@Test
	public void testIndexOutOfBoundsExceptionInGetLower() {
		
		assertThrows(IndexOutOfBoundsException.class, new Executable() {
			public void execute() throws Throwable {
				LinkedListIndexedCollection l = new LinkedListIndexedCollection();
				l.add("Hello");
				l.get(-1);
			}
		});
	}
	
	
	@Test
    public void testIsEmptyWhenItsEmpty() {
		
		LinkedListIndexedCollection l = new LinkedListIndexedCollection();
		
		assertEquals(true, l.isEmpty());
	}
	
	
	@Test
    public void testIsEmptyWhenItsNotEmpty() {
		
		LinkedListIndexedCollection l = new LinkedListIndexedCollection();
		l.add("Hello");
		
		assertEquals(false, l.isEmpty());
	}
	
	@Test
    public void testContainsWhenitDoesnt() {
		
		LinkedListIndexedCollection l = new LinkedListIndexedCollection();
		l.add("Hello");
		
		assertEquals(false, l.contains("Goodbye"));
	}
	
	
	@Test
    public void testContainsWhenItDoes() {
		
		LinkedListIndexedCollection l = new LinkedListIndexedCollection();
		l.add("Hello");
		l.add("Goodbye");
		
		assertEquals(true, l.contains("Goodbye"));
	}
	
	
	@Test
    public void testRemoveElWhenitDoesnt() {
		
		LinkedListIndexedCollection l = new LinkedListIndexedCollection();
		l.add("Hello");
		
		assertEquals(false, l.remove("Goodbye"));
	}
	
	
	@Test
    public void testRemoveElWhenItDoes() {
		
		LinkedListIndexedCollection l = new LinkedListIndexedCollection();
		l.add("Hello");
		l.add("Goodbye");
		l.remove("Goodbye");
		
		assertEquals(false, l.contains("Goodbye"));
	}
	
	
	@Test 
	public void testToArray() {
		LinkedListIndexedCollection l = new LinkedListIndexedCollection();
		l.add("Hello");
		l.add("Goodbye");
		l.add("Good morning");
		Object[] expected = {"Hello", "Goodbye", "Good morning"};
		
		assertArrayEquals(expected, l.toArray());
	}
	
	@Test 
	public void testToClear() {
		LinkedListIndexedCollection l = new LinkedListIndexedCollection();
		l.add("Hello");
		l.add("Goodbye");
		l.clear();
		
		assertEquals(true, l.isEmpty());
	}
	
	
	@Test 
	public void testAddAll() {
		LinkedListIndexedCollection l1 = new LinkedListIndexedCollection();
		l1.add("Hello");
		l1.add("Goodbye");
		l1.add("Good morning");
		LinkedListIndexedCollection l2 = new LinkedListIndexedCollection(l1);
		
		assertArrayEquals(l1.toArray(), l2.toArray());
	}
	
	
	@Test
	public void testinsertIndexOutOfBoundsExceptionHigher() {
		
		assertThrows(IndexOutOfBoundsException.class, new Executable() {
			public void execute() throws Throwable {
				LinkedListIndexedCollection l = new LinkedListIndexedCollection();
				l.add("Hello");
				l.add("Goodbye");
				l.insert("Out", 3);
			}
		});
		
	}
	
	
	@Test
	public void testinsertIndexOutOfBoundsExceptionLower() {
		
		assertThrows(IndexOutOfBoundsException.class, new Executable() {
			public void execute() throws Throwable {
				LinkedListIndexedCollection l = new LinkedListIndexedCollection();
				l.insert("Out", -1);
			}
		});
	}
	
	
	@Test
	public void testInsert() {
		LinkedListIndexedCollection l1 = new LinkedListIndexedCollection();
		l1.add("Hello");
		l1.add("Goodbye");
		l1.add("Good morning");
		LinkedListIndexedCollection l2 = new LinkedListIndexedCollection();
		l2.add("Hello");
		l2.add("Good morning");
		l2.insert("Goodbye", 1);
		
		assertArrayEquals(l1.toArray(), l2.toArray());
	}
	
	
	@Test 
	public void testIndexOfNull() {
		LinkedListIndexedCollection l = new LinkedListIndexedCollection();
		
		assertEquals(-1, l.indexOf(null));
	}
	
	
	@Test 
	public void testIndexOfFound() {
		LinkedListIndexedCollection l = new LinkedListIndexedCollection();
		l.add("Hello");
		l.add("Goodbye");
		
		assertEquals(1, l.indexOf("Goodbye"));
	}
	
	
	@Test 
	public void testIndexOfNotFound() {
		LinkedListIndexedCollection l = new LinkedListIndexedCollection();
		l.add("Hello");
		l.add("Goodbye");
		
		assertEquals(-1, l.indexOf("Not here"));
	}
	
	
	@Test
	public void testRemoveIndexOutOfBoundsExceptionHigher() {
		
		assertThrows(IndexOutOfBoundsException.class, new Executable() {
			public void execute() throws Throwable {
				LinkedListIndexedCollection l = new LinkedListIndexedCollection();
				l.add("Hello");
				l.add("Goodbye");
				l.remove(2);
			}
		});
		
	}
	
	
	@Test
	public void testRemoveIndexOutOfBoundsExceptionLower() {
		
		assertThrows(IndexOutOfBoundsException.class, new Executable() {
			public void execute() throws Throwable {
				LinkedListIndexedCollection l = new LinkedListIndexedCollection();
				l.add("Hello");
				l.add("Goodbye");
				l.remove(-1);
			}
		});
	}
	
	
	@Test
	public void testRemoveByIndex() {
		LinkedListIndexedCollection l1 = new LinkedListIndexedCollection();
		l1.add("Hello");
		l1.add("Goodbye");
		LinkedListIndexedCollection l2 = new LinkedListIndexedCollection();
		l2.add("Hello");
		l2.add("remove");
		l2.add("Goodbye");
		l2.remove(1);
		
		assertArrayEquals(l1.toArray(), l2.toArray());
	}
	

}
