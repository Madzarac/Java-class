package hr.fer.zemris.java.gui.layouts;

/**
 * Elements
 * @author TeaMadzarac
 *
 */
public class RCPosition {
	
	private int row;
	private int column;
	
	/**
	 * getter
	 * @return row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * getter
	 * @return column
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * Constructor
	 * @param row row
	 * @param column column
	 */
	public RCPosition(int row, int column) {
		checkInput(row, column);
		this.row = row;
		this.column = column;
	}

	private void checkInput(int row, int column) {
		
		if(row < 1 || row > 5) {
			throw new CalcLayoutException("Invalid value for row.");
			
		}else if(column < 1 || column > 7) {
			throw new CalcLayoutException("Invalid value for column.");
			
		} else if((row == 1) && (column < 6 && column > 1)) {
			throw new CalcLayoutException("Invalid positioon.");
		}
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof RCPosition))
			return false;
		RCPosition other = (RCPosition) obj;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}
	


}
