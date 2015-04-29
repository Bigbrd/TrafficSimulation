package sim.model;

/**
 * Static class used to set parameters and print them out
 * 
 * @author rodri_000
 *
 */
public class SimParameters {
	private SimParameters() {
	}

	static public void setTimeStep(double time) {
		SP.TIME_STEP.setValue(time);
	}

	static public void setRuntime(double time) {
		SP.RUN_TIME.setValue(time);
	}

	static public void setDimension(int[] dim) {
		SP.DIMENSION.setValue(dim);
	}

	static public void setPattern(String pattern) {
		if (pattern.equals("alternating"))
			SP.PATTERN.setValue(true);
		else
			SP.PATTERN.setValue(false);
	}

	static public void setCarGenDelay(double[] range) {
		SP.CAR_GEN_DELAY.setValue(range);
	}

	static public void setRoadLength(double[] range) {
		SP.ROAD_LENGTH.setValue(range);
	}

	static public void setIntersectionLength(double[] range) {
		SP.INTER_LENGTH.setValue(range);
	}

	static public void setCarLength(double[] range) {
		SP.CAR_LENGTH.setValue(range);
	}

	static public void setCarMaxVelocity(double[] range) {
		SP.CAR_MAX_VEL.setValue(range);
	}

	static public void setCarStopDistance(double[] range) {
		SP.CAR_STOP_DIST.setValue(range);
	}

	static public void setCarBrakeDistance(double[] range) {
		SP.CAR_BRAKE_DIST.setValue(range);
	}

	static public void setTrafficGreenTime(double[] range) {
		SP.TRAFFIC_GTIME.setValue(range);
	}

	static public void setTrafficYellowTime(double[] range) {
		SP.TRAFFIC_YTIME.setValue(range);
	}

	static public void resetParameters() {
		SP.TIME_STEP.setValue(0.1);
		SP.RUN_TIME.setValue(1000.0);
		SP.DIMENSION.setValue(new int[] { 2, 3 });
		SP.PATTERN.setValue(true);
		SP.CAR_GEN_DELAY.setValue(new double[] { 5.0, 25.0 });
		SP.ROAD_LENGTH.setValue(new double[] { 200.0, 500.0 });
		SP.INTER_LENGTH.setValue(new double[] { 10.0, 15.0 });
		SP.CAR_LENGTH.setValue(new double[] { 5.0, 10.0 });
		SP.CAR_MAX_VEL.setValue(new double[] { 10.0, 30.0 });
		SP.CAR_STOP_DIST.setValue(new double[] { 0.5, 5.0 });
		SP.CAR_BRAKE_DIST.setValue(new double[] { 9.0, 10.0 });
		SP.TRAFFIC_GTIME.setValue(new double[] { 10.0, 11.0 });
		SP.TRAFFIC_YTIME.setValue(new double[] { 4.0, 5.0 });
	}

	static public String printParameters() {
		StringBuilder builder = new StringBuilder();
		for (SP p : SP.values()) {
			if (p != SP.CAR_WIDTH_UI || p != SP.ROAD_LENGTH_UI)
				builder.append(p.getTitle() + ": ");
			builder.append(p.toString());
			builder.append("\n");
		}
		return builder.toString();
	}

}
