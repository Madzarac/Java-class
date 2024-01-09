package hr.fer.oprpp1.hw04.db;

import java.util.*;

/**
 * Instances of this class will represent records for each student
 * @author TeaMadzarac
 * @version 9/11/2022
 */
public class StudentDatabase {
	
	private Map<String, StudentRecord> map = new HashMap<>();
	private List<StudentRecord> list = new ArrayList<>();
	
	
	
	public List<StudentRecord> getList() {
		return list;
	}

	/**
	 * constructor
	 * @param lines each string represents one row of database file
	 */
	public StudentDatabase(List<String> lines) {
		
		for (String line: lines) {
			String[] arrOfStr = line.split("\t", 0);
			
			if(map.containsKey(arrOfStr[0])) {
				throw new IllegalArgumentException("Jmbags have to be unique.");
				
			}else if (!(arrOfStr[3].equals("1") ||
					    arrOfStr[3].equals("2") ||
					    arrOfStr[3].equals("3") ||
					    arrOfStr[3].equals("4") ||
					    arrOfStr[3].equals("5"))) {
				throw new IllegalArgumentException("Final grade has to be a number between 1 and 5");
				       
			}
			
			StudentRecord student = new StudentRecord(arrOfStr[0], arrOfStr[1], arrOfStr[2], Integer.parseInt(arrOfStr[3]));
			list.add(student);
			map.put(arrOfStr[0], student);
			
		}	
	}
	
	
	/**
	 * uses index to obtain requested record in O(1)
	 * @param jmbag index
	 * @return requested record or null if there is no such record
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return map.get(jmbag);
	}
	
	
	/**
	 * loops through all student records in its internal list
	 * @param filter filter condition
	 * @return list with all records for which filters method accept returned true
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> temp = new ArrayList<StudentRecord>();
		
		for(StudentRecord s: list) {
			if(filter.accepts(s)) {
				
				temp.add(s);
			}
		}
		
		return temp;
	}

}
