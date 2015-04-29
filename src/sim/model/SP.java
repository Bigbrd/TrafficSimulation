package sim.model;

import sim.util.Util;

/**
 * Simulation Parameters used throughout the simulation
 * 
 * @author rodri_000
 *
 */
public enum SP {
	TIME_STEP("Simulation time step (seconds)", 0.1), 
	RUN_TIME("Simulation run time (seconds)", 1000.0), 
	DIMENSION("Grid size (number of roads)", 1, 1), 
	PATTERN("Traffic pattern", true), 
	CAR_GEN_DELAY("Car entry rate (seconds/car)", 5.0, 25.0), 
	ROAD_LENGTH("Road segment length (meters)", 200.0, 500.0), 
	ROAD_LENGTH_UI("Road UI Length", 300.0), 
	INTER_LENGTH("Intersection length (meters)", 10.0, 15.0), 
	CAR_LENGTH("Car length (meters)", 5.0, 10.0), 
	CAR_WIDTH_UI("Car UI width", 15.0), 
	CAR_MAX_VEL("Car maximum velocity (meters/second)", 10.0, 30.0), 
	CAR_STOP_DIST("Car stop distance (meters)", 0.5, 5.0), 
	CAR_BRAKE_DIST("Car brake distance (meters)", 9.0, 10.0), 
	TRAFFIC_GTIME("Traffic light green time (seconds)", 10.0, 11.0), 
	TRAFFIC_YTIME("Traffic light yellow time (seconds)", 4.0, 5.0);

	private static final double INIT_VAL = 0.0;
	private String _title;
	private double _value;
	private int[] _dimension = { 1, 1 };
	private boolean _isAlternating;
	private double[] _range = { 100, 200 };

	// Used for simulation time step and run time
	SP(String title, double value) {
		_title = title;
		_value = value;
		_dimension = null;
		_isAlternating = false;
		_range = null;
	}

	// Used for grid size
	SP(String title, int row, int col) {
		_title = title;
		_dimension[0] = row;
		_dimension[1] = col;
		_value = INIT_VAL;
		_isAlternating = false;
		_range = null;
	}

	// Used for traffic pattern
	SP(String title, boolean isAlternating) {
		_title = title;
		_isAlternating = isAlternating;
		_value = INIT_VAL;
		_dimension = null;
		_range = null;
	}

	// Used for random values
	SP(String title, double min, double max) {
		_title = title;
		_range[0] = min;
		_range[1] = max;
		_value = INIT_VAL;
		_dimension = null;
		_isAlternating = false;
	}

	public String getTitle() {
		return _title;
	}

	public double getValue() {
		if (_value == INIT_VAL)
			return Util.nextRandom(_range[0], _range[1]);
		else
			return _value;
	}

	public double[] getRange() {
		return _range;
	}

	public boolean getPatternValue() {
		return _isAlternating;
	}

	public int[] getDimValue() {
		return _dimension;
	}

	public void setValue(double value) {
		_value = value;
	}

	public void setValue(double[] range) {
		_range = range;
	}

	public void setValue(boolean pattern) {
		_isAlternating = pattern;
	}

	public void setValue(int[] dimension) {
		_dimension = dimension;
	}

	public String toString() {
		if (_dimension != null)
			return "[" + _dimension[0] + ", " + _dimension[1] + "]";
		else if (_range != null)
			return "[" + _range[0] + ", " + _range[1] + "]";
		else if (_value != INIT_VAL)
			return "[" + _value + "]";
		else {
			if (_isAlternating)
				return "[alternating]";
			else
				return "[simple]";
		}
	}
}
