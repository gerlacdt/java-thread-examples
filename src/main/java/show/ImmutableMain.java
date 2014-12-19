package show;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import show.model.MultiCounter;
import show.model.MultiCounterImmutable;

public class ImmutableMain {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ImmutableMain.class);

	private static final int NUMBER_OF_THREADS = 4;
	private static final int NUMBER_OF_ITERATIONS = 10000;
	
	private static MultiCounter multiCounter = new MultiCounter();
	private static MultiCounterImmutable multiCounterImmutable = new MultiCounterImmutable(0,0,0,0);

	public static void main(String[] args) {
		
		List<Thread> threads = new ArrayList<Thread>();
		
		for (int i = 0; i < NUMBER_OF_THREADS; i++) {
			Thread t = new Thread(new Runnable() {

				@Override
				public void run() {
					for (int i = 0; i < NUMBER_OF_ITERATIONS; i++) {
						multiCounter.incrementAll();
						
						// not thread safe!!
						multiCounterImmutable = multiCounterImmutable.incrementAll();
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
			LOGGER.info("Counter first = " + multiCounter.getFirst());
			LOGGER.info("Counter second = " + multiCounter.getSecond());
			LOGGER.info("Counter third = " + multiCounter.getThird());
			LOGGER.info("Counter fourth = " + multiCounter.getFourth());
			
			LOGGER.info("\n\n-------------------------------------------------------------------------------------\n");
			
			LOGGER.info("ImmutableCounter first = " + multiCounterImmutable.getFirst());
			LOGGER.info("ImmutableCounter second = " + multiCounterImmutable.getSecond());
			LOGGER.info("ImmutableCounter third = " + multiCounterImmutable.getThird());
			LOGGER.info("ImmutableCounter fourth = " + multiCounterImmutable.getFourth());
		} catch (InterruptedException e) {
			LOGGER.info("thread.join() interrupted: ", e);
		}
	}

}
