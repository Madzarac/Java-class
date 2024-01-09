package hr.fer.oprpp1.custom.collections;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.collections.Dictionary;


public class DictionaryTest {


	@Test
	public void testNullKey() {
		// must throw!
		Dictionary<Integer, String> d = new Dictionary<>();
		assertThrows(NullPointerException.class, () -> d.put(null, "No"));
	}
	
	@Test
	public void testNullValue() {
		// must throw!
		Dictionary<Integer, String> d = new Dictionary<>();
		d.put(1, null);
		String s = null;
		assertEquals(s, d.get(1));
	}
	
	@Test
	public void testIsNotEmpty() {
		Dictionary<Integer, String> d = new Dictionary<>();
		d.put(1, "Paris");
;
		assertEquals(false, d.isEmpty());
	}
	
	@Test
	public void testIsEmpty() {
		Dictionary<Integer, String> d = new Dictionary<>();
;
		assertEquals(true, d.isEmpty());
	}
	
	@Test
	public void testSize() {
		Dictionary<Integer, String> d = new Dictionary<>();
		d.put(1, "Paris");
		d.put(5, "Milan");
		d.put(3, "Tokyo");
		d.put(6, "Zagreb");
		assertEquals(4, d.size());
	}
	
	@Test
	public void testSizeZero() {
		Dictionary<Integer, String> d = new Dictionary<>();
		
		assertEquals(0, d.size());
	}
	
	
	@Test
	public void testGet() {
		Dictionary<Integer, String> d = new Dictionary<>();
		d.put(1, "Paris");
		d.put(5, "Milan");
		d.put(3, "Tokyo");
		d.put(6, "Zagreb");
		assertEquals("Tokyo", d.get(3));
	}
	
	@Test
	public void testPutOver() {
		Dictionary<Integer, String> d = new Dictionary<>();
		d.put(1, "Paris");
		d.put(5, "Milan");
		d.put(3, "Tokyo");
		d.put(3, "Zagreb");
		assertEquals("Zagreb", d.get(3));
	}

	@Test
	public void testRemoveReturn() {
		Dictionary<Integer, String> d = new Dictionary<>();
		d.put(1, "Paris");
		d.put(5, "Milan");
		d.put(3, "Tokyo");
		assertEquals("Tokyo", d.remove(3));
	}
	
	@Test
	public void testRemoveNotContained() {
		Dictionary<Integer, String> d = new Dictionary<>();
		d.put(1, "Paris");
		d.put(5, "Milan");
		d.put(3, "Tokyo");
		assertEquals(null, d.remove(7));
	}
	
	@Test
	public void testRemove() {
		Dictionary<Integer, String> d = new Dictionary<>();
		d.put(1, "Paris");
		d.remove(1);
		assertEquals(0, d.size());
	}
}
