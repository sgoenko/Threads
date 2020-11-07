package hay.harbor;

public class DockException extends Exception {
	public DockException() {
		super();
	}

	public DockException(String message, Throwable cause) {
		super(message, cause);
	}

	public DockException(String message) {
		super(message);
	}

	public DockException(Throwable cause) {
		super(cause);
	}
}
