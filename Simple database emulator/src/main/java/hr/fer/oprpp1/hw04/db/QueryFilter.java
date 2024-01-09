package hr.fer.oprpp1.hw04.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * 
 * @author TeaMadzarac
 * @version 12/11/2022
 *
 */
public class QueryFilter {
	
	List<ConditionalExpression> list;
	List<StudentRecord> filtered;
	List<String> lines = null;
	StudentDatabase db;

	/**
	 * constructor
	 * @param list list of ConditionalExpression objects
	 */
	public QueryFilter(List<ConditionalExpression> list) {
		this.list = list;
		
		try {
			lines = Files.readAllLines(
					Paths.get("src/test/resources/database.txt"), 
					StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		db = new StudentDatabase(lines);
		select();
	}
	
	
	private void select() {
		
		
		class Filter implements IFilter {

			@Override
			public boolean accepts(StudentRecord record) {
				boolean accept = true;
				
				for(int i = 0; i < list.size(); ++i) {
					String sLiteral = list.get(i).getStringLiteral();
					accept = list.get(i).getComparisonOperator().satisfied(list.get(i).getFieldGetter().get(record), sLiteral);
					if(!accept) {
						return false;
					}
				}
				
				return accept;
			}
		}
		
		Filter f = new Filter();
		filtered = db.filter(f);
		
		drawTable();
	}
	
	

	/**
	 * prints data in table form
	 */
	private void drawTable() {
		int name = 0;
		int lastName = 0;
		int jmbag = 10;
		
		for(int i = 0; i < filtered.size(); i++) {
			name = name > filtered.get(i).getFirstName().length() ? name : filtered.get(i).getFirstName().length();
			lastName = lastName > filtered.get(i).getLastName().length() ? lastName : filtered.get(i).getLastName().length();
		}

		String table = "+============+";
		for(int i = 0; i <= lastName + 1; i++) {
			table += "=";
		}
		table += "+";
		for(int i = 0; i <= name + 1; i++) {
			table += "=";
		}
		table += "+===+";
		
		
		System.out.println(table);
		
		
		for(int i = 0; i < filtered.size(); i++) {
			
			String row = "| ";
			row += filtered.get(i).getJmbag();
			row += " | ";
			
			row += filtered.get(i).getLastName();
			for(int j = 0; j < lastName - filtered.get(i).getLastName().length(); j++) {
				row += " ";
			}
			row += " | ";
			
			row += filtered.get(i).getFirstName();
			for(int j = 0; j < lastName - filtered.get(i).getFirstName().length(); j++) {
				row += " ";
			}
			row += " | ";
			
			row += filtered.get(i).getFinalGrade();
			row += " |";
			
			System.out.println(row);
		}
		
		System.out.println(table);
	}
	
	
	

}
