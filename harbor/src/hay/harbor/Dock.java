package hay.harbor;

public class Dock {
	private int id;
	private Harbor harbor;
	
	public Dock(int id, Harbor harbor) {
		this.id = id;
		this.harbor = harbor;
	}

	public int getId() {
		return id;
	}

	public boolean load() {
		try {
			if (harbor.sent()) {
				Thread.sleep(50);
				return true;
			} else {
				return false;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean upload() {
		try {
			if (harbor.received()) {
				Thread.sleep(50);
				return true;
			} else {
				return false;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}

}