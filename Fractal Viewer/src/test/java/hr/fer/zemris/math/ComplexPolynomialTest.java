/**package hr.fer.zemris.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ComplexPolynomialTest {


    @Test
    public void testComplexPolynomialMultiply() {
        ComplexPolynomial polynomial1 = new ComplexPolynomial(
                new Complex(2, 7),
                new Complex(-3, -8),
                new Complex(4, 9)
        );

        ComplexPolynomial polynomial2 = new ComplexPolynomial(
                new Complex(0, 10),
                new Complex(2, -20)
        );

        ComplexPolynomial actualResult = polynomial1.multiply(polynomial2);

        ComplexPolynomial expectedResult = new ComplexPolynomial(
            new Complex(-70, 20),
            new Complex(224, -56),
            new Complex(-256, 84),
            new Complex(188, -62)
        );

        assertEquals(expectedResult, actualResult);
    }
    
	
	@Test
    public void testApply() {
        Complex toApply = new Complex(2, 0);

        ComplexPolynomial polynomial = new ComplexPolynomial(new Complex(-2,0), Complex.ZERO, Complex.ZERO, Complex.ZERO, new Complex(2,0));

        Complex actualResult = polynomial.apply(toApply);
        Complex expectedResult = new Complex(30, 0);

        assertEquals(expectedResult, actualResult);
    }
}*/