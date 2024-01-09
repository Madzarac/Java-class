package hr.fer.zemris.java.gui.calc.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

/**
 * CalcModelImpl
 * metode koje upravljaju radom kalkulatora
 * @author TeaMadzarac
 *
 */
public class CalcModelImpl implements CalcModel {
	
	private boolean isEditable;
	private boolean isNegative;
	private String stringValue;
	private double value;
	private String freezeValue;
	private double activeOperand;
	private DoubleBinaryOperator pendingOperation;
	private boolean isActiveOperandSet;
	private List<CalcValueListener> listeners = new ArrayList<>();
	

	/**
	 * konstruktor
	 */
	public CalcModelImpl() {
		isEditable = true;
		isNegative = false;
		stringValue = "";
		value = 0;
		activeOperand = 0;
		pendingOperation = null;
		isActiveOperandSet = false;
		freezeValue = null;
	}
	
	@Override
	public void addCalcValueListener(CalcValueListener l) {
		listeners = new ArrayList<>(listeners); 
		listeners.add(l);
		
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		listeners = new ArrayList<>(listeners); 
		listeners.remove(l);
		
	}
	
	@Override
	public String toString() {
		if(freezeValue != null) {
			return freezeValue;
		}
		if(stringValue.isEmpty()) {
				return "0";
		} else if(stringValue.equals("-")) {
			return "-0";
		}else {
			return stringValue;
		}
	}

	@Override
	public double getValue() {
		return this.value;
	}

	@Override
	public void setValue(double value) {
		isEditable = false;
		this.value = value;
		this.freezeValue = null;
		stringValue = String.valueOf(value);
		if(value < 0) {
			isNegative = true;
		} else {
			isNegative = false;
		}
		for(CalcValueListener l : listeners) { 
			 l.valueChanged(this);
		} 
	}

	@Override
	public boolean isEditable() {
		return isEditable;
	}

	@Override
	public void clear() {
		isEditable = true;
		value = 0;
		stringValue = "";
		for(CalcValueListener l : listeners) { 
			 l.valueChanged(this);
		} 
	}

	@Override
	public void clearAll() {
		isEditable = true;
		value = 0;
		stringValue = "";
		isActiveOperandSet = false;
		activeOperand = 0;
		pendingOperation = null;
		freezeValue = null;
		for(CalcValueListener l : listeners) { 
			 l.valueChanged(this);
		} 
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if(!isEditable) {
			throw new CalculatorInputException("Can't swap sign.");
		}
		if(isNegative) {
			isNegative = false;
		} else {
			isNegative = true;
		}
		value = value * -1;
		if(stringValue.startsWith("-")) {
			stringValue = stringValue.replace("-", "");
		} else {
			stringValue = "-" + stringValue;
		}
		freezeValue = null;
		for(CalcValueListener l : listeners) { 
			 l.valueChanged(this);
		} 
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if(!isEditable) {
			throw new CalculatorInputException("Can't insert decimal point because calculator is not editable.");
		}
		if(stringValue.isEmpty() || stringValue.equals("-")) {
			throw new CalculatorInputException("Can't insert decimal point because decimal point must follow a number.");
		}
		if(stringValue.contains(".")) {
			throw new CalculatorInputException("Can't insert decimal point because number already has decimal point.");
		}
		stringValue = stringValue + ".";
		freezeValue = null;
		for(CalcValueListener l : listeners) { 
			 l.valueChanged(this);
		} 
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		
		if(!isEditable) {
			throw new CalculatorInputException("Can't insert digit because calculator is not editable.");
		}
		if(digit < 0 || digit > 9) {
			throw new IllegalArgumentException("Unvalid value for digit.");
		}
		BigDecimal maxValue = new BigDecimal(Double.MAX_VALUE);
		BigDecimal addedDigit = new BigDecimal(stringValue + digit);
		if(addedDigit.compareTo(maxValue) > 0) {
			throw new CalculatorInputException("Number became too big.");
		}
		this.freezeValue = null;
		if(!(stringValue.equals("0") && digit == 0)) {
			if(stringValue.equals("0")) {
				stringValue = "" + digit;
			} else {
				stringValue = stringValue + digit;
			}
			value = Double.parseDouble(stringValue);		
		}
		freezeValue = null;
		for(CalcValueListener l : listeners) { 
			 l.valueChanged(this);
		} 
	}

	@Override
	public boolean isActiveOperandSet() {
		return isActiveOperandSet;
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if(!isActiveOperandSet) {
			throw new IllegalStateException("Active operand is not set!");
		}
		return activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
		this.isActiveOperandSet = true;
		this.freezeValue = String.valueOf(activeOperand);
		for(CalcValueListener l : listeners) { 
			 l.valueChanged(this);
		} 
	}

	@Override
	public void clearActiveOperand() {
		this.isActiveOperandSet = false;
		this.activeOperand = 0;
		
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return this.pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		this.pendingOperation = op;
	}
	
	public void freezeValue(String value) {
		this.freezeValue = value;
	}
	
	
	public boolean hasFrozenValue() {
		if(freezeValue == null) {
			return false;
		}
		return true;
	}

}
