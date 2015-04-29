package sim.model;

/**
 * End of the road that removes cars from the road
 * 
 * @author rodri_000
 *
 */
public final class Sink implements CarAcceptor {

	public double distanceToObstacle(double fromPosition) {
		return Double.POSITIVE_INFINITY;
	}

	public boolean accept(Car c, double d) {
		if (d == Double.POSITIVE_INFINITY)
			return true;
		return false;
	}
}
