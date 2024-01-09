package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;


import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;


public class NewtonParallel {
	
	private static Complex[] rootsAr;
	private static int numOfTracks = -1;
	private static int numOfWorkers = -1;
	

	public static void main(String[] args) {
		
		input(args);
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

	private static void input(String[] args) {
		
		String[] inp = new String[args.length];
		for(int i = 0; i < inp.length; i++) {
			inp[i] = args[i];
		}
		boolean workers = false;
		boolean tracks = false;
		
		int i = 0;
		while(i < inp.length) {
			if(inp[i].equals("-w")) {
				if(workers) {
					throw new IllegalArgumentException("You can't specify number of workers more than once!");
				} else {
					workers = true;
				}
				numOfWorkers = Integer.parseInt(inp[++i]);
				
			} else if(inp[i].startsWith("--workers=")) {
				if(workers) {
					throw new IllegalArgumentException("You can't specify number of workers more than once!");
				} else {
					workers = true;
				}
				inp[i] = inp[i].replace("--workers=", "");
				numOfWorkers = Integer.parseInt(inp[i]);
				
			} else if(inp[i].equals("-t")) {
				if(tracks) {
					throw new IllegalArgumentException("You can't specify number of tracks more than once!");
				} else {
					tracks = true;
				}
				numOfTracks = Integer.parseInt(inp[++i]);
				if(numOfTracks < 1) {
					throw new IllegalArgumentException("Number of tracks must be 1 or greater.");
				}
				
			} else if(inp[i].startsWith("--tracks=")) {
				if(tracks) {
					throw new IllegalArgumentException("You can't specify number of tracks more than once!");
				} else {
					tracks = true;
				}
				inp[i] = inp[i].replace("--tracks=", "");
				numOfTracks = Integer.parseInt(inp[i]);
				if(numOfTracks < 1) {
					throw new IllegalArgumentException("Number of tracks must be 1 or greater.");
				}
				
			} else {
				throw new IllegalArgumentException("Invalid input.");
			}
			i++;
		}
	}

	public static class PosaoIzracuna implements Runnable {
		double reMin;
		double reMax;
		double imMin;
		double imMax;
		int width;
		int height;
		int yMin;
		int yMax;
		int m;
		short[] data;
		AtomicBoolean cancel;
		public static PosaoIzracuna NO_JOB = new PosaoIzracuna();
		
		private PosaoIzracuna() {
		}
		
		public PosaoIzracuna(double reMin, double reMax, double imMin,
				double imMax, int width, int height, int yMin, int yMax, 
				int m, short[] data, AtomicBoolean cancel) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.m = m;
			this.data = data;
			this.cancel = cancel;
		}
		
		@Override
		public void run() {
			
			double convergenceTreshold = 0.001;
			double rootTreshold = 0.002;
			ComplexRootedPolynomial crp = new ComplexRootedPolynomial(Complex.ONE, rootsAr);
			ComplexPolynomial polynomial = crp.toComplexPolynom();
			ComplexPolynomial derived = polynomial.derive();
			int offset = 0;
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
					} while(iters < m && module > convergenceTreshold); 
					int index = crp.indexOfClosestRootFor(zn, rootTreshold);
					data[offset] = (short)(index + 1);
					offset++;
				}
			}
			
		}
	}
	
	
	public static class MojProducer implements IFractalProducer {
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			System.out.println("Zapocinjem izracun...");
			int m = 16*16*16;
			ComplexRootedPolynomial crp = new ComplexRootedPolynomial(Complex.ONE, rootsAr);
			ComplexPolynomial polynomial = crp.toComplexPolynom();
			short[] data = new short[width * height];
			int brojTraka;
			if(numOfTracks == -1) {
				brojTraka = Runtime.getRuntime().availableProcessors() * 4;
			} else if(numOfTracks > height) {
				brojTraka = height;
			}else {
				brojTraka = numOfTracks;
			}
			int brojYPoTraci = height / brojTraka;
			
			final BlockingQueue<PosaoIzracuna> queue = new LinkedBlockingQueue<>();

			int numOfThreads;
			if(numOfWorkers == -1) {
				numOfThreads = Runtime.getRuntime().availableProcessors();
			} else {
				numOfThreads = numOfWorkers;
			}
			Thread[] radnici = new Thread[numOfThreads];
			for(int i = 0; i < radnici.length; i++) {
				radnici[i] = new Thread(new Runnable() {
					@Override
					public void run() {
						while(true) {
							PosaoIzracuna p = null;
							try {
								p = queue.take();
								if(p==PosaoIzracuna.NO_JOB) break;
							} catch (InterruptedException e) {
								continue;
							}
							p.run();
						}
					}
				});
			}
			for(int i = 0; i < radnici.length; i++) {
				radnici[i].start();
			}
			
			for(int i = 0; i < brojTraka; i++) {
				int yMin = i*brojYPoTraci;
				int yMax = (i+1)*brojYPoTraci-1;
				if(i==brojTraka-1) {
					yMax = height-1;
				}
				PosaoIzracuna posao = new PosaoIzracuna(reMin, reMax, imMin, imMax, width, height, yMin, yMax, m, data, cancel);
				while(true) {
					try {
						queue.put(posao);
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			for(int i = 0; i < radnici.length; i++) {
				while(true) {
					try {
						queue.put(PosaoIzracuna.NO_JOB);
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			
			for(int i = 0; i < radnici.length; i++) {
				while(true) {
					try {
						radnici[i].join();
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			
			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short)(polynomial.order() + 1), requestNo);
			System.out.println("Workers: " + numOfThreads);
			System.out.println("Tracks: " + brojTraka);
		}
	}

}
