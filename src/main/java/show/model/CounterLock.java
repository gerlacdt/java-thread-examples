package show.model;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CounterLock {

	private Lock lock = new ReentrantLock();

	private int counter;

	public int getCounter() {
		return counter;
	}

	public void increment() {
		try {
			lock.lock();
			this.counter++;
		} finally {
			// always unlock
			lock.unlock();
		}
	}
}
