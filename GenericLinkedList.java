package il.co.ilrd.genericlinkedlist;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class GenericLinkedList<T> implements Iterable<T> {
	private Node head;
	private int modCount;//fail-fast iterator var
	
	public void pushFront(T data) {
		head = new Node(data, head);
		++modCount;
	}
	
	public T popFront() {
		assert(null != head);
		T poppedData = head.data;
		head = head.next;
		++modCount;
		
		return (poppedData);
	}
	
	public long getSize(){
		long counter = 0;
		
		ListIterator iter = new ListIterator(head);
		while (iter.hasNext()) {
			++counter;
			iter.next();
		}
		return (counter);
	}
	
	public boolean isEmpty(){
		return (null == head);
	}
	
	public Iterator<T> getBegin() {
		ListIterator iter = new ListIterator(head);
		
		return (iter);
	}
	
	public Iterator<T> find(T toFind){
		ListIterator iter = new ListIterator(head);
		
		for(T t : this) {
			if (toFind.equals(t))
			{
				return (iter);
			}
			iter.next();
		}
		
		return (iter);
	}
	
	public Iterator<T> iterator() {
		return new ListIterator(head);
	}
	
	private class Node {
		private T data;
		private Node next;
		
		private Node(T data, Node next) {
			this.data = data;
			this.next = next;
		}
	}
	
	private class ListIterator implements Iterator<T> {
		private Node node;
		private int expectedModCount;//fail fast iterator var
		
		private ListIterator(Node node) {
			this.node = node;
			expectedModCount = modCount;
		}
		@Override
		public boolean hasNext() {
			return (null != this.node);
		}
		@Override
		public T next() throws ConcurrentModificationException {
			if (modCount != expectedModCount) {
				throw new ConcurrentModificationException();
			}
			T data = node.data;
			node = node.next;
			
			return (data);
		}
	}
	
	public static <E> GenericLinkedList<E> newReverse(GenericLinkedList<E> list) {
		GenericLinkedList<E> reversedList = new GenericLinkedList<E>();

		for (E element : list) {
			reversedList.pushFront(element);
		}
		
		return (reversedList);
	}
	
	public static <E> GenericLinkedList<E> merge(GenericLinkedList<E> list1, GenericLinkedList<E> list2){
		GenericLinkedList<E> mergedList = new GenericLinkedList<E>();
		
		for (E element : list2) {
			mergedList.pushFront(element);
		}
		for (E element : list1) {
			mergedList.pushFront(element);
		}
		
		return newReverse(mergedList);
	}
}
