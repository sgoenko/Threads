package hay.thread;

import java.util.concurrent.*;

public class Runner {
	public static void main(String[] args) {
		Runnable task1 = () -> {
			String threadName = Thread.currentThread().getName();
			System.out.println("Hello " + threadName);
		};
		ExecutorService executor1 = Executors.newSingleThreadExecutor();

		executor1.submit(task1);

		try {
			System.out.println("attempt to shutdown executor");
			executor1.shutdown();
			executor1.awaitTermination(5, TimeUnit.SECONDS);
		}
		catch (InterruptedException e) {
			System.err.println("tasks interrupted");
		}
		finally {
			if (!executor1.isTerminated()) {
				System.err.println("cancel non-finished tasks");
			}
			executor1.shutdownNow();
			System.out.println("shutdown finished");
		}


	}
}
