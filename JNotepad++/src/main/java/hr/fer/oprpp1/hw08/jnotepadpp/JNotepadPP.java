package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;


public class JNotepadPP extends JFrame {
	
	MultipleDocumentModel mdm;
	private String copiedText;
	JMenuItem upper;
    JMenuItem lower;
    JMenuItem invert;
    int col, ln, len, sel;
    JLabel lenLabel, caretLabel;

	public JNotepadPP() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setLocation(0, 0);
        setSize(600, 600);
        setTitle("JNotepad++");
        initGUI();
        this.addWindowListener(new WindowAdapter() {	
        	@Override
			public void windowClosing(WindowEvent e) {
        		exitAction.actionPerformed(null);
        	}
        });		
	}
		
	private void initGUI() {
		
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		cp.add(centerPanel, BorderLayout.CENTER);
		
		mdm = new DefaultMultipleDocumentModel();
		centerPanel.add(mdm.getVisualComponent(), BorderLayout.CENTER);
		
		mdm.addMultipleDocumentListener(new MultipleDocumentListener() {

			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				String path = currentModel.getFilePath() == null ? "(unnamed)" : currentModel.getFilePath().toString();
				setTitle(path + " - JNotepad++");
				lenLabel.setText("  length:" + mdm.getCurrentDocument().getTextComponent().getText().length());
				caretLabel.setText("  Ln:0  Col:0  Sel:0");
			}

			@Override
			public void documentAdded(SingleDocumentModel model) {
				String path = model.getFilePath() == null ? "(unnamed)" : model.getFilePath().toString();
				setTitle(path + " - JNotepad++");
				lenLabel.setText("  length:" + mdm.getCurrentDocument().getTextComponent().getText().length());
				caretLabel.setText("  Ln:0  Col:0  Sel:0");
			}

			@Override
			public void documentRemoved(SingleDocumentModel model) {
				setTitle("JNotepad++");
				lenLabel.setText("  length:0");
				caretLabel.setText("  Ln:0  Col:0  Sel:0");
			}
			
		});
				
		JPanel statusPanel = new JPanel();
		statusPanel.setLayout(new GridLayout());
		lenLabel = new JLabel("  length:" + len);
	    statusPanel.add(lenLabel);
	    caretLabel = new JLabel("  Ln:" + ln + "  Col:" + col + "  Sel:" + sel);
	    statusPanel.add(caretLabel);
	    
	    final java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy hh:mm:ss  ");
	    final JLabel timeLabel = new JLabel(sdf.format(new java.util.Date()));
	    JPanel flowPanel = new JPanel();
	    flowPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
	    flowPanel.add(timeLabel);
	    statusPanel.add(flowPanel);
	    ActionListener al = new ActionListener(){
	      public void actionPerformed(ActionEvent ae){
	        timeLabel.setText(sdf.format(new java.util.Date()));
	      }
	    };
	    new javax.swing.Timer(1000,al).start();
	    
	    centerPanel.add(statusPanel, BorderLayout.SOUTH);
        
	    ActionListener a = new ActionListener(){
	  	      public void actionPerformed(ActionEvent ae){
	  	    	if(mdm.getCurrentDocument() != null) {
	  	        	mdm.getCurrentDocument().getTextComponent().addCaretListener(new CaretListener() {
	  	                @Override
	  	                public void caretUpdate(CaretEvent e) {
	  	                    int length = mdm.getCurrentDocument().getTextComponent().getSelectionEnd() - mdm.getCurrentDocument().getTextComponent().getSelectionStart();
	  	                    updateStatusBar();
	  	                    if(length == 0) {
	  	                    	upper.setEnabled(false);
	  	                    	lower.setEnabled(false);
	  	                    	invert.setEnabled(false);
	  	                    } else {
	  	                    	upper.setEnabled(true);
	  	                    	lower.setEnabled(true);
	  	                    	invert.setEnabled(true);
	  	                    }
	  	                }
	  	            });
	  	        }
	  	      }
	  	    };
	  	new javax.swing.Timer(100,a).start();
	  
		
		createActions();
		createMenus();
		createToolbars();
		
		
		
	}
	
	private void updateStatusBar() {
		if(mdm.getCurrentDocument() != null) {
			int length = mdm.getCurrentDocument().getTextComponent().getSelectionEnd() - mdm.getCurrentDocument().getTextComponent().getSelectionStart();
			String text = mdm.getCurrentDocument().getTextComponent().getText();
	        int offset = mdm.getCurrentDocument().getTextComponent().getCaretPosition();
	        len = text.length();
	        lenLabel.setText("  length:" + len);
	        String textUntilCaret = null;
	        try {
				textUntilCaret = mdm.getCurrentDocument().getTextComponent().getText(0, offset);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
	        sel = length;
	        ln = textUntilCaret.split("\n", -1).length;
	        col = textUntilCaret.split("\n", -1)[ln -1].length() + 1;
	        caretLabel.setText("  Ln:" + ln + "  Col:" + col + "  Sel:" + sel);
		}
	}
	
	 private void createActions() {
	        openDocumentAction.putValue(
	                Action.NAME,
	                "Open");
	        openDocumentAction.putValue(
	                Action.ACCELERATOR_KEY,
	                KeyStroke.getKeyStroke("control O"));
	        openDocumentAction.putValue(
	                Action.MNEMONIC_KEY,
	                KeyEvent.VK_O);
	        openDocumentAction.putValue(
	                Action.SHORT_DESCRIPTION,
	                "Used to open existing file from disk.");
	        
	        
	        openNewDocumentAction.putValue(
	                Action.NAME,
	                "New file");
	        openNewDocumentAction.putValue(
	                Action.ACCELERATOR_KEY,
	                KeyStroke.getKeyStroke("control N"));
	        openNewDocumentAction.putValue(
	                Action.MNEMONIC_KEY,
	                KeyEvent.VK_N);
	        openNewDocumentAction.putValue(
	                Action.SHORT_DESCRIPTION,
	                "Used to crate new file.");

	        
	        saveDocumentAction.putValue(
	                Action.NAME,
	                "Save");
	        saveDocumentAction.putValue(
	                Action.ACCELERATOR_KEY,
	                KeyStroke.getKeyStroke("control S"));
	        saveDocumentAction.putValue(
	                Action.MNEMONIC_KEY,
	                KeyEvent.VK_S);
	        saveDocumentAction.putValue(
	                Action.SHORT_DESCRIPTION,
	                "Used to save current file to disk.");
	        
	        
	        saveAsDocumentAction.putValue(
	                Action.NAME,
	                "Save as");
	        saveAsDocumentAction.putValue(
	                Action.ACCELERATOR_KEY,
	                KeyStroke.getKeyStroke("control shift A"));
	        saveAsDocumentAction.putValue(
	                Action.MNEMONIC_KEY,
	                KeyEvent.VK_A);
	        saveAsDocumentAction.putValue(
	                Action.SHORT_DESCRIPTION,
	                "Used to save current file to disk.");
	        
	        statisticalInfoAction.putValue(
	                Action.NAME,
	                "Info");
	        statisticalInfoAction.putValue(
	                Action.ACCELERATOR_KEY,
	                KeyStroke.getKeyStroke("control I"));
	        statisticalInfoAction.putValue(
	                Action.MNEMONIC_KEY,
	                KeyEvent.VK_I);
	        statisticalInfoAction.putValue(
	                Action.SHORT_DESCRIPTION,
	                "Gives statistical info of a file.");
	        
	        
	        closeTabAction.putValue(
	                Action.NAME,
	                "Close tab");
	        closeTabAction.putValue(
	                Action.ACCELERATOR_KEY,
	                KeyStroke.getKeyStroke("control T"));
	        closeTabAction.putValue(
	                Action.MNEMONIC_KEY,
	                KeyEvent.VK_T);
	        closeTabAction.putValue(
	                Action.SHORT_DESCRIPTION,
	                "Used to close document shown it a tab.");
	        

	        cutAction.putValue(
	                Action.NAME,
	                "Cut");
	        cutAction.putValue(
	                Action.ACCELERATOR_KEY,
	                KeyStroke.getKeyStroke("control K"));
	        cutAction.putValue(
	                Action.MNEMONIC_KEY,
	                KeyEvent.VK_K);
	        cutAction.putValue(
	                Action.SHORT_DESCRIPTION,
	                "Used to cut selected part of text.");

	        copyAction.putValue(
	                Action.NAME,
	                "Copy");
	        copyAction.putValue(
	                Action.ACCELERATOR_KEY,
	                KeyStroke.getKeyStroke("control shift C"));
	        copyAction.putValue(
	                Action.MNEMONIC_KEY,
	                KeyEvent.VK_C);
	        copyAction.putValue(
	                Action.SHORT_DESCRIPTION,
	                "Used to copy selected part of text or entire document.");
	        
	        pasteAction.putValue(
	                Action.NAME,
	                "Paste");
	        pasteAction.putValue(
	                Action.ACCELERATOR_KEY,
	                KeyStroke.getKeyStroke("control P"));
	        pasteAction.putValue(
	                Action.MNEMONIC_KEY,
	                KeyEvent.VK_P);
	        pasteAction.putValue(
	                Action.SHORT_DESCRIPTION,
	                "Used to paste copied text.");


	        exitAction.putValue(
	                Action.NAME,
	                "Exit");
	        exitAction.putValue(
	                Action.ACCELERATOR_KEY,
	                KeyStroke.getKeyStroke("control X"));
	        exitAction.putValue(
	                Action.MNEMONIC_KEY,
	                KeyEvent.VK_X);
	        exitAction.putValue(
	                Action.SHORT_DESCRIPTION,
	                "Exit application.");
	        
	        toUppercaseAction.putValue(
	                Action.NAME,
	                "Uppercase");
	        toUppercaseAction.putValue(
	                Action.MNEMONIC_KEY,
	                KeyEvent.VK_U);
	        toUppercaseAction.putValue(
	                Action.SHORT_DESCRIPTION,
	                "Turns selected text to uppercase.");
	        
	        toLowercaseAction.putValue(
	                Action.NAME,
	                "Lowercase");
	        toLowercaseAction.putValue(
	                Action.MNEMONIC_KEY,
	                KeyEvent.VK_L);
	        toLowercaseAction.putValue(
	                Action.SHORT_DESCRIPTION,
	                "Turns selected text to lowercase.");
	        
	        invertCaseAction.putValue(
	                Action.NAME,
	                "Invert case");
	        invertCaseAction.putValue(
	                Action.MNEMONIC_KEY,
	                KeyEvent.VK_I);
	        invertCaseAction.putValue(
	                Action.SHORT_DESCRIPTION,
	                "Inverts case of selected text.");
	        
	        
	        ascendingAction.putValue(
	                Action.NAME,
	                "Ascending");
	        ascendingAction.putValue(
	                Action.MNEMONIC_KEY,
	                KeyEvent.VK_A);
	        ascendingAction.putValue(
	                Action.SHORT_DESCRIPTION,
	                "Sorts selected lines, ascend.");
	        
	        
	        descendingAction.putValue(
	                Action.NAME,
	                "Descending");
	        descendingAction.putValue(
	                Action.MNEMONIC_KEY,
	                KeyEvent.VK_D);
	        descendingAction.putValue(
	                Action.SHORT_DESCRIPTION,
	                "Sorts selected lines, descend.");
	        
	        uniqueAction.putValue(
	                Action.NAME,
	                "Unique");
	        uniqueAction.putValue(
	                Action.MNEMONIC_KEY,
	                KeyEvent.VK_U);
	        uniqueAction.putValue(
	                Action.SHORT_DESCRIPTION,
	                "Removes duplicate lines.");
	                
	    }
	
	private Action openDocumentAction = new AbstractAction() {

        private static final long serialVersionUID = 1L;

        public void actionPerformed(ActionEvent e) {
            JFileChooser fc = new JFileChooser();
            fc.setDialogTitle("Open file");
            if(fc.showOpenDialog(JNotepadPP.this)!=JFileChooser.APPROVE_OPTION) {
                return;
            }
            File fileName = fc.getSelectedFile();
            Path filePath = fileName.toPath();
            if(!Files.isReadable(filePath)) {
                JOptionPane.showMessageDialog(
                        JNotepadPP.this,
                        "File "+fileName.getAbsolutePath()+" doesn't exist!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(mdm.loadDocument(filePath) == null) {
            	JOptionPane.showMessageDialog(
                        JNotepadPP.this,
                        "Error while loading file "+fileName.getAbsolutePath()+".",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    };
    
    private Action openNewDocumentAction = new AbstractAction() {

        private static final long serialVersionUID = 1L;

        public void actionPerformed(ActionEvent e) {
            mdm.createNewDocument();
        }
    };
	
    private Action saveDocumentAction = new AbstractAction() {

        private static final long serialVersionUID = 1L;

        public void actionPerformed(ActionEvent e) {
        	Path openedFilePath = mdm.getCurrentDocument().getFilePath();
            if(openedFilePath == null) {
                JFileChooser jfc = new JFileChooser();
                jfc.setDialogTitle("Save document");
                if(jfc.showSaveDialog(JNotepadPP.this)!=JFileChooser.APPROVE_OPTION) {
                    JOptionPane.showMessageDialog(
                            JNotepadPP.this,
                            "Nothing had been saved.",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                openedFilePath = jfc.getSelectedFile().toPath();
                File file = new File(jfc.getSelectedFile().toString());
                if (file.exists()) {
                    int response = JOptionPane.showConfirmDialog(null, //
                            "Do you want to replace the existing file?", //
                            "Confirm", JOptionPane.YES_NO_OPTION, //
                            JOptionPane.QUESTION_MESSAGE);
                    if (response != JOptionPane.YES_OPTION) {
                        return;
                    } 
                }
            }
            
            byte[] podatci = mdm.getCurrentDocument().getTextComponent().getText().getBytes(StandardCharsets.UTF_8);
            try {
                Files.write(openedFilePath, podatci);
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(
                        JNotepadPP.this,
                        "Error while writing file "+openedFilePath.toFile().getAbsolutePath()+".\nIt's unclear in which state the file on the disc is!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            mdm.saveDocument(mdm.getCurrentDocument(), openedFilePath);
            JOptionPane.showMessageDialog(
                    JNotepadPP.this,
                    "File saved.",
                    "Information",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    };
    
    
    private Action saveAsDocumentAction = new AbstractAction() {

        private static final long serialVersionUID = 1L;

        public void actionPerformed(ActionEvent e) {
            JFileChooser jfc = new JFileChooser();
            jfc.setDialogTitle("Save document as");
            if(jfc.showSaveDialog(JNotepadPP.this)!=JFileChooser.APPROVE_OPTION) {
                JOptionPane.showMessageDialog(
                        JNotepadPP.this,
                        "Nothing had been saved.",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            Path openedFilePath = jfc.getSelectedFile().toPath();
            File file = new File(jfc.getSelectedFile().toString());
            if (file.exists()) {
                int response = JOptionPane.showConfirmDialog(null, //
                        "Do you want to replace the existing file?", //
                        "Confirm", JOptionPane.YES_NO_OPTION, //
                        JOptionPane.QUESTION_MESSAGE);
                if (response != JOptionPane.YES_OPTION) {
                    return;
                } 
            }
            
            byte[] podatci = mdm.getCurrentDocument().getTextComponent().getText().getBytes(StandardCharsets.UTF_8);
            try {
                Files.write(openedFilePath, podatci);
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(
                        JNotepadPP.this,
                        "Error while writing file "+openedFilePath.toFile().getAbsolutePath()+".\nIt's unclear in which state the file on the disc is!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            mdm.saveDocument(mdm.getCurrentDocument(), openedFilePath);
            JOptionPane.showMessageDialog(
                    JNotepadPP.this,
                    "File saved.",
                    "Information",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    };
    
    private Action exitAction = new AbstractAction() {

        private static final long serialVersionUID = 1L;

        public void actionPerformed(ActionEvent e) {
        	boolean aborted = false;
        	Iterator<SingleDocumentModel> iterModels = mdm.iterator();
        	while (iterModels.hasNext()) {
        		SingleDocumentModel m = iterModels.next();
        		if(m.isModified()) {
        			String path = m.getFilePath() == null ? "(unnamed)" : m.getFilePath().toString();
        			int input = JOptionPane.showConfirmDialog(null, path + " has been modified. Do you want to save it?", "Unsaved document", JOptionPane.YES_NO_CANCEL_OPTION);
        		    if(input == 0) {
        		    	saveDocumentAction.actionPerformed(e);
        		    } else if(input == 1) {
        		    	//nothing
        		    } else { //2 or -1
        		    	aborted = true;
        		    }
        		}
        	}
        	if(!aborted) {
        		System.exit(0);
        	}
            
        }
    };
    
    
    private Action closeTabAction = new AbstractAction() {

        public void actionPerformed(ActionEvent e) {
        	boolean aborted = false;
        	if(mdm.getCurrentDocument().isModified()) {
        		String path = mdm.getCurrentDocument().getFilePath() == null ? "unnamed" : mdm.getCurrentDocument().getFilePath().toString();
    			int input = JOptionPane.showConfirmDialog(null, path + " has been modified. Do you want to save it?", "Unsaved document", JOptionPane.YES_NO_CANCEL_OPTION);
    		    if(input == 0) {
    		    	saveDocumentAction.actionPerformed(e);
    		    } else if(input == 1) {
    		    	//nothing
    		    } else { //2 or -1
    		    	aborted = true;
    		    }
    		}
        	if(!aborted) {
        		mdm.closeDocument(mdm.getCurrentDocument()); 
        	}
        	           
        }
    };
    
    private Action copyAction = new AbstractAction() {


        public void actionPerformed(ActionEvent e) {
            Document doc = mdm.getCurrentDocument().getTextComponent().getDocument();
            int len = Math.abs(mdm.getCurrentDocument().getTextComponent().getCaret().getDot()-mdm.getCurrentDocument().getTextComponent().getCaret().getMark());
            int offset = 0;
            if(len!=0) {
                offset = Math.min(mdm.getCurrentDocument().getTextComponent().getCaret().getDot(),mdm.getCurrentDocument().getTextComponent().getCaret().getMark());
            } else {
                len = doc.getLength();
            }
            try {
                copiedText = doc.getText(offset, len);
            } catch(BadLocationException ex) {
                ex.printStackTrace();
            }
        }
    };
    
    private Action pasteAction = new AbstractAction() {

        public void actionPerformed(ActionEvent e) {
            Document doc = mdm.getCurrentDocument().getTextComponent().getDocument();
            int len = Math.abs(mdm.getCurrentDocument().getTextComponent().getCaret().getDot()-mdm.getCurrentDocument().getTextComponent().getCaret().getMark());
            int offset = 0;
            if(len!=0) {
                offset = Math.min(mdm.getCurrentDocument().getTextComponent().getCaret().getDot(),mdm.getCurrentDocument().getTextComponent().getCaret().getMark());
            } else {
            	offset = mdm.getCurrentDocument().getTextComponent().getCaret().getDot();
            }
            try {
                doc.remove(offset, len);
                doc.insertString(offset, copiedText, null);
            } catch(BadLocationException ex) {
                ex.printStackTrace();
            }
        }
    };
    
    private Action cutAction = new AbstractAction() {


        public void actionPerformed(ActionEvent e) {
            Document doc = mdm.getCurrentDocument().getTextComponent().getDocument();
            int len = Math.abs(mdm.getCurrentDocument().getTextComponent().getCaret().getDot()-mdm.getCurrentDocument().getTextComponent().getCaret().getMark());
            int offset = 0;
            offset = Math.min(mdm.getCurrentDocument().getTextComponent().getCaret().getDot(),mdm.getCurrentDocument().getTextComponent().getCaret().getMark());
            
            try {
                copiedText = doc.getText(offset, len);
                doc.remove(offset, len);
            } catch(BadLocationException ex) {
                ex.printStackTrace();
            }
        }
    };
    
    private Action toLowercaseAction = new AbstractAction() {

        private static final long serialVersionUID = 1L;

        public void actionPerformed(ActionEvent e) {
        	Document doc = mdm.getCurrentDocument().getTextComponent().getDocument();
            int len = Math.abs( mdm.getCurrentDocument().getTextComponent().getCaret().getDot()- mdm.getCurrentDocument().getTextComponent().getCaret().getMark());
            int offset = 0;
            if(len!=0) {
                offset = Math.min( mdm.getCurrentDocument().getTextComponent().getCaret().getDot(), mdm.getCurrentDocument().getTextComponent().getCaret().getMark());
            } else {
                len = doc.getLength();
            }
            try {
                String text = doc.getText(offset, len);
                text = text.toLowerCase();
                doc.remove(offset, len);
                doc.insertString(offset, text, null);
            } catch(BadLocationException ex) {
                ex.printStackTrace();
            }
        }
    };
    
    
    private Action toUppercaseAction = new AbstractAction() {

        private static final long serialVersionUID = 1L;

        public void actionPerformed(ActionEvent e) {
        	Document doc = mdm.getCurrentDocument().getTextComponent().getDocument();
            int len = Math.abs( mdm.getCurrentDocument().getTextComponent().getCaret().getDot()- mdm.getCurrentDocument().getTextComponent().getCaret().getMark());
            int offset = 0;
            if(len!=0) {
                offset = Math.min( mdm.getCurrentDocument().getTextComponent().getCaret().getDot(), mdm.getCurrentDocument().getTextComponent().getCaret().getMark());
            } else {
                len = doc.getLength();
            }
            try {
                String text = doc.getText(offset, len);
                text = text.toUpperCase();
                doc.remove(offset, len);
                doc.insertString(offset, text, null);
            } catch(BadLocationException ex) {
                ex.printStackTrace();
            }
        }
    };
    
    private Action invertCaseAction = new AbstractAction() {

        private static final long serialVersionUID = 1L;

        public void actionPerformed(ActionEvent e) {
        	Document doc = mdm.getCurrentDocument().getTextComponent().getDocument();
            int len = Math.abs( mdm.getCurrentDocument().getTextComponent().getCaret().getDot()- mdm.getCurrentDocument().getTextComponent().getCaret().getMark());
            int offset = 0;
            if(len!=0) {
                offset = Math.min( mdm.getCurrentDocument().getTextComponent().getCaret().getDot(), mdm.getCurrentDocument().getTextComponent().getCaret().getMark());
            } else {
                len = doc.getLength();
            }
            try {
                String text = doc.getText(offset, len);
                text = changeCase(text);
                doc.remove(offset, len);
                doc.insertString(offset, text, null);
            } catch(BadLocationException ex) {
                ex.printStackTrace();
            }
        }

        private String changeCase(String text) {
            char[] znakovi = text.toCharArray();
            for(int i = 0; i < znakovi.length; i++) {
                char c = znakovi[i];
                if(Character.isLowerCase(c)) {
                    znakovi[i] = Character.toUpperCase(c);
                } else if(Character.isUpperCase(c)) {
                    znakovi[i] = Character.toLowerCase(c);
                }
            }
            return new String(znakovi);
        }
    };
    
    private Action ascendingAction = new AbstractAction() {

        private static final long serialVersionUID = 1L;

        public void actionPerformed(ActionEvent e) {
        	if(mdm.getCurrentDocument() != null) {
        		int from = Math.min(mdm.getCurrentDocument().getTextComponent().getCaret().getDot(), mdm.getCurrentDocument().getTextComponent().getCaret().getMark());
            	int to = Math.max(mdm.getCurrentDocument().getTextComponent().getCaret().getDot(), mdm.getCurrentDocument().getTextComponent().getCaret().getMark());
    	        String textFrom = null;
    	        String textTo = null;
    	        try {
    				textFrom = mdm.getCurrentDocument().getTextComponent().getText(0, from);
    				textTo = mdm.getCurrentDocument().getTextComponent().getText(0, to);
    			} catch (BadLocationException e1) {
    				e1.printStackTrace();
    			}
    	        int fromLine = textFrom.split("\n", -1).length -1;
    	        int toLine = textTo.split("\n", -1).length -1;
    	        String text = mdm.getCurrentDocument().getTextComponent().getText();
    	        String[] lines = text.split("\n", -1);
    	        int min;
    	        for(int i = fromLine; i <= toLine; i++) {
    	        	min = i;
    	        	for(int j = i + 1; j <= toLine; j++) {
    	        		if(lines[j].toLowerCase().compareTo(lines[min].toLowerCase()) < 0 ) {
    	        			min = j;
    	        		}
    	        	}
    	        	String pom = lines[i];
    	        	lines[i] = lines[min];
    	        	lines[min] = pom;
    	        }
    	        text = String.join("\n", lines);
    	        mdm.getCurrentDocument().getTextComponent().setText(text);

    		}
        }
    };
    
    private Action descendingAction = new AbstractAction() {

        private static final long serialVersionUID = 1L;

        public void actionPerformed(ActionEvent e) {
        	if(mdm.getCurrentDocument() != null) {
        		int from = Math.min(mdm.getCurrentDocument().getTextComponent().getCaret().getDot(), mdm.getCurrentDocument().getTextComponent().getCaret().getMark());
            	int to = Math.max(mdm.getCurrentDocument().getTextComponent().getCaret().getDot(), mdm.getCurrentDocument().getTextComponent().getCaret().getMark());
    	        String textFrom = null;
    	        String textTo = null;
    	        try {
    				textFrom = mdm.getCurrentDocument().getTextComponent().getText(0, from);
    				textTo = mdm.getCurrentDocument().getTextComponent().getText(0, to);
    			} catch (BadLocationException e1) {
    				e1.printStackTrace();
    			}
    	        int fromLine = textFrom.split("\n", -1).length -1;
    	        int toLine = textTo.split("\n", -1).length -1;
    	        String text = mdm.getCurrentDocument().getTextComponent().getText();
    	        String[] lines = text.split("\n", -1);
    	        int max;
    	        for(int i = fromLine; i <= toLine; i++) {
    	        	max = i;
    	        	for(int j = i + 1; j <= toLine; j++) {
    	        		if(lines[j].toLowerCase().compareTo(lines[max].toLowerCase()) > 0 ) {
    	        			max = j;
    	        		}
    	        	}
    	        	String pom = lines[i];
    	        	lines[i] = lines[max];
    	        	lines[max] = pom;
    	        }
    	        text = String.join("\n", lines);
    	        mdm.getCurrentDocument().getTextComponent().setText(text);

    		}
        }
    };
    
    private Action uniqueAction = new AbstractAction() {

        private static final long serialVersionUID = 1L;

        public void actionPerformed(ActionEvent e) {
        	if(mdm.getCurrentDocument() != null) {
        		int from = Math.min(mdm.getCurrentDocument().getTextComponent().getCaret().getDot(), mdm.getCurrentDocument().getTextComponent().getCaret().getMark());
            	int to = Math.max(mdm.getCurrentDocument().getTextComponent().getCaret().getDot(), mdm.getCurrentDocument().getTextComponent().getCaret().getMark());
    	        String textFrom = null;
    	        String textTo = null;
    	        try {
    				textFrom = mdm.getCurrentDocument().getTextComponent().getText(0, from);
    				textTo = mdm.getCurrentDocument().getTextComponent().getText(0, to);
    			} catch (BadLocationException e1) {
    				e1.printStackTrace();
    			}
    	        int fromLine = textFrom.split("\n", -1).length -1;
    	        int toLine = textTo.split("\n", -1).length -1;
    	        String text = mdm.getCurrentDocument().getTextComponent().getText();
    	        String[] lines = text.split("\n", -1);
    	        Set<String> set = new LinkedHashSet<>();
    	        for(int i = fromLine; i <= toLine; i++) {
    	        	set.add(lines[i]);
    	        }
    	        List<String> newText = new ArrayList<>();
    	        for(int i = 0; i < lines.length; i++) {
    	        	if(i == fromLine) {
    	        		newText.addAll(set);
    	        	} else if(i > fromLine && i <= toLine) {
    	        		//do nothing
    	        	} else {
    	        		newText.add(lines[i]);
    	        	}
    	        }
    	        text = String.join("\n", newText);
    	        mdm.getCurrentDocument().getTextComponent().setText(text);

    		}
        }
    };
    
    private Action statisticalInfoAction = new AbstractAction() {

        private static final long serialVersionUID = 1L;

        public void actionPerformed(ActionEvent e) {
        	if(mdm.getCurrentDocument() != null) {
    			String text = mdm.getCurrentDocument().getTextComponent().getText();
    	        int len = text.length();
    	        int lenNonWhite = text.replaceAll("\\s+","").length();
    	        int ln = text.split("\n", -1).length;
    	        JOptionPane.showMessageDialog(
                        JNotepadPP.this,
                        "Your document has "+ len + " characters, " + lenNonWhite + " non-blank characters and " + ln + " lines.",
                        "Information",
                        JOptionPane.INFORMATION_MESSAGE);
    		}
        }
    };
	
	private void createMenus() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        fileMenu.add(new JMenuItem(openDocumentAction));
        fileMenu.add(new JMenuItem(openNewDocumentAction));
        fileMenu.addSeparator();
        fileMenu.add(new JMenuItem(saveDocumentAction));
        fileMenu.add(new JMenuItem(saveAsDocumentAction));
        fileMenu.addSeparator();
        fileMenu.add(new JMenuItem(statisticalInfoAction));
        fileMenu.addSeparator();
        fileMenu.add(new JMenuItem(closeTabAction));
        fileMenu.add(new JMenuItem(exitAction));

        JMenu editMenu = new JMenu("Edit");
        menuBar.add(editMenu);
        editMenu.add(new JMenuItem(cutAction));
        editMenu.add(new JMenuItem(copyAction));
        editMenu.add(new JMenuItem(pasteAction));
        
        JMenu caseMenu = new JMenu("Change case");
        menuBar.add(caseMenu);
        upper = new JMenuItem(toUppercaseAction);
        lower = new JMenuItem(toLowercaseAction);
        invert = new JMenuItem(invertCaseAction);
        upper.setEnabled(false);
    	lower.setEnabled(false);
    	invert.setEnabled(false);
        caseMenu.add(upper);
        caseMenu.add(lower);
        caseMenu.add(invert);

        JMenu sortMenu = new JMenu("Sort");
        menuBar.add(sortMenu);
        sortMenu.add(new JMenuItem(ascendingAction));
        sortMenu.add(new JMenuItem(descendingAction));
        sortMenu.add(new JMenuItem(uniqueAction));
 

        this.setJMenuBar(menuBar);
    }
	
	private void createToolbars() {
        JToolBar toolBar = new JToolBar("Alati");
        toolBar.setFloatable(true);

        toolBar.add(new JButton(openDocumentAction));
        toolBar.add(new JButton(openNewDocumentAction));
        toolBar.add(new JButton(saveDocumentAction));
        toolBar.add(new JButton(saveAsDocumentAction));
        toolBar.addSeparator();
        toolBar.add(new JButton(cutAction));
        toolBar.add(new JButton(copyAction));
        toolBar.add(new JButton(pasteAction));
        toolBar.addSeparator();
        toolBar.add(new JButton(statisticalInfoAction));
        toolBar.addSeparator();
        toolBar.add(new JButton(closeTabAction));
        toolBar.add(new JButton(exitAction));

        this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
    }
		
		
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
		new JNotepadPP().setVisible(true);
		});
	}
}
