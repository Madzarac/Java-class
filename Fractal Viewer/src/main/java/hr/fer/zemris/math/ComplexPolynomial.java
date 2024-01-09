package hr.fer.zemris.math;

import java.util.ArrayList;

public class ComplexPolynomial {

	private ArrayList<Complex> factors = new ArrayList<>();
	
	/**
	 * constructor
	 * @param factors factors 
	 */
	public ComplexPolynomial(Complex ...factors) {
		for(Complex factor: factors) {
			this.factors.add(factor);
		}
	}
	
	/**
	 * eg. For (7+2i)z^3+2z^2+5z+1 returns 3
	 * @return order of this polynom
	 */
	public short order() {
		return (short) (factors.size() -1);
	}
	
	// computes a new polynomial this*p
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Complex[] newPol = new Complex[this.order() + p.order() + 1];
		for(int i = 0; i <= this.order(); i++) {
			for(int j = 0; j <= p.order(); j++) {
				Complex calc = this.factors.get(i).multiply(p.factors.get(j));
				if(newPol[i + j] == null) {
					newPol[i + j] = Complex.ZERO;
				}
				newPol[i + j] = newPol[i + j].add(calc);
			}
		}
		return new ComplexPolynomial(newPol);
	}
	
	/**
	 * computes first derivative of this polynomial; for example, for
	 * (7+2i)z^3+2z^2+5z+1 returns (21+6i)z^2+4z+5
	 * @return derivation of polynom
	 */
	public ComplexPolynomial derive() {
		Complex[] derived = new Complex[factors.size() -1];
		for(int i = 1; i <= factors.size() -1; i++) {
			derived[i - 1] = (new Complex(factors.get(i).getRe() *i, factors.get(i).getIm() *i));
		}
		return new ComplexPolynomial(derived);
	}
	
	/**
	 * computes polynomial value at given point z
	 * @param z complex point
	 * @return complex value
	 */
	public Complex apply(Complex z) {
		Complex result = factors.get(0);
		for(int i = 1; i < factors.size(); i++) {
			result = result.add(factors.get(i).multiply(z.power(i)));
		}
		return result;
	}
	
	@Override
	public String toString() {
		String c = "";
		for(int i = factors.size()-1; i > 0; i--) {
			c += factors.get(i).toString() + "*z^" + i + "+";
		}
		if(!factors.isEmpty()) {
			c += factors.get(0).toString();
		}
		return c;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((factors == null) ? 0 : factors.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ComplexPolynomial))
			return false;
		ComplexPolynomial other = (ComplexPolynomial) obj;
		if (factors == null) {
			if (other.factors != null)
				return false;
		} else if (!factors.equals(other.factors))
			return false;
		return true;
	}
	
	
}
