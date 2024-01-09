package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ObjectStack;

/**
 * 
 * @author TeaMadzarac
 * @version 17/10/2022
 *
 */
public class StackDemo {
	
	/**
	 * main method that accepts arguments from command line
	 * @param args
	 */
	public static void main(String[] args) {

        String expression;
        expression = args[0];

        expression.trim();
        expression = expression.replaceAll("\\s", " ");
        String[] operation = expression.split(" ");
        ObjectStack stack = new ObjectStack();

        for(int i = 0; i < operation.length; i++) {
            char[] op = operation[i].toCharArray();
            if(Character.isDigit(op[op.length - 1])) {
                int num = op[op.length - 1] - 48;
                if(op.length == 2) {
                    num = num * (-1);
                }
                stack.push(num);

            } else if (op[0] == '+' || op[0] == '-' ||
                    op[0] == '/' || op[0] == '*' || op[0] == '%'){

                int first, second;
                second = (Integer)stack.pop();
                first = (Integer)stack.pop();
                if(second == 0 && op[0] == '/') {
                    System.out.println("You can't divide by zero!");
                    break;
                }
                int result = calculate(first,op[0], second);
                stack.push(result);

            } else {
                System.out.println("Expression is invalid, use of an invalid symbol > " + operation[i]);
                break;
            }
        }

        if(stack.size() != 1) {
        	
        	System.err.println("Error, expression can't be evalueted!");

        } else {
            System.out.println("Expression evaluates to " + stack.pop() + ".");
        }
    }

/**
 * calculates the operation
 * @param first
 * @param operator
 * @param second
 * @return
 */
    private static int calculate(int first, char operator, int second){

        if(operator == '+') {
            return first + second;

        } else if(operator == '-') {
            return first - second;

        } else if(operator == '/') {
            return first / second;

        } else if(operator == '*') {
            return first * second;

        } else { // %
            return first % second;
        }

    }

}
