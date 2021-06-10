package hay.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class Runner {

	ReentrantLock lock = new ReentrantLock();
	int count = 0;

	void increment() {
		lock.lock();
		try {
			count++;
		} finally {
			lock.unlock();
		}
	}

	public void run() throws InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(5);

		IntStream.range(0, 10000)
				.forEach(i -> executor.submit(this::increment));

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
