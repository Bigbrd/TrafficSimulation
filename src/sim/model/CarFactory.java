package sim.model;

public class CarFactory {
	private CarFactory() {
	}

	static public Car createCar() {
		return new Car();
	}

}
