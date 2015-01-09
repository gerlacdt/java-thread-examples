package show.executors;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FuturesInvokeAll {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(FuturesInvokeAll.class);

	private static final ExecutorService pool = Executors.newCachedThreadPool();

	private static final int NUMBER_OF_TASKS = 4;

	public static void main(String[] args) {

		// create callables
		List<Callable<Integer>> tasks = new ArrayList<Callable<Integer>>();

		// add long running task at first
		tasks.add(new Callable<Integer>() {

			@Override
			public Integer call() throws Exception {
				Thread.sleep(5000);
				return 1;
			}

		});

		// add short running tasks
		for (int i = 0; i < NUMBER_OF_TASKS; i++) {
			tasks.add(new Callable<Integer>() {

				@Override
				public Integer call() {
					return 42;
				}
			});
		}

		// invoke all callables at once
		List<Future<Integer>> futures = null;
		try {
			futures = pool.invokeAll(tasks);  // blocking
			LOGGER.info("after invokeAll()");
			for (Future<Integer> f : futures) {
				LOGGER.info("value of futures: " + f.get());
			}
		} catch (InterruptedException e) {
			LOGGER.error("future.get() interrupted: ", e);
		} catch (ExecutionException e) {
			LOGGER.error("future.get() executionException: ", e);
		} finally {
			// cancel all futures, for successful futures cancellation has no
			// impact
			for (Future<Integer> f : futures) {
				f.cancel(true);
			}
			pool.shutdown();
		}
	}
}
