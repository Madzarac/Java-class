package hr.fer.zemris.java.gui.calc;

import java.util.function.Function;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

/**
 * JUnariButton izveden od klase JButton
 * u konstruktoru dodatno prima dvije operacije (originalnu i inverznu)
 * @author Ideapad 5
 *
 */
public class JUnariOpButton extends JButton {
	
	private Function<Double,Double> operation;
	private Function<Double,Double> operationInv;
	

	public JUnariOpButton(Function<Double,Double> operation, Function<Double,Double> operationInv) {
		super();
		this.operation = operation;
		this.operationInv = operationInv;
		// TODO Auto-generated constructor stub
	}

	public JUnariOpButton(Action a, Function<Double,Double> operation, Function<Double,Double> operationInv) {
		super(a);
		this.operation = operation;
		this.operationInv = operationInv;
		// TODO Auto-generated constructor stub
	}

	public JUnariOpButton(Icon icon, Function<Double,Double> operation, Function<Double,Double> operationInv) {
		super(icon);
		this.operation = operation;
		this.operationInv = operationInv;
		// TODO Auto-generated constructor stub
	}

	public JUnariOpButton(String text, Icon icon, Function<Double,Double> operation, Function<Double,Double> operationInv) {
		super(text, icon);
		this.operation = operation;
		this.operationInv = operationInv;
		// TODO Auto-generated constructor stub
	}

	public Function<Double, Double> getOperation() {
		return operation;
	}
	
	public Function<Double, Double> getOperationInv() {
		return operationInv;
	}

	public JUnariOpButton(String text, Function<Double,Double> operation, Function<Double,Double> operationInv) {
		super(text);
		this.operation = operation;
		this.operationInv = operationInv;
		// TODO Auto-generated constructor stub
	}

	
}
