package user.util;

import java.util.LinkedList;
import java.util.List;

public class MyQueue<T> {
	private List<T> queue;
	private int maxSize=-1;
	
	public MyQueue(){
		queue=new LinkedList<T>();
	}
	
	public MyQueue(int maxsize){
		queue=new LinkedList<T>();
		maxSize=maxsize;
	}
	
	public synchronized T get(){
		try {
			while(queue.size()<=0){
				this.wait();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return queue.remove(0);
	}
	
	
	public synchronized T get(int timeOut){
		long start=System.currentTimeMillis();
		try {
			while(queue.size()<=0&&(System.currentTimeMillis()-start)<timeOut){
				this.wait(timeOut);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(queue.size()<=0){
			return null;
		}
		return queue.remove(0);
	}

	public synchronized void add(T o){
		if(queue.size()<maxSize||maxSize<0){
				queue.add(o);
		}else{
			System.out.println("Queue is too long than "+maxSize+".");
		}
		this.notifyAll();
	}
	
	public synchronized void addAll(List<T> o,int priority){
		if(queue.size()<maxSize||maxSize<0){
				queue.addAll(o);
		}else{
			System.out.println("Queue is too long than "+maxSize+".");
		}
		this.notifyAll();
	}
	
	public int size(){
		return queue.size();
	}
	
	public synchronized void clear(){
		queue.clear();
	}
	
	public boolean contains(T o){
		return queue.contains(o);
	}
}
