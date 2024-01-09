package hr.fer.zemris.math;

import java.util.ArrayList;

public class ComplexRootedPolynomial {

	

	private Complex constant;
	private ArrayList<Complex> roots = new ArrayList<>();
	
	/**
	 * constructor
	 * @param constant z0
	 * @param roots roots
	 */
	public ComplexRootedPolynomial(Complex constant, Complex ... roots) {
		this.constant = constant;
		for(Complex root: roots) {
			this.roots.add(root);
		}
	}
	
	/**
	 * computes polynomial value at given point z
	 * @param z complex point
	 * @return complex value
	 */
	public Complex apply(Complex z) {
		Complex result = this.constant;
		for(int i = 0; i < roots.size(); i++) {
			Complex newB = (z.sub(roots.get(i)));
			result = result.multiply(newB);
		}
		return result;
	}
	
	/**
	 * converts this representation to ComplexPolynomial type
	 * @return complexPolynomial
	 */
	public ComplexPolynomial toComplexPolynom() {
		Complex[] ar = new Complex[roots.size() +1];
		for(int i = 0; i < ar.length; i++) {
			ar[i] = Complex.ZERO;
		}
		ar[roots.size()] = this.constant;
		Complex res = this.constant;
		for(int i = 0; i < roots.size(); i++) {
			res = res.multiply(roots.get(i).negate());
		}
		ar[0] = res;
		return new ComplexPolynomial(ar);
	}
	
	@Override
	public String toString() {
		String c = this.constant.toString();
		for(int i = 0; i < roots.size(); i++) {
			c += "*(z-" + roots.get(i) + ")";
		}
		return c;
	}
	
	/**
	 * finds index of closest root for given complex number z that is within
	 * treshold; if there is no such root, returns -1
	 * first root has index 0, second index 1, etc
	 * @param z complex number
	 * @param treshold treshold
	 * @return index of closest root
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		int index = -1;
		double minDist = -1;
		for(Complex root: roots) {
			double distance = Math.sqrt((root.getRe() - z.getRe())*(root.getRe() - z.getRe()) + 
					(root.getIm() - z.getIm())*(root.getIm() - z.getIm()));
			//double distance = root.sub(z).module();
			if(distance < treshold) {
				if(minDist == -1 || minDist > distance) {
					minDist = distance;
					index = roots.indexOf(root);
				}
			}
		}
		return index;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((constant == null) ? 0 : constant.hashCode());
		result = prime * result + ((roots == null) ? 0 : roots.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ComplexRootedPolynomial))
			return false;
		ComplexRootedPolynomial other = (ComplexRootedPolynomial) obj;
		if (constant == null) {
			if (other.constant != null)
				return false;
		} else if (!constant.equals(other.constant))
			return false;
		if (roots == null) {
			if (other.roots != null)
				return false;
		} else if (!roots.equals(other.roots))
			return false;
		return true;
	}

}
