package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * 
 * @author TeaMadzarac
 *
 */
public class BarChart {

	private List<XYValue> list;
	private String descriptionX;
	private String descriptionY;
	private int minY;
	private int maxY;
	private int distanceBetweenY;
	
	/**
	 * konstruktor
	 * @param list lista y vrijednosti za zadane x nazive
	 * @param descriptionX opis x osi
	 * @param descriptionY opis y osi
	 * @param minY minimalna vrijednost y
	 * @param maxY maksimalna vrijednost od y
	 * @param distanceBetweenY udaljenost izmedu 2 y
	 */
	public BarChart(List<XYValue> list, String descriptionX, String descriptionY, int minY, int maxY,
			int distanceBetweenY) {
		if(minY < 0) {
			throw new IllegalArgumentException("Minium y value must be greater than 0.");
		}
		if(maxY <= minY) {
			throw new IllegalArgumentException("Maximum y value must be greater than minimum y value.");
		}
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).getY() < minY) {
				throw new IllegalArgumentException("Value of y can't be smaller than minimum y value.");
			}
		}
		if((maxY - minY) % distanceBetweenY != 0) {
			distanceBetweenY = minY + 1;
		}
		this.list = list;
		this.descriptionX = descriptionX;
		this.descriptionY = descriptionY;
		this.minY = minY;
		this.maxY = maxY;
		this.distanceBetweenY = distanceBetweenY;
	}

	public List<XYValue> getList() {
		return list;
	}

	public String getDescriptionX() {
		return descriptionX;
	}

	public String getDescriptionY() {
		return descriptionY;
	}

	public int getMinY() {
		return minY;
	}

	public int getMaxY() {
		return maxY;
	}

	public int getDistanceBetweenY() {
		return distanceBetweenY;
	}
	
	
	
	
}
