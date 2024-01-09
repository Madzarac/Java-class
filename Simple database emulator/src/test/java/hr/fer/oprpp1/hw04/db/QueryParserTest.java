package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class QueryParserTest {
	
	@Test
	public void testisDirectQuery() {
	
		QueryParser qp1 = new QueryParser(" jmbag =\"0123456789\" ");
		assertEquals(true,  qp1.isDirectQuery());
	}
	
	
	@Test
	public void testgetQueriedJMBAG() {
		
		QueryParser qp1 = new QueryParser(" jmbag =\"0123456789\" ");
		assertEquals("0123456789",  qp1.getQueriedJMBAG());		
	}
	
	@Test
	public void testgetQueriedJMBAGSize() {
		
		QueryParser qp1 = new QueryParser(" jmbag =\"0123456789\" ");
		assertEquals(1,  qp1.getQuery().size());		
	}
	
	@Test
	public void testisDirectQueryFalse() {
	
		QueryParser qp2 = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
		assertEquals(false,  qp2.isDirectQuery());
	}
	
	@Test
	public void testQueriedJMBAGException() {
	
		QueryParser qp2 = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
		assertThrows(IllegalStateException.class, () -> qp2.getQueriedJMBAG());
	}
	
	
	@Test
	public void testgetQueriedJMBAGSize2() {
		
		QueryParser qp2 = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
		assertEquals(2,  qp2.getQuery().size());		
	}

}
