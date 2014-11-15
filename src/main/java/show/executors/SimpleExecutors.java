package show.executors;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import show.CounterAtomic;

public class SimpleExecutors {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SimpleExecutors.class);
	
	// there are many ThreadPools (cached, fixed etc.)
	private static final Executor fixedExecutor = Executors.newFixedThreadPool(4);
	
	private static final CounterAtomic counter = new CounterAtomic();

	public static void main(String[] args) {
		
		fixedExecutor.execute(new Runnable() {

			@Override
			public void run() {
				counter.increment();
			}
		});
		
		// 0 ?? why?? 
		LOGGER.info("counter = " + counter.getCounter());
	}

}
