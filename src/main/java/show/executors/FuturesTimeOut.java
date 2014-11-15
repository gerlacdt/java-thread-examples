package show.executors;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FuturesTimeOut {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(FuturesTimeOut.class);

	private static final ExecutorService pool = Executors.newCachedThreadPool();

	public static void main(String[] args) {

		Future<Integer> future = pool.submit(new Callable<Integer>() {

			@Override
			public Integer call() {
				
				// check if thread is interrupted : && !Thread.currentThread().isInterrupted()
				for (int i = 0; i < 1000000; i++) {
					LOGGER.info("looping");
				}
				return 1;
			}
		});
		
		try {
			Integer randomNumber = future.get(1, TimeUnit.SECONDS);
			LOGGER.info("randomNumber = " + randomNumber);
		} catch (InterruptedException e) {
			LOGGER.error("future.get() interrupted: " , e);
		} catch (ExecutionException e) {
			LOGGER.error("future.get() executionException: ", e);
		} catch (TimeoutException e) {
			LOGGER.error("future.get() timeout: " , e);
		} finally {
			// cancel futures, for successful futures cancellation has no impact
			future.cancel(true);
			pool.shutdown();
		}
	}

}
