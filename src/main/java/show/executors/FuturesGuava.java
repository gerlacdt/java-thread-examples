package show.executors;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

public class FuturesGuava {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(FuturesGuava.class);

	private static final ExecutorService pool = Executors.newCachedThreadPool();
	
	// improve standard threadpool with guava
	public static final ListeningExecutorService guavaPool = MoreExecutors.listeningDecorator(pool);

	private static final int NUMBER_OF_TASKS = 3;
	
	private static final CountDownLatch latch = new CountDownLatch(1);
	
	public static void main(String[] args) throws InterruptedException {
		
		// create callables
		List<Callable<Integer>> tasks = new ArrayList<Callable<Integer>>();
		
		
		// add long running task
		tasks.add(new Callable<Integer>() {

			@Override
			public Integer call() throws Exception {
				Thread.sleep(5000);
				latch.countDown();
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
		
		List<ListenableFuture<Integer>> futures = new ArrayList<ListenableFuture<Integer>>();
		
		try {
			for (Callable<Integer> t : tasks) {
				futures.add(guavaPool.submit(t));
			}
			
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
		} finally {
			// wait as long as futures run (so main thread does not exit)
			latch.await();
			
			// cancel all futures, for successful futures cancellation has no impact
			for (Future<Integer> f : futures) {
				f.cancel(true);
			}
			 guavaPool.shutdown();
		}
	}



}
