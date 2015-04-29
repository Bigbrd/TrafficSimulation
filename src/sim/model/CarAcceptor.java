package sim.model;

public interface CarAcceptor {
	public boolean accept(Car c, double frontPosition);
	public double distanceToObstacle(double fromDistance);
}
