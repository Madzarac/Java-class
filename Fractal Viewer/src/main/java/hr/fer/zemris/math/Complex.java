package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author TeaMadzarac
 * @version 29/12/2022
 */
public class Complex {
	
	private double re;
	private double im;

	public static final Complex ZERO = new Complex(0,0);
	public static final Complex ONE = new Complex(1,0);
	public static final Complex ONE_NEG = new Complex(-1,0);
	public static final Complex IM = new Complex(0,1);
	public static final Complex IM_NEG = new Complex(0,-1);
	
	/**
	 * constructor
	 */
	public Complex() {
		this.im = 0;
		this.re = 0;
	}
	
	/**
	 * constructor
	 * @param re real part
	 * @param im imaginary part
	 */
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}
	
	/**
	 * Getter
	 * @return value of real part
	 */
	public double getRe() {
		return re;
	}

	/**
	 * Getter
	 * @return value of imaginary part
	 */
	public double getIm() {
		return im;
	}
	
	/**
	 * calculates module
	 * @return module of complex number
	 */
	public double module() {
		return Math.sqrt((re * re) + (im * im));
	}
	
	/**
	 * multiplication of complex numbers
	 * @param c complex number
	 * @return this*c
	 */
	public Complex multiply(Complex c) {
		double newRe = this.re * c.re - (this.im * c.im);
		double newIm = this.im * c.re + this.re * c.im;
		
		return new Complex(newRe, newIm);
	}
	
	/**
	 * division of complex numbers
	 * @param c complex number
	 * @return this/c
	 */
	public Complex divide(Complex c) {
		if(c.equals(ZERO)) {
			throw new IllegalArgumentException("You can't divide with 0.");
		}
		double newRe = (this.re * c.re + this.im * c.im) / (c.re * c.re + c.im * c.im);
		double newIm = (this.im * c.re - this.re * c.im) / (c.re * c.re + c.im * c.im);
		
		return new Complex(newRe, newIm);
	}
	
	/**
	 * adding complex numbers
	 * @param c complex number
	 * @return this + c
	 */
	public Complex add(Complex c) {
		double newRe = this.re + c.re;
		double newIm = this.im + c.im;
		
		return new Complex(newRe, newIm);
	}
	
	/**
	 * subtraction of complex numbers
	 * @param c complex number
	 * @return this-c
	 */
	public Complex sub(Complex c) {
		double newRe = this.re - c.re;
		double newIm = this.im - c.im;
		
		return new Complex(newRe, newIm);
	}
	
	/**
	 * negating complex numbers
	 * @return -this
	 */
	public Complex negate() {
		double newRe, newIm;
		if(this.re != 0) {
			newRe = -this.re;
		} else {
			newRe = this.re;
		}
		if(this.im != 0) {
			newIm = -this.im;
		} else {
			newIm = this.im;
		}

		return new Complex(newRe, newIm);
	}
	
	/**
	 * power of complex number
	 * @param n wanted power
	 * @return this^n
	 */
	public Complex power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Exponent can't be 0.");
		} else if (n == 0){
			return ONE;
		} else if(n ==1) {
			return this;
		}
		Complex res = this;
		for(int i = 0; i < n -1; i++) {
			res = res.multiply(this);
		}
		return res;
	}
	
	/**
	 * calculates n-th root of this, n is positive integer
	 * @param n wanted root
	 * @return n-th root of this
	 */
	public List<Complex> root(int n) {
		double r = this.module();
		double ang = Math.atan(this.im / this.re);
		r = Math.round(Math.pow(r, 1.0 / n));
		
		List<Complex> list = new ArrayList<>();
		for(int k = 0; k < n; k++) {
			double angK = (ang + 2*Math.PI*k) / n;
			
			double newRe = r * Math.cos(angK);
			double newIm = r * Math.sin(angK);
			list.add(new Complex(newRe, newIm));
		}
		return list;
	}
	
	@Override
	public String toString() {
		String c = "(" + this.re;
		if(this.im < 0) {
			c += "-i" + Math.abs(this.im) + ")";
		} else {
			c += "+i" + this.im + ")";
		}
		return c;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(im);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(re);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Complex))
			return false;
		Complex other = (Complex) obj;
		if (Double.doubleToLongBits(im) != Double.doubleToLongBits(other.im))
			return false;
		if (Double.doubleToLongBits(re) != Double.doubleToLongBits(other.re))
			return false;
		return true;
	}

	
}
