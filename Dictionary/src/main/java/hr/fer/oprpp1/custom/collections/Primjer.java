package hr.fer.oprpp1.custom.collections;

import java.util.Arrays;
import java.util.Iterator;

import hr.fer.oprpp1.custom.collections.SimpleHashtable.TableEntry;

public class Primjer {
	
		
	public static void main(String[] args) {
		
		
		// create collection:
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana
		for(SimpleHashtable.TableEntry<String,Integer> pair : examMarks) {
			System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
		}

		
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter1 = examMarks.iterator();
		while(iter1.hasNext()) {
			SimpleHashtable.TableEntry<String,Integer> pair = iter1.next();
			if(pair.getKey().equals("Ivana")) {
				iter1.remove(); // sam iterator kontrolirano uklanja trenutni element
			}
		}

		
		for(SimpleHashtable.TableEntry<String,Integer> pair1 : examMarks) {
			for(SimpleHashtable.TableEntry<String,Integer> pair2 : examMarks) {
				System.out.printf(
					"(%s => %d) - (%s => %d)%n", 
					pair1.getKey(), pair1.getValue(),
					pair2.getKey(), pair2.getValue());
			}
		}

		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
		while(iter.hasNext()) {
			SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
			System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
			iter.remove();
		}
		System.out.printf("Velicina: %d%n", examMarks.size());

	}



}
