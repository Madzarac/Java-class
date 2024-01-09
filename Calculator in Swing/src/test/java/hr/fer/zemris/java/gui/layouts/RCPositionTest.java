package hr.fer.zemris.java.gui.layouts;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;


public class RCPositionTest {

	 @Test
	 public void testRowManjiOdJedan() {
		 assertThrows(CalcLayoutException.class, () -> new RCPosition(-1,3));
	 }
	 
	 @Test
	 public void testRowVeciOdPet() {
		 assertThrows(CalcLayoutException.class, () -> new RCPosition(6,3));
	 }
	 
	 @Test
	 public void testColumnManjiOdJedan() {
		 assertThrows(CalcLayoutException.class, () -> new RCPosition(3,-1));
	 }
	 
	 @Test
	 public void testColumnVeciOdSedam() {
		 assertThrows(CalcLayoutException.class, () -> new RCPosition(2,8));
	 }
	 
	 @Test
	 public void testZabrenjenaVrijednost() {
		 assertThrows(CalcLayoutException.class, () -> new RCPosition(1,5));
	 }
	 
	 @Test
	 public void testZabranjenaVrijednost2() {
		 assertThrows(CalcLayoutException.class, () -> new RCPosition(1,2));
	 }
}
