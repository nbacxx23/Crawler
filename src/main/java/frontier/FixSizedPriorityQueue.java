package frontier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class FixSizedPriorityQueue<E extends Comparable>{
	private PriorityQueue<E> queue;
	private int maxSize; 

	public FixSizedPriorityQueue(int maxSize) {
		if (maxSize <= 0)
			throw new IllegalArgumentException();
		this.maxSize = maxSize;
		this.queue = new PriorityQueue(maxSize, new Comparator<E>() {
			public int compare(E o1, E o2) {
				return (o2.compareTo(o1));
			}
		});
	}

	public void add(E e) {
		if (queue.size() < maxSize) { 
			queue.add(e);
		} else {
			E peek = queue.peek();
			if (e.compareTo(peek) < 0) {
				queue.poll();
				queue.add(e);
			}
		}
	}
	public E poll(){
		return queue.poll();
	}
	public List<E> sortedList() {
		List<E> list = new ArrayList<E>(queue);
		Collections.sort(list); 
		return list;
	}

	public PriorityQueue<E> getQueue() {
		return queue;
	}

	public void setQueue(PriorityQueue<E> queue) {
		this.queue = queue;
	}

	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	public int size(){
		return this.queue.size();
	}
}
