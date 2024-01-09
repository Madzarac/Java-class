package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author TeaMadzarac
 * @version 12/11/2022
 */
public class QueryParser {
	
	private String userInput;

	/**
	 * constructor
	 * @param userInput everything user entered after query
	 */
	public QueryParser(String userInput) {
		this.userInput = userInput;
	}
	
	/**
	 * Check if it is a direct query
	 * @return true if query was of form jmbag="xxx"
	 */
	boolean isDirectQuery() {
		String s = userInput;
		if(s.toLowerCase().contains("and")) {
			return false;
			
		} else if(s.contains("=")) {
			 s = s.trim();
			if(s.startsWith("jmbag")) {
				return true;
			} else {
				return false;
			}
			
		} else {
			return false;
		}
	}
	
	
	/**
	 * gets value of jmbag of direct query
	 * @return string value of jmbag
	 */
	String getQueriedJMBAG() {
		if(this.isDirectQuery()) {
			String s = userInput;
			s = s.replace("=","");
			s = s.replace("jmbag", "");
			s = s.trim();
			s = s.replace("\"", "");
			return s;
			
		} else {
			throw new IllegalStateException();
		}
	}
	
	/**
	 * works for all queries
	 * @return list of conditional expression from query
	 */
	List<ConditionalExpression> getQuery() {
		
		List<ConditionalExpression> list = new ArrayList<ConditionalExpression>();
		
		
		Lexer lexer = new Lexer(this.userInput);
		while(!(lexer.nextToken().getType().equals(TokenType.EOF))) {
			IFieldValueGetter f = null;
			IComparisonOperator o = null;
			String s;
			
			
			if(lexer.getToken().getType().equals(TokenType.AND)) {
				continue;
				
				
				
			} else if(lexer.getToken().getValue().equals("field")) {
				
				if(lexer.getToken().getType().equals(TokenType.FIRSTNAME)) {
					f = FieldValueGetters.FIRST_NAME;
				} else if(lexer.getToken().getType().equals(TokenType.LASTNAME)) {
					f = FieldValueGetters.LAST_NAME;
				} else if(lexer.getToken().getType().equals(TokenType.JMBAG)) {
					f = FieldValueGetters.JMBAG;
				}
				lexer.nextToken();
				
				
				if(lexer.getToken().getValue().equals("operator")) {
					if(lexer.getToken().getType().equals(TokenType.OGREATER)) {
						o = ComparisonOperators.GREATER;
					} else if(lexer.getToken().getType().equals(TokenType.OLESS)) {
						o = ComparisonOperators.LESS;
					} else if(lexer.getToken().getType().equals(TokenType.OGREATEREQ)) {
						o = ComparisonOperators.GREATER_OR_EQUALS;
					} else if(lexer.getToken().getType().equals(TokenType.OLESSEQ)) {
						o = ComparisonOperators.LESS_OR_EQUALS;
					} else if(lexer.getToken().getType().equals(TokenType.OEQ)) {
						o = ComparisonOperators.EQUALS;
					} else if(lexer.getToken().getType().equals(TokenType.ONOT)) {
						o = ComparisonOperators.NOT_EQUALS;
					} else if(lexer.getToken().getType().equals(TokenType.OLIKE)) {
						o = ComparisonOperators.LIKE;
					}
					
					lexer.nextToken();
					
					if(lexer.getToken().getType().equals(TokenType.SLITERAL)) {
						s = (String)lexer.getToken().getValue();
						ConditionalExpression ce = new ConditionalExpression(f,s,o);
						list.add(ce);
						continue;
					}
					
				}
				
				throw new IllegalArgumentException("Wrong order of tokens in query command");
				
			} else {
				throw new IllegalArgumentException("Wrong order of tokens in query command");
			}
			
			
			
		}
		
		return list;
	}
		
		
		
		
		
	
	
}
