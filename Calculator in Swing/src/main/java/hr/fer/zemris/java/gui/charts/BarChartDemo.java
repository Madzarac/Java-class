package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.DemoFrame1;

/**
 * 
 * @author TeaMadzarac
 *
 */
public class BarChartDemo extends JFrame{
	
	 private static String file;
	 private BarChart chart;
	
	 /**
	  * konstruktor
	  * @param chart BarChart primjerak
	  */
	public BarChartDemo(BarChart chart) {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(500, 500);
		this.chart = chart;
		initGUI();
		//pack();
	}

	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		JLabel path = new JLabel(file, SwingConstants.CENTER);
		cp.add(path, BorderLayout.NORTH);
		
		BarChartComponent component = new BarChartComponent(chart);
		cp.add(component, BorderLayout.CENTER);
		
		
		
	}
	
	public static void main(String[] args) throws IOException {
		
		file = args[0];   
		BufferedReader reader = new BufferedReader(new FileReader(file));
		
	    String descX = reader.readLine();
	    String descY = reader.readLine();
	    String values = reader.readLine();
	    int minY = Integer.parseInt(reader.readLine());
	    int maxY = Integer.parseInt(reader.readLine());
	    int distance = Integer.parseInt(reader.readLine());
	    
	    String[] splited = values.split("\\s+");
	    List<XYValue> list = new ArrayList<>();
	    for(String s: splited) {
	    	String[] xy = s.split(",");
	    	int x = Integer.parseInt(xy[0]);
	    	int y = Integer.parseInt(xy[1]);
	    	XYValue newVal = new XYValue(x,y);
	    	list.add(newVal);
	    }
	    
	    BarChart makeChart = new BarChart(list, descX, descY, minY, maxY, distance);
		SwingUtilities.invokeLater(()->{
		new BarChartDemo(makeChart).setVisible(true);
		});
	}
}
