package cheese.squeeze.structures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LinkedList<T> {

	private int size;
	private T[] elements;
	
	@SuppressWarnings("unchecked")
	public LinkedList(int size) {
		this.size = size;
		this.elements = (T[]) new Object[size];
	}
	
	public boolean addElement(T element,int row) {
		if (row > 1 && row < size) {
			if(elements[row-1] == null && elements[row-2] == null && elements[row] == null){
				elements[row-1] = element;
				return true;
			}
		}
		else if (row == size) {
			if(elements[row-1] == null && elements[row-2] == null){
				elements[row-1] = element;
				return true;
			}
		}
		else if (row == 1 ) {
			if(elements[row] == null && elements[row-1] == null){
				elements[row-1] = element;
				return true;
			}
		}

		return false;
	}
	
	public List<T> getElements() {
		ArrayList<T> list = new ArrayList<T>();
		for(T el : elements) {
			if(el != null) {
				list.add(el);
			}
		}
		return list;
	}
	
	public int getSize() {
		return this.size;
	}

	public boolean isValidPosition(int row) {
		if (row > 1 && row < size) {
			if(elements[row-1] == null && elements[row-2] == null && elements[row] == null){
				return true;
			}
		}
		else if (row == size) {
			if(elements[row-1] == null && elements[row-2] == null){
				return true;
			}
		}
		else if (row == 1) {
			//TODO bugg
			if(elements[row] == null && elements[row-1] == null){
				return true;
			}
		}

		return false;
	}
	
	public String toString() {
		String s = "[";
		for(T el : elements) {
			s += el == null ?  "null," : el.toString() +",";
		}
		return s+"]";
	}
	
}
