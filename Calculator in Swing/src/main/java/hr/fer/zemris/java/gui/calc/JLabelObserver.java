package hr.fer.zemris.java.gui.calc;

import javax.swing.Icon;
import javax.swing.JLabel;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;

/**
 * izvedeno iz razreda JLabel, ova labela je ujedno promatrac modela
 * @author TeaMadzarac
 *
 */
public class JLabelObserver extends JLabel {

	private final CalcModel model;
	
	/**
	 * konstruktor
	 * @param model CalcModel
	 */
	public JLabelObserver(CalcModel model) {
		super();
		this.model = model; 
		model.addCalcValueListener(l); 
		updateLabelText();
	}

	public JLabelObserver(Icon image, int horizontalAlignment, CalcModel model) {
		super(image, horizontalAlignment);
		this.model = model; 
		model.addCalcValueListener(l); 
		updateLabelText();
	}

	public JLabelObserver(Icon image, CalcModel model) {
		super(image);
		this.model = model; 
		model.addCalcValueListener(l); 
		updateLabelText();
	}

	public JLabelObserver(String text, Icon icon, int horizontalAlignment, CalcModel model) {
		super(text, icon, horizontalAlignment);
		this.model = model; 
		model.addCalcValueListener(l); 
		updateLabelText();
	}

	public JLabelObserver(String text, int horizontalAlignment, CalcModel model) {
		super(text, horizontalAlignment);
		this.model = model; 
		model.addCalcValueListener(l); 
		updateLabelText();
	}

	public JLabelObserver(String text, CalcModel model) {
		super(text);
		this.model = model; 
		model.addCalcValueListener(l); 
		updateLabelText();
		// TODO Auto-generated constructor stub
	}
	
	private void updateLabelText() { 
		setText(model.toString()); 
	} 

	
	private final CalcValueListener l = new CalcValueListener() {

		@Override
		public void valueChanged(CalcModel model) {
			updateLabelText();
			
		}
		
	};

}
