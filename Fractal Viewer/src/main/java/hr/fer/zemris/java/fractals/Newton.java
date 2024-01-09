package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

public class Newton {
	
	static Complex[] rootsAr;
	

	public static void main(String[] args) {
		
		System.out.print("Welcome to Newton-Raphson iteration-based fractal viewer.\nPlease enter at least two roots, one root per line. Enter 'done' when done.\n");
		Scanner sc = new Scanner(System.in);
		boolean done = false;
		int counter = 1;
		ArrayList<Complex> roots = new ArrayList<>();
		
		do {
			System.out.print("Root " + counter + "> ");
			String line = sc.nextLine();
			line = line.trim();
			if(line.equals("done")) {
				done = true;
			} else {
				String[] parts = line.split(" ");
				if(parts.length == 1) {
					if(line.contains("i")) {
						if(line.equals("i")) {
							roots.add(Complex.IM);
						} else if(line.equals("-i")) {
							roots.add(Complex.IM_NEG);
						} else {
							line = line.replace("i", "");
							roots.add(new Complex(0,Double.parseDouble(line)));
						}
					} else {
						roots.add(new Complex(Double.parseDouble(line),0));						
					}
					
				} else {
					double re, im;
					re = Double.parseDouble(parts[0]);
					if(parts[2].equals("i")) {
						im = 1;
					}else {
						im = Double.parseDouble(parts[2].replace("i", ""));
					}
					if(parts[1].equals("-") && im != 0) {
						im = im * (-1);
					}
					roots.add(new Complex(re,im));
				}
				counter++;
			}
		}while(!done);
		rootsAr = new Complex[roots.size()];
		for(int i = 0; i < roots.size(); i++) {
			rootsAr[i] = roots.get(i);
		}
		System.out.println("Image of fractal will appear shortly. Thank you.");
		FractalViewer.show(new MojProducer());
		sc.close();
	}
	
	
	
	public static class MojProducer implements IFractalProducer {
		
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			System.out.println("Zapocinjem izracun...");
			int maxIter = 16 * 16 * 16;
			double convergenceTreshold = 0.001;
			double rootTreshold = 0.002;
			ComplexRootedPolynomial crp = new ComplexRootedPolynomial(Complex.ONE, rootsAr);
			ComplexPolynomial polynomial = crp.toComplexPolynom();
			ComplexPolynomial derived = polynomial.derive();
			int offset = 0;
			short[] data = new short[width * height];
			for(int y = 0; y < height; y++) {
				if(cancel.get()) break;
				for(int x = 0; x < width; x++) {
					double cre = x / (width-1.0) * (reMax - reMin) + reMin;
					double cim = (height-1.0-y) / (height-1) * (imMax - imMin) + imMin;
					Complex zn = new Complex(cre, cim);
					double module = 0;
					int iters = 0;
					do {
						Complex numerator = polynomial.apply(zn);
						Complex denominator = derived.apply(zn);
						Complex znold = zn;
						Complex fraction = numerator.divide(denominator);
						zn = zn.sub(fraction);
						module = znold.sub(zn).module();
						iters++;
					} while(iters < maxIter && module > convergenceTreshold); 
					int index = crp.indexOfClosestRootFor(zn, rootTreshold);
					data[offset] = (short)(index + 1);
					offset++;
				}
			}
			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short)(polynomial.order() + 1), requestNo);
		}
	}

}
