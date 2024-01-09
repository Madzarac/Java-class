package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * CalcLayout manager
 * @author TeaMadzarac
 *
 */
public class CalcLayout implements LayoutManager2 {
	
	private final int nRow = 5;
	private final int nCol = 7;
	private Map<Component, RCPosition> components;
	private int desiredSpace;
	
	/**
	 * konstruktor
	 */
	public CalcLayout() {
		components = new HashMap<>();
		desiredSpace = 0;
	}
	
	/**
	 * konstruktor
	 * @param space razmak izmedu tipki
	 */
	public CalcLayout(int space) {
		components = new HashMap<>();
		desiredSpace = space;
	}
	
	/**
	 * creates RCPosition component based on boundaries from string
	 * @param text "row,column"
	 * @return RCPosition
	 */
	public RCPosition parse(String text) {
		String[] splitted = text.split(",");
		if(splitted.length != 2) {
			throw new IllegalArgumentException("Invalid string input.");
		}
		int row = Integer.parseInt(splitted[0]);
		int column = Integer.parseInt(splitted[1]);
		return new RCPosition(row, column);
		
	}


	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException();
	}

	/**
	 * ukljanja komponentu
	 */
	public void removeLayoutComponent(Component comp) {
		components.remove(comp);
		
	}

	/**
	 * racuna preferiranu velicinu (najveca preferirana velicina komponente)
	 */
	public Dimension preferredLayoutSize(Container parent) {
		return findSize(parent, comp -> comp.getPreferredSize());
	}

	/**
	 * racuna minimalnu velicinu (najveca minimalna velicina komponente)
	 */
	public Dimension minimumLayoutSize(Container parent) {
		return findSize(parent, comp -> comp.getPreferredSize());
	}

	/**
	 * odreduje polozaj komponenti i dodaje ih u layout
	 */
	public void layoutContainer(Container parent) {

		Insets insets = parent.getInsets();
		double height = parent.getHeight() - (insets.top + insets.bottom);
		double width = parent.getWidth() - (insets.left + insets.right);
		double sumOfSpacesInRow = desiredSpace * 6.;
		double sumOfSpacesInCol = desiredSpace * 4.;
		double heightOfOneComp = height - sumOfSpacesInCol;
		double widthOfOneComp = width - sumOfSpacesInRow;
		
		heightOfOneComp = heightOfOneComp / nRow;
		widthOfOneComp = widthOfOneComp / nCol;
		
		for(int i = 0; i < parent.getComponentCount(); i++) {
			
			Component c = parent.getComponent(i);

			 int overHeight = (int)((heightOfOneComp - (int)heightOfOneComp) * nRow);
			int overWidth = (int)((widthOfOneComp - (int)widthOfOneComp) * nCol);
			
			if(components.containsKey(c)) {
				double x = insets.left;
				double y = insets.top;
				int addH = 0;
				int addW = 0;
				int addWfirst = 0;
				if(!(components.get(c).getColumn() == 1 && components.get(c).getRow() == 1)) {
					if(overHeight > 2) {
						if(components.get(c).getRow() % 2 == 0) {
							addH = 1;
						}
					}
					if(overWidth > 3) {
						if(components.get(c).getColumn() % 2 == 0) {
							addW = 1;
							addWfirst = 2;
						}
					}
					x = x + widthOfOneComp * (components.get(c).getColumn() -1) + desiredSpace  * (components.get(c).getColumn() -1);
					y = y + heightOfOneComp * (components.get(c).getRow()-1) + desiredSpace *(components.get(c).getRow()-1);
					c.setBounds((int)x, (int)y, (int)widthOfOneComp + addW, (int)heightOfOneComp + addH);
				} else {
					c.setBounds((int)x, (int)y, (int)widthOfOneComp * 5 + desiredSpace*4 + addWfirst, (int)heightOfOneComp + addH);
				}
			} else {
				System.out.println("Greska, komponenta nije u unutarnjoj listi");
			}
		}
	}

	public void addLayoutComponent(Component comp, Object constraints) {
		
		if(components.size() == 31) {
			throw new CalcLayoutException("Layout is already full.");
		}
		
		if(comp == null) {
			throw new NullPointerException("Component can't be null.");
		}
		if(constraints == null) {
			throw new NullPointerException("Constraints can't be null.");
		}
		
		if(!(constraints instanceof String || constraints instanceof RCPosition)) {
			throw new IllegalArgumentException();	
		} 
		
		if(constraints instanceof String) {
			RCPosition constraint = parse((String)constraints);
			if(components.containsValue(constraint)) {
				throw new CalcLayoutException("Component with set constraints already exists.");
			} else {
				components.put(comp, constraint);
			}
		}
		
		if(constraints instanceof RCPosition) {
			if(components.containsValue(constraints)) {
				throw new CalcLayoutException("Component with set constraints already exists.");
			} else {
				components.put(comp, (RCPosition)constraints);
			}
		}
		
		
	}

	/**
	 * odreduje polozaj komponenti i dodaje ih u layout
	 */
	public Dimension maximumLayoutSize(Container target) {
		 return findSize(target, comp -> comp.getMaximumSize());
	}

	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	public void invalidateLayout(Container target) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * determines desired size
	 * @param container container
	 * @param method min/max/preffered size
	 * @return wanted size
	 */
	public Dimension findSize(Container container, Function<Component, Dimension> method) {
		
		int height = (int)components.keySet().stream().filter(c -> c != null).filter(c -> method.apply(c).height != 0).mapToInt(c -> method.apply(c).height).max().getAsInt();
		Component firstComp = components.entrySet().stream()
                .filter(entry -> entry.getValue().getColumn() == 1 && entry.getValue().getRow() == 1)
                .findFirst().map(Map.Entry::getKey)
                .orElse(null);
		int width = 0;
		int widthFirst = 0;
		if(components.containsKey(firstComp)) {
			widthFirst = method.apply(firstComp).width - desiredSpace * 4;
			widthFirst = widthFirst / 5;
		} 
		width = (int)components.keySet().stream().filter(c -> !c.equals(firstComp)).filter(c -> c != null).filter(c -> method.apply(c).width != 0).mapToInt(c -> method.apply(c).width).max().getAsInt();
		width = Math.max(width, widthFirst);
		return new Dimension(width * 7 + desiredSpace * 6, height * 5 + desiredSpace * 4);

	}

}
