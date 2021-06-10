package hay.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Runner {
	int count = 0;

	void increment() {
		count = count + 1;
	}

	synchronized void syncIncrement() {
		count = count + 1;
	}

	public void run() throws InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(2);

		IntStream.range(0, 10000)
				.forEach(i -> executor.submit(this::syncIncrement));

		ConcurrentUtils.stop(executor);

		System.out.println(count);  // 9965
	}

	public static void main(String[] args) {
		Runner runner = new Runner();
		try {
			runner.run();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
