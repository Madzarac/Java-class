package hr.fer.oprpp1.custom.collections;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class ArrayIndexedCollectionTest {

	
	
	@Test
	public void testIllegalArgumentExceptionInConstructor() {
		
		assertThrows(IllegalArgumentException.class, new Executable() {
			public void execute() throws Throwable {
				new ArrayIndexedCollection(0);
			}
		});
		
		
		
	}
	
	
	@Test
	public void testNullPointerExceptionInConstructor() {
		
		assertThrows(NullPointerException.class, new Executable() {
			public void execute() throws Throwable {
				new ArrayIndexedCollection(null, 1);
			}
		});
	}
	
	
	@Test
	public void testConstructor() {
		
		ArrayIndexedCollection a1 = new ArrayIndexedCollection();
		a1.add("Hello");
		a1.add("Goodbye");
		ArrayIndexedCollection a2 = new ArrayIndexedCollection(a1);
		
		assertArrayEquals(a1.toArray(), a2.toArray());
	}
	
	
	@Test
	public void testNullPointerExceptionInAdd() {
		
		assertThrows(NullPointerException.class, new Executable() {
			public void execute() throws Throwable {
				new ArrayIndexedCollection(null, 1);
			}
		});
	}
	
	
	@Test
	public void testAddGet() {
		
		ArrayIndexedCollection a = new ArrayIndexedCollection();
		a.add("Hello");
		
		assertEquals("Hello", a.get(0));
	}
	
	
	@Test
	public void testIndexOutOfBoundsExceptionInGetHigher() {
		
		assertThrows(IndexOutOfBoundsException.class, new Executable() {
			public void execute() throws Throwable {
				ArrayIndexedCollection a = new ArrayIndexedCollection();
				a.add("Hello");
				a.get(1);
			}
		});
	}
	
	
	@Test
	public void testIndexOutOfBoundsExceptionInGetLower() {
		
		assertThrows(IndexOutOfBoundsException.class, new Executable() {
			public void execute() throws Throwable {
				ArrayIndexedCollection a = new ArrayIndexedCollection();
				a.add("Hello");
				a.get(-1);
			}
		});
	}
	
	
	@Test
    public void testIsEmptyWhenItsEmpty() {
		
		ArrayIndexedCollection a = new ArrayIndexedCollection();
		
		assertEquals(true, a.isEmpty());
	}
	
	
	@Test
    public void testIsEmptyWhenItsNotEmpty() {
		
		ArrayIndexedCollection a = new ArrayIndexedCollection();
		a.add("Hello");
		
		assertEquals(false, a.isEmpty());
	}
	
	@Test
    public void testContainsWhenitDoesnt() {
		
		ArrayIndexedCollection a = new ArrayIndexedCollection();
		a.add("Hello");
		
		assertEquals(false, a.contains("Goodbye"));
	}
	
	
	@Test
    public void testContainsWhenItDoes() {
		
		ArrayIndexedCollection a = new ArrayIndexedCollection();
		a.add("Hello");
		a.add("Goodbye");
		
		assertEquals(true, a.contains("Goodbye"));
	}
	
	
	@Test
    public void testRemoveElWhenitDoesnt() {
		
		ArrayIndexedCollection a = new ArrayIndexedCollection();
		a.add("Hello");
		
		assertEquals(false, a.remove("Goodbye"));
	}
	
	
	@Test
    public void testRemoveElWhenItDoes() {
		
		ArrayIndexedCollection a = new ArrayIndexedCollection();
		a.add("Hello");
		a.add("Goodbye");
		a.remove("Goodbye");
		
		assertEquals(false, a.contains("Goodbye"));
	}
	
	
	@Test 
	public void testToArray() {
		ArrayIndexedCollection a = new ArrayIndexedCollection();
		a.add("Hello");
		a.add("Goodbye");
		a.add("Good morning");
		Object[] expected = {"Hello", "Goodbye", "Good morning"};
		
		assertArrayEquals(expected, a.toArray());
	}
	
	@Test 
	public void testToClear() {
		ArrayIndexedCollection a = new ArrayIndexedCollection();
		a.add("Hello");
		a.add("Goodbye");
		a.clear();
		
		assertEquals(true, a.isEmpty());
	}
	
	
	@Test 
	public void testAddAll() {
		ArrayIndexedCollection a1 = new ArrayIndexedCollection();
		a1.add("Hello");
		a1.add("Goodbye");
		a1.add("Good morning");
		ArrayIndexedCollection a2 = new ArrayIndexedCollection(a1);
		
		assertArrayEquals(a1.toArray(), a2.toArray());
	}
	
	
	@Test
	public void testinsertIndexOutOfBoundsExceptionHigher() {
		
		assertThrows(IndexOutOfBoundsException.class, new Executable() {
			public void execute() throws Throwable {
				ArrayIndexedCollection a = new ArrayIndexedCollection();
				a.add("Hello");
				a.add("Goodbye");
				a.insert("Out", 3);
			}
		});
		
	}
	
	
	@Test
	public void testinsertIndexOutOfBoundsExceptionLower() {
		
		assertThrows(IndexOutOfBoundsException.class, new Executable() {
			public void execute() throws Throwable {
				ArrayIndexedCollection a = new ArrayIndexedCollection();
				a.insert("Out", -1);
			}
		});
	}
	
	
	@Test
	public void testInsert() {
		ArrayIndexedCollection a1 = new ArrayIndexedCollection();
		a1.add("Hello");
		a1.add("Goodbye");
		a1.add("Good morning");
		ArrayIndexedCollection a2 = new ArrayIndexedCollection();
		a2.add("Hello");
		a2.add("Good morning");
		a2.insert("Goodbye", 1);
		
		assertArrayEquals(a1.toArray(), a2.toArray());
	}
	
	
	@Test 
	public void testIndexOfNull() {
		ArrayIndexedCollection a = new ArrayIndexedCollection();
		
		assertEquals(-1, a.indexOf(null));
	}
	
	
	@Test 
	public void testIndexOfFound() {
		ArrayIndexedCollection a = new ArrayIndexedCollection();
		a.add("Hello");
		a.add("Goodbye");
		
		assertEquals(1, a.indexOf("Goodbye"));
	}
	
	
	@Test 
	public void testIndexOfNotFound() {
		ArrayIndexedCollection a = new ArrayIndexedCollection();
		a.add("Hello");
		a.add("Goodbye");
		
		assertEquals(-1, a.indexOf("Not here"));
	}
	
	
	@Test
	public void testRemoveIndexOutOfBoundsExceptionHigher() {
		
		assertThrows(IndexOutOfBoundsException.class, new Executable() {
			public void execute() throws Throwable {
				ArrayIndexedCollection a = new ArrayIndexedCollection();
				a.add("Hello");
				a.add("Goodbye");
				a.remove(2);
			}
		});
		
	}
	
	
	@Test
	public void testRemoveIndexOutOfBoundsExceptionLower() {
		
		assertThrows(IndexOutOfBoundsException.class, new Executable() {
			public void execute() throws Throwable {
				ArrayIndexedCollection a = new ArrayIndexedCollection();
				a.add("Hello");
				a.add("Goodbye");
				a.remove(-1);
			}
		});
	}
	
	
	@Test
	public void testRemoveByIndex() {
		ArrayIndexedCollection a1 = new ArrayIndexedCollection();
		a1.add("Hello");
		a1.add("Goodbye");
		ArrayIndexedCollection a2 = new ArrayIndexedCollection();
		a2.add("Hello");
		a2.add("remove");
		a2.add("Goodbye");
		a2.remove(1);
		
		assertArrayEquals(a1.toArray(), a2.toArray());
	}
}
