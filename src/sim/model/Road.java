package sim.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import sim.agent.TimeServer;
import sim.agent.TimeServerLinked;

public class Road implements CarAcceptor {

    

        //Enum used to control which light this is
        public enum RoadDirection {
            L, F, R
        }
        
	private Set<Car> _cars;
	private TimeServer _time;
	private double _endPosition;
	private CarAcceptor _next;
        private RoadDirection _directionRoad;
        public int queueCarCount;
        public List<Double> _carDelays;
        

	Road(CarAcceptor ca, RoadDirection dr) {
		_next = ca;
		_cars = new HashSet<Car>();
		_endPosition = SP.ROAD_LENGTH.getValue();
		_time = TimeServerLinked.getInstance();
                _directionRoad = dr;
                queueCarCount = 0;
                _carDelays = new ArrayList<>();
	}
        
        public RoadDirection getRoadDirection() {
            return _directionRoad;
        }
        
        public void setRoadDirection(RoadDirection rd){
            _directionRoad = rd;
        }

	public List<Car> getCars() {
		return new ArrayList<Car>(_cars);
	}
        
        public int getNumberOfStoppedCars(){
            int stoppedCount = 0;
            List<Car> carsOnRoad = getCars();
            for(Car c: carsOnRoad){
                if(c.isStopped())
                {
                    stoppedCount++;
                }
            }
            return stoppedCount;
        }

	public double getLength() {
		return _endPosition;
	}

	/**
	 * USED FOR TESTING PURPOSES
	 * 
	 * @return
	 */
	public CarAcceptor getNext() {
		return _next;
	}

	/**
	 * USED FOR TESTING PURPOSES
	 * 
	 * @param Length l
	 */
	public void setLength(double l) {
		_endPosition = l;
	}

	private double distanceToCarBack(double fromPosition) {
		double carBackPosition = Double.POSITIVE_INFINITY;
		for (Car c : _cars) {
			if (c.getBackPosition() >= fromPosition
					&& c.getBackPosition() < carBackPosition)
				carBackPosition = c.getBackPosition();
		}
		return carBackPosition;
	}

	public double distanceToObstacle(double fromPosition) {
		double obstaclePosition = this.distanceToCarBack(fromPosition);
		if (obstaclePosition == Double.POSITIVE_INFINITY) {
			double distanceToEnd = Math.abs(_endPosition - fromPosition);
			if (_next != null) {
				obstaclePosition = (_next).distanceToObstacle(Math
						.abs(_endPosition - -fromPosition));
				if (obstaclePosition == 0.0) {
					return distanceToEnd;
				}
			} else {
				return distanceToEnd;
			}
		}

		return obstaclePosition - fromPosition;
	}

	public boolean accept(Car c, double frontPosition) {
		_cars.remove(c);
		if (frontPosition > _endPosition) {//next light/road takes it
                    _carDelays.add(_time.currentTime() - c.getTimeDelayed());
			if (_next != null) {
				return _next.accept(c, frontPosition - _endPosition);
			} else {
                            //car is off the grid
                                //_carDelays.add(_time.currentTime() - c.getTimeDelayed());
                                //c.setTimeDelayed(_time.currentTime() - c.getTimeDelayed());
				return false;
			}
		} else {
			c.setCurrentRoad(this);
                        if(c.getFrontPosition() == frontPosition){
                            //c didnt move so in the queue
                            c.setStopped(true);
                            //queueCarCount++;
                        }
                        else
                        {
                            c.setStopped(false);
                            //queueCarCount--;
                        }
			c.setFrontPosition(frontPosition);
			_cars.add(c);
			_time.enqueue(_time.currentTime() + SP.TIME_STEP.getValue(), c);
			return true;
		}
	}
}
