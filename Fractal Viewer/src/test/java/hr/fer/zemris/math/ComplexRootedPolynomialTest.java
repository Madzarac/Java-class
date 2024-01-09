/**package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ComplexRootedPolynomialTest {


    @Test
    public void testApply() {
    	
    	ComplexRootedPolynomial crp = new ComplexRootedPolynomial(
				new Complex(2,0), Complex.ONE, Complex.ONE_NEG, Complex.IM, Complex.IM_NEG
				);

        Complex toApply = new Complex(2, 0);

        Complex actualResult = crp.apply(toApply);
        Complex expectedResult = new Complex(30, 0);

        assertEquals(expectedResult, actualResult);
    }
    
    @Test
    public void testIndexOfClosestRootFor() {
    	ComplexRootedPolynomial crp = new ComplexRootedPolynomial(
				new Complex(2,0), Complex.ONE, Complex.ONE_NEG, Complex.IM, Complex.IM_NEG
				);
		ComplexPolynomial cp = crp.toComplexPolynom();

        assertEquals(1, crp.indexOfClosestRootFor(new Complex(-0.9995, 0), 0.002));
    }


}*/