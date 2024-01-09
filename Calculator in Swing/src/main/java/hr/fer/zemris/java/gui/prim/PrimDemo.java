package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * PrimDemo po pokretanju otvara prozor u kojem se prikazuju dvije liste
 * @author TeaMadzarac
 *
 */
public class PrimDemo extends JFrame{
	
	/**
	 * konstruktor
	 */
	public PrimDemo() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
		setSize(500, 500);
		//pack();
	}
	

	/**
	 * Dodaje komponente u prozor
	 */
	private void initGUI() {
		
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		PrimListModel model = new PrimListModel();
		
		JButton button = new JButton("sljedeći");
		cp.add(button, BorderLayout.SOUTH);
		
		button.addActionListener(e -> {
			model.next();
		});
		
		JList<Integer> lista1 = new JList<>(model);
		JList<Integer> lista2 = new JList<>(model);
		
		JPanel central = new JPanel(new GridLayout(1, 0));
		central.add(new JScrollPane(lista1));
		central.add(new JScrollPane(lista2));
		
		cp.add(central, BorderLayout.CENTER);
		
	}


	/**
	 * Stvara primjerak razreda PrimDemo
	 * @param args argumenti dodani preko programske linije
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			new PrimDemo().setVisible(true);
		});
	}

}
