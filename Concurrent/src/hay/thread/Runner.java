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


		Callable task2 = () -> {
			try {
				TimeUnit.SECONDS.sleep(2);
				return 123;
			}
			catch (InterruptedException e) {
				throw new IllegalStateException("task interrupted", e);
			}
		};
		ExecutorService executor2 = Executors.newFixedThreadPool(1);
		Future<Integer> future = executor2.submit(task2);

		System.out.println("future done? " + future.isDone());

		Integer result = null;

		try {
			result = future.get(1, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}

		System.out.println("future done? " + future.isDone());
		System.out.print("result: " + result);

	}
}
