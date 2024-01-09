/**package hr.fer.zemris.math;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ComplexTest {

    @Test
    public void testModule() {
        double real = 3;
        double imaginary = 4;
        double expectedModule = Math.sqrt(Math.pow(real, 2) + Math.pow(imaginary, 2));

        Complex complex = new Complex(3, 4);

        assertEquals(expectedModule, complex.module());
    }

    @Test
    public void testMultiplyZero() {
        Complex input = new Complex(3, 4);

        assertEquals(Complex.ZERO, input.multiply(Complex.ZERO));
    }

    @Test
    public void testMultiply1() {
        Complex complex1 = new Complex(3, 4);
        Complex complex2 = new Complex(5, -6);

        Complex expected = new Complex(39, 2);

        assertEquals(expected, complex1.multiply(complex2));
    }

    @Test
    public void testMultiply2() {
        Complex complex1 = new Complex(3, 0);
        Complex complex2 = new Complex(0, 4);

        Complex expected = new Complex(0, 12);

        assertEquals(expected, complex1.multiply(complex2));
    }
    
    @Test
    public void testDivide() {
        Complex complex1 = new Complex(39, 2);
        Complex complex2 = new Complex(5, -6);

        Complex expected = new Complex(3, 4);

        assertEquals(expected, complex1.divide(complex2));
    }


    @Test
    public void testAdd() {
        Complex complex1 = new Complex(5, 6);
        Complex complex2 = new Complex(-7, 8);

        Complex expected = new Complex(-2, 14);

        assertEquals(expected, complex1.add(complex2));
    }


    @Test
    public void testSub() {
        Complex complex1 = new Complex(5, 6);
        Complex complex2 = new Complex(-7, 8);

        Complex expected = new Complex(12, -2);

        assertEquals(expected, complex1.sub(complex2));
    }

    @Test
    public void testNegate() {
        Complex input = new Complex(3, -4);
        Complex expected = new Complex(-3, 4);

        assertEquals(expected, input.negate());
    }

    @Test
    public void testPowerIllegalArgumentException() {
        Complex input = new Complex(5, -6);
        int n = -3;

        assertThrows(IllegalArgumentException.class, () -> input.power(n));
    }

    @Test
    public void testPowerNIsZero() {
        Complex input = new Complex(5, -6);
        int n = 0;

        assertEquals(Complex.ONE, input.power(n));
    }

    @Test
    public void testPowerNIsOne() {
        Complex input = new Complex(5, -6);
        int n = 1;

        assertEquals(input, input.power(n));
    }

    @Test
    public void testPower1() {
        Complex input = new Complex(5, -6);
        Complex expected = new Complex(-11, -60);
        int n = 2;

        assertEquals(expected, input.power(n));
    }

    @Test
    public void testPower2() {
        Complex input = new Complex(3, -4);
        Complex expected = new Complex(76443, -16124);
        int n = 7;

        assertEquals(expected, input.power(n));
    }


}*/

