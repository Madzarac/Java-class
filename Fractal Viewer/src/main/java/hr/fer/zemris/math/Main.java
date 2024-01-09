package hr.fer.zemris.math;

public class Main {

	public static void main(String[] args) {
		
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(
				new Complex(2,0), Complex.ONE, Complex.ONE_NEG, Complex.IM, Complex.IM_NEG
				);
		ComplexPolynomial cp = crp.toComplexPolynom();
		System.out.println(crp.apply(new Complex(2,0)));
		System.out.println(cp.apply(new Complex(2,0)));
		System.out.println(crp.indexOfClosestRootFor(new Complex(-0.9995, 0), 0.002));

	}

}
