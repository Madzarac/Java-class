package hr.fer.oprpp1.hw04.db;

/**
 * functional interface
 * @author TeaMadzarac
 * @version 9/11/2022
 */
public interface IFilter {

	public boolean accepts(StudentRecord record);
}
