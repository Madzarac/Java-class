package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.model.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Calculator
 * @author TeaMadzarac
 *
 */
public class Calculator extends JFrame{
	
	private ArrayList<String> stack;

	/**
	 * konstruktor
	 */
	public Calculator() {
		setLocation(20, 50);
		setSize(500, 500);
		setTitle("Calculator");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		stack = new ArrayList<>();
		
		initGUI();
	}

	
	/**
	 * postavlja sve dijelove kalkulatora
	 */
	private void initGUI() {
		
		CalcModelImpl model = new CalcModelImpl();
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(3));
		
		JLabelObserver l = new JLabelObserver(model.toString(), SwingConstants.RIGHT, model);
		l.setBackground(Color.YELLOW);
		l.setOpaque(true);
		l.setFont(l.getFont().deriveFont(30f));
		cp.add(l, "1,1");
		
		JCheckBox inv = new JCheckBox("inv");
		cp.add(inv, "5,7");
		
		ActionListener digitPressed = a -> {
			JButton b = (JButton)a.getSource();
			model.insertDigit(Integer.parseInt(b.getText()));
		};
		
		ActionListener unarOpPressed = a -> {
			if(model.hasFrozenValue()) {
				throw new CalculatorInputException("Invalid input, can't insert operation.");
			}
			JUnariOpButton b = (JUnariOpButton)a.getSource();
			if(inv.isSelected()) {
				model.setValue(b.getOperationInv().apply(model.getValue()));
			} else {
				model.setValue(b.getOperation().apply(model.getValue()));
			}
		};
		
		ActionListener binOpPressed = a -> {
			if(model.hasFrozenValue()) {
				throw new CalculatorInputException("Invalid input, can't insert operation.");
			}
			JBinOpButton b = (JBinOpButton)a.getSource();
			if(model.getPendingBinaryOperation() != null) {
				model.setActiveOperand(model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(), model.getValue()));
				model.clear();
			} else {
				model.setActiveOperand(model.getValue());
				model.clear();
			}
			if(inv.isSelected()) {
				if(b.isHasInv()) {
					model.setPendingBinaryOperation(b.getOperationInv());
				} else {
					model.setPendingBinaryOperation(b.getOperation());				}
			} else {
				model.setPendingBinaryOperation(b.getOperation());
			}
		};
		
		ActionListener equalPressed = a -> {
			double result = model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(), model.getValue());
			model.setValue(result);
			model.clearActiveOperand();
			model.setPendingBinaryOperation(null);
		};
		
		ActionListener clrPressed = a -> {
			model.clear();
		};
		
		ActionListener resPressed = a -> {
			model.clearAll();
		};
		
		ActionListener signSwapPressed = a -> {
			model.swapSign();
		};
		
		ActionListener pushPressed = a -> {
			stack.add(model.toString());
		};
		
		ActionListener popPressed = a -> {
			if(stack.isEmpty()) {
				System.out.println("Stack is Empty.");
			} else {
				model.setValue(Double.parseDouble(stack.get(stack.size()-1)));
				stack.remove(stack.size()-1);
			}
		};
		
		ActionListener decPointPressed = a -> {
			model.insertDecimalPoint();
		};
		
		JButton[] digitButtons = new JButton[10];
		String[] Pos = {"5,3", "4,3", "4,4", "4,5", "3,3", "3,4", "3,5", "2,3", "2,4", "2,5"};
		for(int i = 0; i < 10; i++) {
			digitButtons[i] = new JButton(Integer.toString(i));
			digitButtons[i].setBackground(Color.gray);
			digitButtons[i].setOpaque(true);
			digitButtons[i].addActionListener(digitPressed);
			digitButtons[i].setFont(digitButtons[i].getFont().deriveFont(30f));
			cp.add(digitButtons[i],Pos[i]);
		}
		
		ArrayList<JUnariOpButton> unarOpButtons = new ArrayList<JUnariOpButton>();
		JUnariOpButton sin = new JUnariOpButton("sin", Math::sin, Math::asin);
		unarOpButtons.add(sin);
		JUnariOpButton cos = new JUnariOpButton("cos", Math::cos, Math::acos);
		unarOpButtons.add(cos);
		JUnariOpButton tan = new JUnariOpButton("tan", Math::tan, Math::atan);
		unarOpButtons.add(tan);
		JUnariOpButton ctg = new JUnariOpButton("ctg", a -> {
			return Math.cos(a)/Math.sin(a);
		}, b -> {
			return 1 / (Math.cos(b)/Math.sin(b));
		});
		unarOpButtons.add(ctg);
		JUnariOpButton log = new JUnariOpButton("log", Math::log10, a -> {
			return Math.pow(10, a);
		});
		unarOpButtons.add(log);
		JUnariOpButton ln = new JUnariOpButton("ln", Math::log, a -> {
			return Math.pow(Math.E, a);
		});
		unarOpButtons.add(ln);
		JUnariOpButton reciprocate = new JUnariOpButton("1/x", a -> {
			return 1 / a;
		}, a -> a);
		unarOpButtons.add(reciprocate);
	
		String[] Pos2 = {"2,2", "3,2", "4,2", "5,2", "3,1", "4,1", "2,1"};
		for(int i = 0; i < unarOpButtons.size(); i++) {
			unarOpButtons.get(i).setBackground(Color.gray);
			unarOpButtons.get(i).setOpaque(true);
			unarOpButtons.get(i).addActionListener(unarOpPressed);
			cp.add(unarOpButtons.get(i),Pos2[i]);
		}

		ArrayList<JBinOpButton> binOpButtons = new ArrayList<JBinOpButton>();
		JBinOpButton plus = new JBinOpButton("+", Double::sum);
		binOpButtons.add(plus);
		JBinOpButton minus = new JBinOpButton("-", (a, b) -> a - b);
		binOpButtons.add(minus);
		JBinOpButton multiply = new JBinOpButton("*", (a, b) -> a * b);
		binOpButtons.add(multiply);
		JBinOpButton divide = new JBinOpButton("/", (a, b) -> a / b);
		binOpButtons.add(divide);
		JBinOpButton power = new JBinOpButton("x^n", (a, b) -> Math.pow(a, b), (a,b) -> Math.pow(a,1/b));
		binOpButtons.add(power);
		
		String[] Pos3 = {"5,6", "4,6", "3,6", "2,6", "5,1"};
		for(int i = 0; i < binOpButtons.size(); i++) {
			binOpButtons.get(i).setBackground(Color.gray);
			binOpButtons.get(i).setOpaque(true);
			binOpButtons.get(i).addActionListener(binOpPressed);
			cp.add(binOpButtons.get(i),Pos3[i]);
		}
		
		JButton equal = new JButton("=");
		equal.setBackground(Color.gray);
		equal.setOpaque(true);
		equal.addActionListener(equalPressed);
		cp.add(equal, "1,6");
		
		JButton clr = new JButton("clr");
		clr.setBackground(Color.gray);
		clr.setOpaque(true);
		clr.addActionListener(clrPressed);
		cp.add(clr, "1,7");
		
		JButton res = new JButton("res");
		res.setBackground(Color.gray);
		res.setOpaque(true);
		res.addActionListener(resPressed);
		cp.add(res, "2,7");
		
		JButton signSwap = new JButton("+/-");
		signSwap.setBackground(Color.gray);
		signSwap.setOpaque(true);
		signSwap.addActionListener(signSwapPressed);
		cp.add(signSwap, "5,4");
		
		JButton push = new JButton("push");
		push.setBackground(Color.gray);
		push.setOpaque(true);
		push.addActionListener(pushPressed);
		cp.add(push, "3,7");
		
		JButton pop = new JButton("pop");
		pop.setBackground(Color.gray);
		pop.setOpaque(true);
		pop.addActionListener(popPressed);
		cp.add(pop, "4,7");
		
		JButton decPoint = new JButton(".");
		decPoint.setBackground(Color.gray);
		decPoint.setOpaque(true);
		decPoint.addActionListener(decPointPressed);
		cp.add(decPoint, "5,5");	
		
	}

	
	public static void main(String[] args) {

		SwingUtilities.invokeLater(() -> {
			JFrame frame = new Calculator();
			frame.pack();
			frame.setVisible(true);
		});
	}
}
