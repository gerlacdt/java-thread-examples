package show.executors;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FutureCompletionService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(FutureCompletionService.class);

	private static final ExecutorService pool = Executors.newCachedThreadPool();

	private static final int NUMBER_OF_TASKS = 4;

	public static void main(String[] args) {

		ExecutorCompletionService<Integer> ecs = new ExecutorCompletionService<Integer>(
				pool);

		List<Future<Integer>> futures = new ArrayList<Future<Integer>>();

		// add long running task at first
		futures.add(ecs.submit(new Callable<Integer>() {

			@Override
			public Integer call() throws Exception {
				Thread.sleep(2000);
				return 1;
			}

		}));

		// add short running task at last
		for (int i = 0; i < NUMBER_OF_TASKS; i++) {
			futures.add(ecs.submit(new Callable<Integer>() {

				@Override
				public Integer call() {
					return 42;
				}
			}));
		}

		try {

			for (int i = 0; i < NUMBER_OF_TASKS + 1; i++) {
				Integer result = ecs.take().get(); // blocks only as long as the
													// first result is ready
				LOGGER.info("value of futures: " + result);
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
