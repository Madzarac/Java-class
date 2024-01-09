package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * PrimListModel
 * @author TeaMadzarac
 *
 */
public class PrimListModel implements ListModel {
	
	private List<Integer> elementi = new ArrayList<>();
	private List<ListDataListener> promatraci = new ArrayList<>();
	
	/**
	 * konstruktor
	 */
	public PrimListModel() {
		elementi.add(1);
	}


	@Override
	public int getSize() {
		return elementi.size();
	}

	@Override
	public Integer getElementAt(int index) {
		return elementi.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		promatraci.add(l);	
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		promatraci.remove(l);
	}
	
	/**
	 * dodaje sljedeci prim broj u elemente
	 */
	public void next() {
		
		boolean divided = true;
		int num = elementi.get(getSize()-1) + 2;
		if(getSize() == 1) {
			num = 2;
			divided = false;
		}
		if(getSize() == 2) {
			num = 3;
			divided = false;
		}
		while(divided) {
			divided = false;
			for(int i = 1; i < getSize() && !divided; i++) {
				if(num % elementi.get(i) == 0) {
					divided = true;
				}
			}
			if(divided) {
				num += 2;
			}
		}
		elementi.add(num);
		
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, getSize(), getSize());
		for(ListDataListener l : promatraci) {
			l.intervalAdded(event);
		}
	}

}
