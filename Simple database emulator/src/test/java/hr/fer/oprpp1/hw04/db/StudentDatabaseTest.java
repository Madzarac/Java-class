package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;


public class StudentDatabaseTest {

	@Test
	public void testforJMBAGwhenItsNotThere() {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(
					Paths.get("src/test/resources/database.txt"), 
					StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		StudentDatabase db = new StudentDatabase(lines);
		assertEquals(null, db.forJMBAG("1234432"));
	}
	
	@Test
	public void testforJMBAGwhenItIsThere() {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(
					Paths.get("src/test/resources/database.txt"), 
					StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		StudentDatabase db = new StudentDatabase(lines);
		StudentRecord s = new StudentRecord("0000000015", "Glavinić Pecotić", "Kristijan", 4);
		assertEquals(s, db.forJMBAG("0000000015"));
	}
	
	@Test
	public void testFilterWhenAlwaysTrue() {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(
					Paths.get("src/test/resources/database.txt"), 
					StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		StudentDatabase db = new StudentDatabase(lines);
		
		class AlwaysTrue implements IFilter {

			@Override
			public boolean accepts(StudentRecord record) {
				return true;
			}
			
		}
		AlwaysTrue f = new AlwaysTrue();
		assertEquals(db.getList(), db.filter(f));
	}
	
	
	@Test
	public void testFilterWhenAlwaysFalse() {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(
					Paths.get("src/test/resources/database.txt"), 
					StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		StudentDatabase db = new StudentDatabase(lines);
		
		class AlwaysFalse implements IFilter {

			@Override
			public boolean accepts(StudentRecord record) {
				return false;
			}
			
		}
		AlwaysFalse f = new AlwaysFalse();
		List<StudentRecord> list = new ArrayList<>();
		assertEquals(list, db.filter(f));
	}
	
	@Test
	public void testFilter() {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(
					Paths.get("src/test/resources/database.txt"), 
					StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		StudentDatabase db = new StudentDatabase(lines);
		
		class Kristijan implements IFilter {

			@Override
			public boolean accepts(StudentRecord record) {
				if(record.getFirstName().equals("Kristijan")) {
					return true;
				} else {
					return false;
				}
			}
			
		}
		Kristijan f = new Kristijan();
		StudentRecord s = new StudentRecord("0000000015", "Glavinić Pecotić", "Kristijan", 4);
		StudentRecord s2 = new StudentRecord("0000000062", "Zadro", "Kristijan", 3);
		List<StudentRecord> list = new ArrayList<>();
		list.add(s);
		list.add(s2);
		assertEquals(list, db.filter(f));
	}
	
	@Test
	public void testLESS() {
		IComparisonOperator oper = ComparisonOperators.LESS;
		assertEquals(true, oper.satisfied("Ana", "Jasna"));
	}
	
	@Test
	public void testLessFalse() {
		IComparisonOperator oper = ComparisonOperators.LESS;
		assertEquals(false, oper.satisfied("Ana", "Ana"));
	}
	
	@Test
	public void testLessOrEqualsTrue() {
		IComparisonOperator oper = ComparisonOperators.LESS_OR_EQUALS;
		assertEquals(true, oper.satisfied("Ana", "Ana"));
	}
	
	@Test
	public void testLessOrEqualsFalse() {
		IComparisonOperator oper = ComparisonOperators.LESS_OR_EQUALS;
		assertEquals(false, oper.satisfied("Anaa", "Ana"));
	}
	
	@Test
	public void testGreater() {
		IComparisonOperator oper = ComparisonOperators.GREATER;
		assertEquals(true, oper.satisfied("Zdenka", "Jasna"));
	}
	
	@Test
	public void testGreaterFalse() {
		IComparisonOperator oper = ComparisonOperators.GREATER;
		assertEquals(false, oper.satisfied("Ana", "Ana"));
	}
	
	@Test
	public void testGreaterOrEqualsTrue() {
		IComparisonOperator oper = ComparisonOperators.GREATER_OR_EQUALS;
		assertEquals(true, oper.satisfied("Ana", "Ana"));
	}
	
	@Test
	public void testGreaterOrEqualsFalse() {
		IComparisonOperator oper = ComparisonOperators.GREATER_OR_EQUALS;
		assertEquals(false, oper.satisfied("Ana", "Anaa"));
	}
	

	@Test
	public void testEquals() {
		IComparisonOperator oper = ComparisonOperators.EQUALS;
		assertEquals(true, oper.satisfied("Ana", "Ana"));
	}
	
	@Test
	public void testEqualsFalse() {
		IComparisonOperator oper = ComparisonOperators.EQUALS;
		assertEquals(false, oper.satisfied("Ani", "Ana"));
	}
	
	@Test
	public void testNotEquals() {
		IComparisonOperator oper = ComparisonOperators.NOT_EQUALS;
		assertEquals(false, oper.satisfied("Šime", "Šime"));
	}
	
	@Test
	public void testNotEqualsFalse() {
		IComparisonOperator oper = ComparisonOperators.NOT_EQUALS;
		assertEquals(true, oper.satisfied("Ani", "Ana"));
	}
	
	@Test
	public void testLikeStarAtTheStart() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertEquals(true, oper.satisfied("Anamarija", "*marija"));
	}
	
	@Test
	public void testLikeMultipleStar() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertEquals(false, oper.satisfied("Anamarija", "*mar*ija"));
	}
	
	
	@Test
	public void testLikeStarAtTheEnd() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertEquals(true, oper.satisfied("Anamarija", "Ana*"));
	}
	
	@Test
	public void testLike1() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertEquals(false, oper.satisfied("Zagreb", "Aba*"));
	}
	
	@Test
	public void testLike2() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertEquals(false, oper.satisfied("AAA", "AA*AA"));
	}
	
	
	@Test
	public void testLike3() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertEquals(true, oper.satisfied("AAAA", "AA*AA"));
	}
	
	@Test
	public void testFIRST_NAME() {
		StudentRecord s = new StudentRecord("0000000015", "Glavinić Pecotić", "Kristijan", 4);
		assertEquals("Kristijan",  FieldValueGetters.FIRST_NAME.get(s));
	}
	
	@Test
	public void testLAST_NAME() {
		StudentRecord s = new StudentRecord("0000000015", "Glavinić Pecotić", "Kristijan", 4);
		assertEquals("Glavinić Pecotić",  FieldValueGetters.LAST_NAME.get(s));
	}
	
	@Test
	public void testJMBAG() {
		StudentRecord s = new StudentRecord("0000000015", "Glavinić Pecotić", "Kristijan", 4);
		assertEquals("0000000015",  FieldValueGetters.JMBAG.get(s));
	}
	
	@Test
	public void testConditionalExpressionTrue() {

		ConditionalExpression expr = new ConditionalExpression(
				 FieldValueGetters.LAST_NAME,
				 "Bos*",
				 ComparisonOperators.LIKE
				);
		
		StudentRecord record = new StudentRecord("0000000015", "Bosnić", "Andrea", 4);
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(
		 expr.getFieldGetter().get(record), // returns lastName from given record
		 expr.getStringLiteral() // returns "Bos*"
		);
		assertEquals(true,  recordSatisfies);
	}
	
	
	@Test
	public void testConditionalExpressionFalse() {

		ConditionalExpression expr = new ConditionalExpression(
				 FieldValueGetters.LAST_NAME,
				 "Bos*",
				 ComparisonOperators.LIKE
				);
		
		StudentRecord record = new StudentRecord("0000000015", "Glavinić Pecotić", "Kristijan", 4);
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(
		 expr.getFieldGetter().get(record), // returns lastName from given record
		 expr.getStringLiteral() // returns "Bos*"
		);
		assertEquals(false,  recordSatisfies);
	}
	
	
	
}
