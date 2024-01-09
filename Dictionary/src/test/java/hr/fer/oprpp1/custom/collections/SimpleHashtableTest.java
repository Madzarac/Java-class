package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.collections.SimpleHashtable.TableEntry;

public class SimpleHashtableTest {

	
	@Test
	public void testNullKey() {
		
		SimpleHashtable<Integer, String> table = new SimpleHashtable<>();
		assertThrows(NullPointerException.class, () -> table.put(null, "No"));
	}
	
	
	@Test
	public void testPutOver() {
		SimpleHashtable<Integer, String> table = new SimpleHashtable<>();
		table.put(1, "Paris");
		table.put(5, "Milan");
		table.put(3, "Tokyo");
		table.put(3, "Zagreb");
		assertEquals("Zagreb", table.get(3));
	}
	
	@Test
	public void testPutOverInSmallerTableOne() {
		SimpleHashtable<Integer, String> table = new SimpleHashtable<>(2);
		table.put(1, "Paris");
		table.put(5, "Milan");
		table.put(3, "Tokyo");
		table.put(3, "Zagreb");
		assertEquals("Zagreb", table.get(3));
	}
	
	@Test
	public void testPutOverInSmallerTableTwo() {
		SimpleHashtable<Integer, String> table = new SimpleHashtable<>(2);
		table.put(1, "Paris");
		table.put(5, "Milan");
		table.put(9, "Tokyo");
		table.put(2, "Ohio");
		table.put(4, "Chicago");
		table.put(7, "Toronto");
		table.put(3, "Tokyo");
		table.put(3, "Zagreb");
		assertEquals("Zagreb", table.get(3));
	}
	
	@Test
	public void testPutOverInSmallerTableOnlyOneKey() {
		SimpleHashtable<Integer, String> table = new SimpleHashtable<>(2);
		table.put(1, "Paris");
		assertEquals("Paris", table.get(1));
	}
	
	@Test
	public void testGeteNullKey() {
		SimpleHashtable<Integer, String> table = new SimpleHashtable<>(2);
		table.put(1, "Paris");
		assertEquals(null, table.get(null));
	}
	
	@Test
	public void testAddNullKey() {
		// must throw!
		SimpleHashtable<Integer, String> table = new SimpleHashtable<>();
		assertThrows(NullPointerException.class, () -> table.put(null, "No"));
	}
	
	@Test
	public void testSize() {
		SimpleHashtable<Integer, String> table = new SimpleHashtable<>(2);
		table.put(1, "Paris");
		table.put(5, "Milan");
		table.put(9, "Tokyo");
		table.put(2, "Ohio");
		table.put(4, "Chicago");
		table.put(7, "Toronto");
		table.put(3, "Tokyo");
		table.put(3, "Zagreb");
		assertEquals(7, table.size());
	}
	
	@Test
	public void testContainsKeyWithKeyOnTheEnd() {
		SimpleHashtable<Integer, String> table = new SimpleHashtable<>(2);
		table.put(1, "Paris");
		table.put(5, "Milan");
		table.put(9, "Tokyo");
		table.put(2, "Ohio");
		table.put(4, "Chicago");
		table.put(7, "Toronto");
		table.put(3, "Tokyo");
		table.put(11, "Zagreb");
		assertEquals(true, table.containsKey(11));
	}
	
	@Test
	public void testContainsKeyOnlyKey() {
		SimpleHashtable<Integer, String> table = new SimpleHashtable<>(2);
		table.put(11, "Zagreb");
		assertEquals(true, table.containsKey(11));
	}
	
	@Test
	public void testContainsKeyNullKey() {
		SimpleHashtable<Integer, String> table = new SimpleHashtable<>(2);
		table.put(11, "Zagreb");
		assertEquals(false, table.containsKey(null));
	}
	
	
	
	@Test
	public void testContainsKeyWhenDoesNotContainIt() {
		SimpleHashtable<Integer, String> table = new SimpleHashtable<>(2);
		table.put(1, "Paris");
		table.put(5, "Milan");
		table.put(9, "Tokyo");
		table.put(2, "Ohio");
		table.put(4, "Chicago");
		table.put(7, "Toronto");
		table.put(3, "Tokyo");
		table.put(11, "Zagreb");
		assertEquals(false, table.containsKey(13));
	}
	
	@Test
	public void testContainsValueWithValueOnTheEnd() {
		SimpleHashtable<Integer, String> table = new SimpleHashtable<>(2);
		table.put(1, "Paris");
		table.put(5, "Milan");
		table.put(9, "Tokyo");
		table.put(2, "Ohio");
		table.put(4, "Chicago");
		table.put(7, "Toronto");
		table.put(3, "Tokyo");
		table.put(11, "Zagreb");
		assertEquals(true, table.containsValue("Zagreb"));
	}
	
	@Test
	public void testContainsValueOnlyValue() {
		SimpleHashtable<Integer, String> table = new SimpleHashtable<>(2);
		table.put(11, "Zagreb");
		assertEquals(true, table.containsValue("Zagreb"));
	}
	
	@Test
	public void testContainsValueNullValueWhenItDoesnt() {
		SimpleHashtable<Integer, String> table = new SimpleHashtable<>(2);
		table.put(11, "Zagreb");
		assertEquals(false, table.containsValue(null));
	}
	
	@Test
	public void testContainsValueNullValueWhenItDoes() {
		SimpleHashtable<Integer, String> table = new SimpleHashtable<>(2);
		table.put(1, null);
		assertEquals(true, table.containsValue(null));
	}
	
	@Test
	public void testContainsValueWhenDoesNotContainIt() {
		SimpleHashtable<Integer, String> table = new SimpleHashtable<>(2);
		table.put(1, "Paris");
		table.put(5, "Milan");
		table.put(9, "Tokyo");
		table.put(2, "Ohio");
		table.put(4, "Chicago");
		table.put(7, "Toronto");
		table.put(3, "Tokyo");
		table.put(11, "Zagreb");
		assertEquals(false, table.containsValue(13));
	}
	
	@Test
	public void testRemoveOnlyWalueReturn() {
		SimpleHashtable<Integer, String> table = new SimpleHashtable<>(2);
		table.put(1, "Zagreb");
		assertEquals("Zagreb", table.remove(1));
	}
	
	@Test
	public void testRemoveOnlyWalueSize() {
		SimpleHashtable<Integer, String> table = new SimpleHashtable<>(2);
		table.put(1, "Zagreb");
		table.remove(1);
		assertEquals(0, table.size());
	}
	
	@Test
	public void testRemoveWhenDoesNotContainIt() {
		SimpleHashtable<Integer, String> table = new SimpleHashtable<>(2);
		table.put(1, "Paris");
		table.put(5, "Milan");
		table.put(9, "Tokyo");
		assertEquals(null, table.remove(13));
	}
	
	@Test
	public void testRemoveWithValueOnTheEnd() {
		SimpleHashtable<Integer, String> table = new SimpleHashtable<>(2);
		table.put(1, "Paris");
		table.put(5, "Milan");
		table.put(9, "Tokyo");
		table.put(2, "Ohio");
		table.put(4, "Chicago");
		table.put(7, "Toronto");
		table.put(3, "Tokyo");
		table.put(11, "Zagreb");
		assertEquals("Zagreb", table.remove(11));
	}
	
	
	@Test
	public void testIsEmptyTrue() {
		SimpleHashtable<Integer, String> table = new SimpleHashtable<>(2);
		table.put(1, "Zagreb");
		table.remove(1);
		assertEquals(true, table.isEmpty());
	}
	
	@Test
	public void testIsEmptyTrueNothingWasAdded() {
		SimpleHashtable<Integer, String> table = new SimpleHashtable<>(2);
		assertEquals(true, table.isEmpty());
	}
	
	
	@Test
	public void testIsEmptyFalse() {
		SimpleHashtable<Integer, String> table = new SimpleHashtable<>(2);
		table.put(1, "Zagreb");
		assertEquals(false, table.isEmpty());
	}
	
	@Test
	public void testToStringWhenItsEmpty() {
		SimpleHashtable<Integer, String> table = new SimpleHashtable<>(2);
		assertEquals("[]", table.toString());
	}
	
	@Test
	public void testToString() {
		SimpleHashtable<Integer, String> table = new SimpleHashtable<>(2);
		table.put(4, "Chicago");
		table.put(7, "Toronto");
		assertEquals("[4=Chicago, 7=Toronto]", table.toString());
	}
	
	
	@Test
	public void testToArray() {
		
		SimpleHashtable<Integer, String> table1 = new SimpleHashtable<>(2);
		table1.put(4, "Chicago");
		table1.put(7, "Toronto");

		TableEntry<Integer, String>[] a = table1.toArray();
		assertEquals("Toronto", a[1].getValue());
	}
	
	@Test
	public void testToBigArray() {
		
		SimpleHashtable<Integer, String> table = new SimpleHashtable<>(2);
		table.put(1, "Paris");
		table.put(5, "Milan");
		table.put(9, "Tokyo");
		table.put(2, "Ohio");
		table.put(4, "Chicago");
		table.put(7, "Toronto");
		table.put(3, "Tokyo");
		table.put(11, "Zagreb");
		table.put(8, "Bjelovar");

		TableEntry<Integer, String>[] a = table.toArray();
		assertEquals(9, a.length);
	}
	
	@Test
	public void testRemoveOnFirstSlot() {
		SimpleHashtable<Integer, String> table = new SimpleHashtable<>(2);
		table.put(1, "Zagreb");
		table.put(3, "Berlin");
		assertEquals("Zagreb", table.remove(1));
	}
	
	@Test
	public void testRemoveOnFirstSlotOutOfFour() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); 
		
		assertEquals(5, examMarks.remove("Ivana"));
	}
	
	@Test
	public void testRemoveOnFirstSlotOutOfFourSize() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); 
		examMarks.remove("Ivana");
		
		assertEquals(3, examMarks.size());
	}
	
}
