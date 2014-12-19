package show;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import show.model.Counter;
import show.model.CounterAtomic;
import show.model.CounterSync;

public class ThreadInterference {
	
	private static Logger LOGGER = LoggerFactory.getLogger(ThreadInterference.class);
	
	private static final int NUMBER_OF_THREADS = 4;
	private static final int NUMBER_OF_ITERATIONS = 10000;
	
	// create different counter types
	private static Counter counter = new Counter();
	private static CounterSync counterSync = new CounterSync();
	private static CounterAtomic counterAtomic = new CounterAtomic();

	public static void main(String[] args) {
		
		List<Thread> threads = new ArrayList<Thread>();
		
		for (int i = 0; i < NUMBER_OF_THREADS; i++) {
			Thread t = new Thread(new Runnable() {

				@Override
				public void run() {
					for (int i = 0; i < NUMBER_OF_ITERATIONS; i++) {

						counter.increment();
						counterSync.increment();
						counterAtomic.increment();
					}
				}
			});
			threads.add(t);
			t.start();
		}
		
		try {
			for (Thread t : threads) {
				t.join();
			}
			LOGGER.info("Counter = " + counter.getCounter());
			LOGGER.info("CounterSync = " + counterSync.getCounter());
			LOGGER.info("CounterAtomic = " + counterAtomic.getCounter());
		} catch (InterruptedException e) {
			LOGGER.info("thread.join() interrupted: ", e);
		}
	}
}
