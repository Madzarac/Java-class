package hr.fer.oprpp1.hw08.jnotepadpp;

import javax.swing.JLabel;
import javax.swing.JTextArea;


public class JTextAreaObserver extends JTextArea{
	
	private final SingleDocumentModel model;

	public JTextAreaObserver(String text, SingleDocumentModel model) {
		super(text);
		model.addSingleDocumentListener(l);
		this.model = model;
		
	}

	private void updateText() { 
		setText(model.getTextComponent().getText()); 
	} 

	
	private final SingleDocumentListener l = new SingleDocumentListener() {

		@Override
		public void documentModifyStatusUpdated(SingleDocumentModel model) {
			//updateText();
			
		}

		@Override
		public void documentFilePathUpdated(SingleDocumentModel model) {
			// TODO Auto-generated method stub
			
		}
		
	};

	
}
