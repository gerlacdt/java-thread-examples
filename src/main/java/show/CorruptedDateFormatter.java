package show;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CorruptedDateFormatter {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(CorruptedDateFormatter.class);

	private static final int NUMBER_OF_THREADS = 4;

	
	// NOT Thread-safe
	private static final DateFormat formatter = new SimpleDateFormat(
			"yyyy-MM-dd");

	public static void main(String[] args) {

		List<Thread> threads = new ArrayList<Thread>();

		for (int i = 0; i < NUMBER_OF_THREADS; i++) {
			Thread t = new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						Date date = formatter.parse("2014-11-24");
						LOGGER.info("parsed date: " + date);
					} catch (ParseException e) {
						LOGGER.error("parse error: ", e);
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
		} catch (InterruptedException e) {
			LOGGER.info("thread.join() interrupted: ", e);
		}
	}

}
