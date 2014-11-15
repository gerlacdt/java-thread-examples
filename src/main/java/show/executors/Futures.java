package show.executors;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Futures {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(Futures.class);

	private static final ExecutorService pool = Executors.newCachedThreadPool();

	public static void main(String[] args) {

		Future<Integer> future = pool.submit(new Callable<Integer>() {

			@Override
			public Integer call() {
				return 42;
			}
		});
		Integer randomNumber;
		try {
			randomNumber = future.get();
			LOGGER.info("randomNumber = " + randomNumber);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			pool.shutdown();
		}
	}
}
