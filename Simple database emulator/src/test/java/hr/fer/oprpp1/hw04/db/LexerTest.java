package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class LexerTest {
	
	@Test
	public void testCombinedInput() {
		// Lets check for several symbols...
		Lexer lexer = new Lexer("  firstName>\"A\" and lastName LIKE \"B*\"");

		Token correctData[] = {
			new Token(TokenType.FIRSTNAME, "field"),
			new Token(TokenType.OGREATER, "operator"),
			new Token(TokenType.SLITERAL, "A"),
			new Token(TokenType.AND, "and"),
			new Token(TokenType.LASTNAME, "field"),
			new Token(TokenType.OLIKE, "operator"),
			new Token(TokenType.SLITERAL, "B*"),
			new Token(TokenType.EOF, null)
		};

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testCombinedInput2() {
		// Lets check for several symbols...
		Lexer lexer = new Lexer("  firstName    >	\"A\" and lastNameLIKE \"B*\"");

		Token correctData[] = {
			new Token(TokenType.FIRSTNAME, "field"),
			new Token(TokenType.OGREATER, "operator"),
			new Token(TokenType.SLITERAL, "A"),
			new Token(TokenType.AND, "and"),
			new Token(TokenType.LASTNAME, "field"),
			new Token(TokenType.OLIKE, "operator"),
			new Token(TokenType.SLITERAL, "B*"),
			new Token(TokenType.EOF, null)
		};

		checkTokenStream(lexer, correctData);
	}
	
	
	
	
	
	// Helper method for checking if lexer generates the same stream of tokens
		// as the given stream.
		private void checkTokenStream(Lexer lexer, Token[] correctData) {
			int counter = 0;
			for(Token expected : correctData) {
				Token actual = lexer.nextToken();
				String msg = "Checking token "+counter + ":";
				assertEquals(expected.getType(), actual.getType(), msg);
				assertEquals(expected.getValue(), actual.getValue(), msg);
				counter++;
			}
		}
}
