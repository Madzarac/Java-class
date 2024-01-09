package hr.fer.oprpp1.hw04.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		boolean terminate = false;
		
		
		do {
			System.out.print(">");
			Scanner sc = new Scanner(System.in);
			String comName = sc.next();
			
			if(comName.equals("exit")) {
				System.out.println("Goodbye!");
				terminate = true;
				
			} else if(comName.equals("query")){
				String line = sc.nextLine();
				line.trim();
				
				QueryParser qp = new QueryParser(line);
				if(qp.isDirectQuery()) {
					String jmb = qp.getQueriedJMBAG();
					List<String> lines = null;
					try {
						lines = Files.readAllLines(
								Paths.get("src/test/resources/database.txt"), 
								StandardCharsets.UTF_8);
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					StudentDatabase db = new StudentDatabase(lines);
					StudentRecord student = db.forJMBAG(jmb);
					System.out.println("Using index for record retrieval.");
					
					String table = "+============+";
					for(int i = 0; i <= student.getLastName().length() + 1; i++) {
						table += "=";
					}
					table += "+";
					for(int i = 0; i <= student.getFirstName().length() + 1; i++) {
						table += "=";
					}
					table += "+===+";
					
					
					System.out.println(table);
					System.out.println("| " + student.getJmbag() + " | " + student.getLastName() + " | " + student.getFirstName() + " | " + student.getFinalGrade() + " |");
					System.out.println(table);
					
				} else {
					QueryFilter qf = new QueryFilter(qp.getQuery());
				}
				
				
				
			} else {
				System.out.println("query is the only acceptable command.");
			}
			
		} while (!terminate);
		

	}

}
