package hr.fer.oprpp1.custom.collections;

/**
 * 
 * @author TeaMadzarac
 * @version 17/10/2022
 *
 */
public class ObjectStack {
	
	/**
	 * stack
	 */
	private ArrayIndexedCollection stack;

    public ObjectStack() {
        this.stack = new ArrayIndexedCollection();
    }

    /**
     * checks if the stack is empty
     * @return
     */
    public boolean isEmpty() {

        return this.stack.isEmpty();
    }

    /**
     * returns the size of the stack
     * @return
     */
    public int size() {

        return this.stack.size();
    }

    /**
     * adds element on top of the stack
     * @param value
     */
    public void push(Object value) {

        this.stack.add(value);
    }

    /**
     * removes an element from the stack and returns it
     * @return
     */
    public Object pop() {

        if (this.stack.size() == 0) {
            throw new EmptyStackException("Stack is empty!");

        } else {
            Object removedValue = this.stack.get(this.stack.size() -1);
            this.stack.remove(this.stack.size() - 1);
            return removedValue;
        }
    }

    /**
     * returns element that is on top of the stack
     * @return
     */
    public Object peek() {

        if (this.stack.size() == 0) {
            throw new EmptyStackException("The stack is empty!");

        } else {
            return this.stack.get(this.stack.size() -1);
        }
    }

    /**
     * removes all elements from the stack
     */
    public void clear() {
        this.stack.clear();
    }


}
