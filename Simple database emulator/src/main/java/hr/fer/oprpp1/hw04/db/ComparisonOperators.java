package hr.fer.oprpp1.hw04.db;

/**
 * 
 * @author TeaMadzarac
 * @version 11/11/2022
 */
public class ComparisonOperators  {

	
	public static final IComparisonOperator LESS = new Less();
	public static final IComparisonOperator LESS_OR_EQUALS = new LessOrEquals();
	public static final IComparisonOperator GREATER = new Greater();
	public static final IComparisonOperator GREATER_OR_EQUALS = new GreaterOrEquals();
	public static final IComparisonOperator EQUALS = new Equals();
	public static final IComparisonOperator NOT_EQUALS = new NotEquals();
	public static final IComparisonOperator LIKE = new Like();
	
	
	
	private static class Less implements IComparisonOperator {
		
		@Override
		public boolean satisfied(String value1, String value2) {
			if(value1.compareTo(value2) < 0) {
				return true;
			} else {
				return false;				
			}
		}
	}
	
	
	private static class LessOrEquals implements IComparisonOperator {
		
		@Override
		public boolean satisfied(String value1, String value2) {
			if(value1.compareTo(value2) <= 0) {
				return true;
			} else {
				return false;				
			}
		}
	}
	
	
	private static class Greater implements IComparisonOperator {
		
		@Override
		public boolean satisfied(String value1, String value2) {
			if(value1.compareTo(value2) > 0) {
				return true;
			} else {
				return false;				
			}
		}
	}
	
	private static class GreaterOrEquals implements IComparisonOperator {
		
		@Override
		public boolean satisfied(String value1, String value2) {
			if(value1.compareTo(value2) >= 0) {
				return true;
			} else {
				return false;				
			}
		}
	}


	private static class Equals implements IComparisonOperator {
		
		@Override
		public boolean satisfied(String value1, String value2) {
			if(value1.compareTo(value2) == 0) {
				return true;
			} else {
				return false;				
			}
		}
	}
	
	
	private static class NotEquals implements IComparisonOperator {
		
		@Override
		public boolean satisfied(String value1, String value2) {
			if(value1.compareTo(value2) != 0) {
				return true;
			} else {
				return false;				
			}
		}
	}
	
	
	private static class Like implements IComparisonOperator {
		
		@Override
		public boolean satisfied(String value1, String value2) {
			
			if (value2.contains("*")) {
				
				String[] splitCon = value2.split("\\*");
				
				//if user put more than one *
				if(splitCon.length > 2) {
					
					try {
						throw new IllegalArgumentException("You can use only one wildcard *.");
					} catch(IllegalArgumentException e) {
						System.out.print(e.getMessage());
					} 
					return false;	
				}
				
				//* is only character
				else if(value2.length() == 1) {
					return true;
				}
				
				//if condition starts with *
				else if(value2.charAt(0) == '*') {
					if (value1.endsWith(splitCon[0])) {
						return true;
					} else {
						return false;
					}
				}
				
				//if condition ends with *
				else if(value2.charAt(value2.length()-1) == '*') {
					if (value1.startsWith(splitCon[0])) {
						return true;
					} else {
						return false;
					}
				}
				
				//if value2 is empty 
				else if(value2.length() == 0) {
					return false;
				}
				 
				//* is somewhere in the middle
				else {
					if (value1.startsWith(splitCon[0])) {
						value1 = value1.replaceFirst(splitCon[0], "");
						if (value1.endsWith(splitCon[1])) {
							return true;
						} else {
							return false;
						}
					}
					return false;
					
				}
				
			// there is no wildcard sign	
			} else {
				
				if(value1.compareTo(value2) == 0) {
					return true;
				} else {
					return false;				
				}
			}
			
		}
	}
	
}
