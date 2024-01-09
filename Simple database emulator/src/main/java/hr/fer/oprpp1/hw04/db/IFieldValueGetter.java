package hr.fer.oprpp1.hw04.db;

/**
 * strategy
 * @author TeaMadzarac
 * @version 11/11/2022
 */
public interface IFieldValueGetter {

	/**
	 * responsible for obtaining a requested field value from given StudentRecord
	 * @param record given StudentRecord
	 * @return requested field
	 */
	public String get(StudentRecord record);
	
}
