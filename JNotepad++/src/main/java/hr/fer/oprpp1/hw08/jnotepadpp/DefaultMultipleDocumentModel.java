package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;



/**
 * 
 * @author TeaMadzarac
 *
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {
	
	private List<SingleDocumentModel> models = new ArrayList<>();
	private SingleDocumentModel current;
	private List<MultipleDocumentListener> listeners = new ArrayList<>();
	private List<Boolean> oldModifyStatus = new ArrayList<>();
	private boolean running = false;
	
	public DefaultMultipleDocumentModel() {
		this.addChangeListener(new ChangeListener() {
        public void stateChanged(ChangeEvent e) {
            if(getSelectedIndex() >= 0 && !running) {
            	SingleDocumentModel old = current;
            	current = getDocument(getSelectedIndex());
            	for(MultipleDocumentListener l : listeners) {
					 l.currentDocumentChanged(old, current);
				}
            }
        }
    });
		
	}
	
	private void run() {
		
		ImageIcon green = createImageIcon("icons/green.png");
		ImageIcon red = createImageIcon("icons/red.png");
		this.removeAll();
		running = true;
		for(SingleDocumentModel model : models) {
			model.addSingleDocumentListener(new SingleDocumentListener() {

				@Override
				public void documentModifyStatusUpdated(SingleDocumentModel model) {
					if(oldModifyStatus.get(models.indexOf(model)) != model.isModified()) {
						ImageIcon icon;
						if(model.isModified()) {
							icon = red;
						} else {
							icon = green;
						}
						setIconAt(models.lastIndexOf(current), icon);
						oldModifyStatus.set(models.indexOf(model), model.isModified());
					}
				}

				@Override
				public void documentFilePathUpdated(SingleDocumentModel model) {
					String[] pathParts = model.getFilePath().toString().split("\\\\");
					String filename = pathParts[pathParts.length -1];
					setTitleAt(models.lastIndexOf(current), filename);
					
				}
				
			});
			JComponent panel1 =  new JScrollPane(model.getTextComponent());
			String filename, path;
			if(model.getFilePath() == null) {
				filename = "unnamed";
				path = "(unnamed)";
			} else {
				String[] pathParts = model.getFilePath().toString().split("\\\\");
				filename = pathParts[pathParts.length -1];
				path = model.getFilePath().toString();
			}
			ImageIcon icon;
			if(model.isModified()) {
				icon = red;
			} else {
				icon = green;
			}
			
			this.addTab(filename, icon, panel1, path);
			
		}
		running = false;
		this.setSelectedIndex(models.indexOf(current));
		
	}

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return models.iterator();
	}

	@Override
	public JComponent getVisualComponent() {
		return this;
	}
	


	private ImageIcon createImageIcon(String string) {
		InputStream is = this.getClass().getResourceAsStream(string);
		if(is==null) {
			throw new NullPointerException();
		}
		byte[] bytes = null;
		try {
			bytes = is.readAllBytes();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon icon = new ImageIcon(bytes);
		Image image = icon.getImage();
		Image newimg = image.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH);  
		icon = new ImageIcon(newimg);
		return icon;

	}

	@Override
	public SingleDocumentModel createNewDocument() {
		SingleDocumentModel newModel = new DefaultSingleDocumentModel(null, "");
		current = newModel;
		for(MultipleDocumentListener l : listeners) { 
			 l.documentAdded(newModel);
		}
		oldModifyStatus.add(false);
		models.add(current);
		run();
		return newModel;
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return current;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		if(path == null) {
			throw new NullPointerException("Path can't be null.");
		}
		boolean found = false;
		for(SingleDocumentModel model: models) {
			if(model.getFilePath() != null) {
				if(model.getFilePath().equals(path)) {
					current = model;
					found = true;
					for(MultipleDocumentListener l : listeners) { 
						 l.documentAdded(model);
					}
				}
			}	
		}
		if(!found) {
			byte[] okteti = null;
            try {
				okteti = Files.readAllBytes(path);
			} catch (IOException e) {
				return null;
			}
            String tekst = new String(okteti, StandardCharsets.UTF_8);
			SingleDocumentModel newModel = new DefaultSingleDocumentModel(path,tekst);
			models.add(newModel);
			oldModifyStatus.add(false);
			current = newModel;
			for(MultipleDocumentListener l : listeners) { 
				 l.documentAdded(newModel);
			}
			
		}
		run();
		return current;
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		if(newPath != null) {
			models.get(models.indexOf(model)).setFilePath(newPath);
		}
		model.setModified(false);
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		int index = models.indexOf(model);
		if(models.size() == 1) {
			for(MultipleDocumentListener l : listeners) { 
				 l.documentRemoved(model);
			}
		}else if(index == 0) {
			for(MultipleDocumentListener l : listeners) { 
				 l.currentDocumentChanged(current, models.get(1));
			}
			current = models.get(1);
		} else {
			for(MultipleDocumentListener l : listeners) { 
				 l.currentDocumentChanged(current, models.get(index - 1));
			}
			current = models.get(index - 1);
		}
		
		models.remove(index);
		oldModifyStatus.remove(index);
		run();

	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listeners = new ArrayList<>(listeners); 
		listeners.add(l);

	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners = new ArrayList<>(listeners); 
		listeners.remove(l);

	}

	@Override
	public int getNumberOfDocuments() {
		return models.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return models.get(index);
	}

	@Override
	public SingleDocumentModel findForPath(Path path) {
		for(int i = 0; i < models.size(); i++) {
			if(models.get(i).getFilePath().equals(path)) {
				return models.get(i);
			}
		}
		return null;
	}

	@Override
	public int getIndexOfDocument(SingleDocumentModel doc) {
		for(int i = 0; i < models.size(); i++) {
			if(models.get(i).getFilePath().equals(doc.getFilePath())) {
				return i;
			}
		}
		return -1;
	}

}
