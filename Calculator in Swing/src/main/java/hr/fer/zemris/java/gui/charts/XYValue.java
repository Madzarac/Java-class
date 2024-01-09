package hr.fer.zemris.java.gui.charts;

/**
 * XYValue read-only
 * @author TeaMadzarac
 *
 */
public class XYValue {

	private int x;
	private int y;
	
	/**
	 * konstruktor
	 * @param x x vrijednost
	 * @param y y vrijednost
	 */
	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * getter
	 * @return x value
	 */
	public int getX() {
		return x;
	}

	/**
	 * getter
	 * @return y value
	 */
	public int getY() {
		return y;
	}
	
}
