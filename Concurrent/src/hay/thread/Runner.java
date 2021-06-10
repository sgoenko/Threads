package hay.thread;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class Runner {

	static Callable callable(String result, long sleepSeconds) {
		return () -> {
			TimeUnit.SECONDS.sleep(sleepSeconds);
			return result;
		};
	}

	public static void main(String[] args) throws InterruptedException {
		ExecutorService executor = Executors.newWorkStealingPool();


		List<Callable<String>> callables = Arrays.asList(
				callable("task1", 2),
				callable("task2", 1),
				callable("task3", 3)
		);

		String result = null;
		try {
			result = executor.invokeAny(callables);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		System.out.println(result);

	}
}
