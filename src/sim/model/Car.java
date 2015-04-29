package sim.model;

import java.awt.Color;

import sim.agent.Agent;
import sim.agent.TimeServer;
import sim.agent.TimeServerLinked;
import sim.model.Road.RoadDirection;

public class Car implements Agent {

	private static final double _INITIAL_POS = 0.0;
	private double _frontPosition;
	private double _maxVelocity;
	private double _brakeDistance;
	private double _stopDistance;
	private double _length;
	private Color _color;
	private Road _currentRoad;
        //restructure the car setup
        private Road _startRoad;
        private Road _finishRoad;
        private boolean _isStopped;
        
        private double _timeDelayed;

	Car() {
		_frontPosition = _INITIAL_POS;
		_maxVelocity = SP.CAR_MAX_VEL.getValue();
		_brakeDistance = SP.CAR_BRAKE_DIST.getValue();
		_stopDistance = SP.CAR_STOP_DIST.getValue();
		_length = SP.CAR_LENGTH.getValue();
		_color = getRandColor();
                
                _startRoad = getRandRoad();
                _finishRoad = getRandRoad();
                _isStopped = true;
                _currentRoad = _startRoad;
                
                _timeDelayed = 0.0;
	}

	private Color getRandColor() {
		return new java.awt.Color((int) Math.ceil(Math.random() * 255),
				(int) Math.ceil(Math.random() * 255), (int) Math.ceil(Math
						.random() * 255));
	}
        
        private Road getRandRoad() {
                //select random road direction
                int enumRoad = (int)Math.ceil(Math.random()*3);
                RoadDirection answer;
                switch(enumRoad) {
                case 1:
                    answer = RoadDirection.L;
                case 2:
                    answer = RoadDirection.F;
                case 3:
                    answer = RoadDirection.R;
                default:
                    answer = RoadDirection.F;
                }
                
                return new Road(new Sink(),answer);
        }

	public Road getCurrentRoad() {
		return _currentRoad;
	}

	public void setCurrentRoad(Road road) {
		_currentRoad = road;
	}
        
        public Road getStartRoad() {
		return _startRoad;
	}

	public void setStartRoad(Road road) {
		_startRoad = road;
	}
        
        public Road getFinishRoad() {
		return _finishRoad;
	}

	public void setFinishRoad(Road road) {
		_finishRoad = road;
	}

	public double getFrontPosition() {
		return _frontPosition;
	}

	public void setFrontPosition(double frontPosition) {
		_frontPosition = frontPosition;
	}

	public double getBackPosition() {
		return _frontPosition - _length;
	}

	public Color getColor() {
		return _color;
	}

	public double getLength() {
		return _length;
	}

	private double newVelocity(double closestObstacle) {
		return (_maxVelocity / (_brakeDistance - _stopDistance))
				* (closestObstacle - _stopDistance);
	}
        
        public boolean isStopped(){
            return _isStopped;

        }
        public void setStopped(boolean stopped){
            _isStopped = stopped;
        }
        
        public void setTimeDelayed(double timeDelay){
            _timeDelayed= timeDelay;
        }
        public double getTimeDelayed(){
            return _timeDelayed;
        }
        
	public void run() {
		double velocity = 0;
		double closestObstacle = getCurrentRoad().distanceToObstacle(getFrontPosition());
                //_isStopped = true;
                
		if ((closestObstacle < _maxVelocity)
				&& (closestObstacle > _brakeDistance)) {
			velocity = newVelocity(closestObstacle);
                        //_isStopped = false;
		} else if (closestObstacle <= _stopDistance) {
                        
			velocity = 0;
		} else if (closestObstacle > _maxVelocity) {
			velocity = _maxVelocity;
                        //_isStopped = false;
		}

		_frontPosition += velocity * SP.TIME_STEP.getValue();
		getCurrentRoad().accept(this, _frontPosition);
	}

}
