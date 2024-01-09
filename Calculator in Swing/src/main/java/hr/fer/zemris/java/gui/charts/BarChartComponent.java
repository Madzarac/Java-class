package hr.fer.zemris.java.gui.charts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

/**
 * 
 * @author TeaMadzarac
 *
 */
public class BarChartComponent extends JComponent {
	
	private BarChart chart;

	/**
	 * konstruktor
	 * @param chart BarChart
	 */
	public BarChartComponent(BarChart chart) {
		super();
		this.chart = chart;
	}
	
	/**
	 * crtanje grafa
	 */
	@Override
	protected void  paintComponent(Graphics g) {
		Dimension size = getSize();
		Insets ins = getInsets();
		int width = size.width - ins.left - ins.right;
		int height = size.height - ins.top - ins.bottom;
		
		int leftOfY;
		int downOfX;
		
		Graphics2D g2d = (Graphics2D) g;
		
		//names of axis
		Font font = new Font("Serif", Font.BOLD, 12);
		g2d.setFont(font);
		g2d.rotate(-Math.PI /2);
		g2d.drawString(chart.getDescriptionY(), -height /2 - getFontMetrics(font).stringWidth(chart.getDescriptionY())/2, 12);
		g2d.rotate(Math.PI/2);
		g2d.drawString(chart.getDescriptionX(), width/2 - getFontMetrics(font).stringWidth(chart.getDescriptionX())/2, height-10);
		
		//needed calculations
		//yValues
		List<Integer> yValues = new ArrayList<>();
		for(int i = chart.getMinY(); i <= chart.getMaxY(); i = i + chart.getDistanceBetweenY()) {
			yValues.add(i);
		}
		
		//downOfX
		downOfX =  height - getFontMetrics(font).getHeight() * 2 -10 -15 -10;
		leftOfY = getFontMetrics(font).getHeight() + getFontMetrics(font).stringWidth(String.valueOf(yValues.get(yValues.size()-1))) +35;
		
		//lines as y values
		int ySpaces = height - 2 *(height - downOfX);
		ySpaces /= yValues.size();
		int yHeight = ySpaces * (yValues.size() -1);
		for(int i = 0; i < yValues.size(); i++) {
			Line2D.Double l = new Line2D.Double(leftOfY, downOfX - i * ySpaces, width - leftOfY - 2, downOfX - i * ySpaces);
			g2d.setColor(Color.GRAY);
			g2d.draw(l);
			Line2D.Double numL = new Line2D.Double(leftOfY - 5, downOfX - i * ySpaces, leftOfY, downOfX - i * ySpaces);
			g2d.setColor(Color.BLACK);
			g2d.draw(numL);
		}
		
		//values at y axis
		for(int i = 0; i < yValues.size(); i++) {
			String el = String.valueOf(yValues.get(i));
			g2d.drawString(el, leftOfY - 10 - getFontMetrics(font).stringWidth(String.valueOf(el)), downOfX - i * ySpaces + getFontMetrics(font).getHeight() / 3);
		}
		
		//graph
		int xSpaces = width - 2 * leftOfY;
		xSpaces /= chart.getList().size();
		for(int i = 0; i < chart.getList().size(); i++) {
			XYValue el = chart.getList().get(i);
			//Rectangle2D.Double r = new Rectangle2D.Double(leftOfY + i * xSpaces, downOfX - el.getY() / chart.getDistanceBetweenY()  * ySpaces, xSpaces, el.getY() / chart.getDistanceBetweenY() * ySpaces);
			g2d.setColor(new Color(253, 146,87));
			Rectangle2D.Double r = new Rectangle2D.Double(leftOfY + i * xSpaces, downOfX - yHeight * (el.getY()/(double)chart.getMaxY()), xSpaces, yHeight * (el.getY()/(double)chart.getMaxY()));
			g2d.fill(r);
		}

		//lines as x values
		for(int i = 0; i <= chart.getList().size(); i++) {
			Line2D.Double l = new Line2D.Double(leftOfY + i * xSpaces, height - (height - downOfX), leftOfY + i * xSpaces, height - downOfX + 5);   
			g2d.setColor(Color.GRAY);
			g2d.draw(l);
			Line2D.Double numL = new Line2D.Double(leftOfY + i * xSpaces, downOfX, leftOfY + i * xSpaces, downOfX + 5);      
			g2d.setColor(Color.BLACK);
			g2d.draw(numL);
		}
		
		//values at x axis
		for(int i = 0; i < chart.getList().size(); i++) {
			String el = String.valueOf(chart.getList().get(i).getX());
			g2d.drawString(el, xSpaces * i + xSpaces / 2  + leftOfY - getFontMetrics(font).stringWidth(String.valueOf(el)) / 2, downOfX + 5 + getFontMetrics(font).getHeight());
		}
		
		//for axis of graph
		Line2D.Double x = new Line2D.Double(leftOfY, downOfX, width - leftOfY + 10, downOfX);
		Line2D.Double y = new Line2D.Double(leftOfY, height - downOfX, leftOfY, downOfX);
		g2d.setColor(Color.BLACK);
		Stroke stroke = new BasicStroke(2f);
		g2d.setStroke(stroke);
		g2d.draw(x);
		g2d.draw(y);
		Stroke stroke2 = new BasicStroke(1f);
		g2d.setStroke(stroke2);
		
		//arrows on axis
		//x
		Path2D.Double arrowX = new Path2D.Double();
		arrowX.moveTo(width - leftOfY + 12, downOfX);
		arrowX.lineTo(width - leftOfY + 8, downOfX + 4);
		arrowX.lineTo(width - leftOfY + 8, downOfX - 4);
		arrowX.closePath();
		g2d.fill(arrowX);
		//y
		Path2D.Double arrowY = new Path2D.Double();
		arrowY.moveTo(leftOfY, height - downOfX - 2);
		arrowY.lineTo(leftOfY - 4, height - downOfX + 3);
		arrowY.lineTo(leftOfY + 4, height - downOfX + 3);
		arrowY.closePath();
		g2d.fill(arrowY);
		
		
		
	}

}
