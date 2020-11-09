package hay.harbor;

public class HarborRunner {
	public static void main(String[] args) {
		Harbor harbor = new Harbor(50, 50, 3);
		
		Ship Mary = new Ship(harbor, "<Mary>", 10, 10);
		Mary.setAction(ServiceType.UPLOAD);
		
		Ship Star = new Ship(harbor, "<Star>", 12, 10);
		Star.setAction(ServiceType.UPLOAD);

		Ship Calipso = new Ship(harbor, "<Calipso>", 10, 5);
		Calipso.setAction(ServiceType.LOAD);
		
		Ship Guron = new Ship(harbor, "<Guron>", 10, 0);
		Guron.setAction(ServiceType.LOAD);
		
		Ship Victory = new Ship(harbor, "<Victory>", 15, 0);
		Victory.setAction(ServiceType.LOAD);
		
		Mary.start();
		Calipso.start();
		Guron.start();
		Star.start();
		Victory.start();

		Mary.setAction(ServiceType.LOAD);
	}
}
