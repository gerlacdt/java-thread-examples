package show;

public class CounterSync {
	
	private int counter;

	public synchronized int getCounter() {
		return counter;
	}

	public synchronized void increment() {
		this.counter++;
	}

}
