package hr.fer.oprpp1.hw04.db;

/**
 * strategies
 * @author TeaMadzarac
 * @version 11/11/2022
 *
 */
public class FieldValueGetters {
	
	public static final IFieldValueGetter FIRST_NAME = new GetFirstName();
	public static final IFieldValueGetter LAST_NAME = new GetLastName();
	public static final IFieldValueGetter JMBAG = new GetJmbag();
	
	
	
	private static class GetFirstName implements IFieldValueGetter {

		@Override
		public String get(StudentRecord record) {
			return record.getFirstName();
		}
		
	}

	private static class GetLastName implements IFieldValueGetter {

		@Override
		public String get(StudentRecord record) {
			return record.getLastName();
		}
		
	}
	
	private static class GetJmbag implements IFieldValueGetter {

		@Override
		public String get(StudentRecord record) {
			return record.getJmbag();
		}
		
	}
}
