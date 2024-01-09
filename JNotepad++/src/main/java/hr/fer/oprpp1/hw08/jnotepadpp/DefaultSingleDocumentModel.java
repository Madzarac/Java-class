package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;



/**
 * 
 * @author TeaMadzarac
 *
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {
	
	private JTextArea editor;
	private boolean modified;
	private Path openedFilePath;
	private List<SingleDocumentListener> listeners = new ArrayList<>();
	
	public DefaultSingleDocumentModel(Path path, String text) {
		editor = new JTextArea(text);
		editor.setEditable(true);
		editor.addCaretListener(new CaretListener() {
                @Override
                public void caretUpdate(CaretEvent e) {
                    
                }
            });
		editor.getDocument().addDocumentListener(new DocumentListener() {

	        @Override
	        public void removeUpdate(DocumentEvent e) {
	        	setModified(true);
	        }

	        @Override
	        public void insertUpdate(DocumentEvent e) {
	        	setModified(true);
	        }

	        @Override
	        public void changedUpdate(DocumentEvent arg0) {
	        	setModified(true);
	        }
	    });
		modified = false;
		openedFilePath = path;


	}

	@Override
	public JTextArea getTextComponent() {
		return editor;
	}

	@Override
	public Path getFilePath() {
		return openedFilePath;
	}

	@Override
	public void setFilePath(Path path) {
		if(path == null) {
			throw new NullPointerException();
		}
		openedFilePath = path;
		for(SingleDocumentListener l : listeners) { 
			 l.documentFilePathUpdated(this);
		}
		
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void setModified(boolean modified) {
		this.modified = modified;
		for(SingleDocumentListener l : listeners) { 
			 l.documentModifyStatusUpdated(this);
		}
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		listeners = new ArrayList<>(listeners); 
		listeners.add(l);
		
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners = new ArrayList<>(listeners); 
		listeners.remove(l);
		
	}
	

}
