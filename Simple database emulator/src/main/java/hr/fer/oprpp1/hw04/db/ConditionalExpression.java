package hr.fer.oprpp1.hw04.db;

/**
 * 
 * @author TeaMadzarac
 * @version 11/11/2022
 */
public class ConditionalExpression {
	
	private IFieldValueGetter fieldGetter;
	private String stringLiteral;
	private IComparisonOperator comparisonOperator;
	
	/**
	 * constructor
	 * @param getter IFieldValueGetter strategy
	 * @param s string literal
	 * @param operator IComparisonOperator strategy
	 */
	public ConditionalExpression(IFieldValueGetter getter, String s, IComparisonOperator operator) {
		super();
		this.fieldGetter = getter;
		this.stringLiteral = s;
		this.comparisonOperator = operator;
	}

	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}

	public String getStringLiteral() {
		return stringLiteral;
	}

	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}

	

}
