package hr.fer.zemris.java.gui.calc;

import java.util.function.BiFunction;
import java.util.function.DoubleBinaryOperator;
import java.util.function.Function;

import javax.swing.JButton;

/**
 * gumb za binarne operacije, u konstruktoru prima jos i zeljenu operaciju (u posebnim slucajevima i njenu inverznu operaciju)
 * @author TeaMadzarac
 *
 */
public class JBinOpButton extends JButton{

	private DoubleBinaryOperator operation;
	private DoubleBinaryOperator operationInv;
	private boolean hasInv;
	
	
	public DoubleBinaryOperator getOperation() {
		return operation;
	}

	public DoubleBinaryOperator getOperationInv() {
		return operationInv;
	}

	/**
	 * provjerava ima li operacija inverznu operaciju
	 * @return
	 */
	public boolean isHasInv() {
		return hasInv;
	}


	/**
	 * konstruktor
	 * @param text tekst gumba
	 * @param operation operacija koju izvodi gumb
	 * inverz se postavlja na netocno
	 */
	public JBinOpButton(String text, DoubleBinaryOperator operation) {
		super(text);
		this.operation = operation;
		this.hasInv = false;
	}
	
	/**
	 * konstruktor sa inverznom operacijom
	 * @param text tekst gumba
	 * @param operation operacija koju izvodi gumb
	 * @param operationInv inverzna operacija koju izvodi gumb
	 */
	public JBinOpButton(String text, DoubleBinaryOperator operation, DoubleBinaryOperator operationInv) {
		super(text);
		this.operation = operation;
		this.operationInv = operationInv;
		this.hasInv = true;
	}


	
	
	
	

}
