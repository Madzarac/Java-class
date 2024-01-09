package hr.fer.oprpp1.hw04.db;


/**
 * 
 * @author TeaMadzarac
 * @version 9/11/2022
 */
public class StudentRecord {

	private String jmbag;
	private String lastName;
	private String firstName;
	private int finalGrade;
	
	/*
	 * constructor
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}

	/**
	 * get jmbag
	 * @return jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}


	/**
	 * get last name
	 * @return last name
	 */
	public String getLastName() {
		return lastName;
	}


	/**
	 * get first name
	 * @return first name
	 */
	public String getFirstName() {
		return firstName;
	}


	/**
	 * get final grade
	 * @return final grade
	 */
	public int getFinalGrade() {
		return finalGrade;
	}




	/**
	 * hashCode using jmbag
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}


	/**
	 * equals based on comparing jmbag
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}
	
	
	
}
