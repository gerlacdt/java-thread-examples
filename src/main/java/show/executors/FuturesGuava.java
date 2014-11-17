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

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.Futures;

public class FuturesGuava {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(FuturesGuava.class);

	private static final ExecutorService pool = Executors.newCachedThreadPool();
	
	// improve standard threadpool with guava
	public static final ListeningExecutorService guavaPool = MoreExecutors.listeningDecorator(pool);

	private static final int NUMBER_OF_TASKS = 10;
	
	public static void main(String[] args) {
		
		// create callables
		List<Callable<Integer>> tasks = new ArrayList<Callable<Integer>>();

		for (int i = 0; i < NUMBER_OF_TASKS; i++) {
			tasks.add(new Callable<Integer>() {

				@Override
				public Integer call() {
					return 42;
				}
			});
		}
		
		List<ListenableFuture<Integer>> futures = null;
		
		try {
			futures = (List) guavaPool.invokeAll(tasks);
			
			for (ListenableFuture<Integer> f : futures) {
				Futures.addCallback(f, new FutureCallback<Integer>() {
					
					@Override
					public void onFailure(Throwable arg0) {
						LOGGER.warn("failure during future execution");
					}
					
					@Override
					public void onSuccess(Integer arg0) {
						LOGGER.info("number = " + arg0);
					}
					
				});
			}
		} catch (InterruptedException e) {
			LOGGER.error("invokeAll() interrupted: ", e);
		} finally {
			// cancel all futures, for successful futures cancellation has no impact
			for (Future<Integer> f : futures) {
				f.cancel(true);
			}
			 guavaPool.shutdown();
		}
	}

}
