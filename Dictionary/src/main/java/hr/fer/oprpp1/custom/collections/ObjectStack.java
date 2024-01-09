package hr.fer.oprpp1.custom.collections;

/**
 * stack-like collection, adaptor of the ArrayIndexedCollection class
 * @author TeaMadzarac
 * @version 30/10/2022
 *
 */
public class ObjectStack<E> {
	
	/**
	 * stack
	 */
	private ArrayIndexedCollection<E> stack;

	/**
	 * constructs stack as ArrayIndexedCollection
	 */
    public ObjectStack() {
        this.stack = new ArrayIndexedCollection<E>();
    	
    }

    /**
     * checks if the stack is empty
     * @return true if the stack is empty, false otherwise
     */
    public boolean isEmpty() {

        return this.stack.isEmpty();
    }

    /**
     * returns the size of the stack
     * @return size of the stack
     */
    public int size() {

        return this.stack.size();
    }

    /**
     * adds element on top of the stack
     * @param value to be added on top of the stack
     */
    public void push(E value) {

        this.stack.add(value);
    }

    /**
     * removes an element from the stack and returns it
     * @return element removed from stack
     */
    public E pop() {

        if (this.stack.size() == 0) {
            throw new EmptyStackException("Stack is empty!");

        } else {
            E removedValue = this.stack.get(this.stack.size() -1);
            this.stack.remove(this.stack.size() - 1);
            return removedValue;
        }
    }

    /**
     * returns element that is on top of the stack
     * @return value of the element on top of the stack
     */
    public E peek() {

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
