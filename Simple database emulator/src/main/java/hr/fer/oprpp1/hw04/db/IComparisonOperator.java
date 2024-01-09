package hr.fer.oprpp1.hw04.db;

/**
 * strategy
 * @author TeaMadzarac
 * @version 11/11/2022
 *
 */
public interface IComparisonOperator {
	
	public boolean satisfied(String value1, String value2);
	
}

