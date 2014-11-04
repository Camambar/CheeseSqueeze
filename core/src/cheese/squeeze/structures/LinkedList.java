package cheese.squeeze.structures;

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
		if(elements[row-1] == null && elements[row-2] == null && elements[row] == null){
			elements[row-1] = element;
			return true;
		}
		return false;
	}
	
	public List<T> getElements() {
		return Arrays.asList(elements);
	}
	
	public int getSize() {
		return this.size;
	}
	
}
