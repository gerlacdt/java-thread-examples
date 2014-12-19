package show.model;

import java.util.concurrent.atomic.AtomicInteger;

public class CounterAtomic {
	
	private AtomicInteger counter;
	
	public CounterAtomic() {
		this.counter = new AtomicInteger();
	}

	public AtomicInteger getCounter() {
		return counter;
	}

	public void increment() {
		counter.incrementAndGet();
	}

}
