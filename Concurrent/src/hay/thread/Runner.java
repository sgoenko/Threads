package hay.thread;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class Runner {
	public static void main(String[] args) throws InterruptedException {
		ExecutorService executor = Executors.newWorkStealingPool();

		List<Callable<String>> callables = Arrays.asList(
				() -> "task1",
				() -> "task2",
				() -> "task3");

		executor.invokeAll(callables)
				.stream()
				.map(future -> {
					try {
						return future.get();
					} catch (Exception e) {
						throw new IllegalStateException(e);
					}
				})
				.forEach(System.out::println);

	}
}
