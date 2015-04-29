package sim.model;

/**
 * Light that sets the flow for the traffic
 * 
 * @author rodri_000
 *
 */
public class Light implements CarAcceptor {
	
        //Enum used to control which light this is
        public enum LightDirection {
            L, F, R
        }

        // Enum used to control traffic
	public enum FlowIndicator {
		GO, CAUTION, STOP
	}

        public int queueCarCount;
        public int successCarCount;
        private LightDirection _directionLight;
	private FlowIndicator _state;
	private CarAcceptor _nextRoad;

	Light(CarAcceptor ca) {
		_nextRoad = ca;
                queueCarCount = 0;
                successCarCount = 0;
	}

        public LightDirection getLightDirection() {
            return _directionLight;
        }
        
	public FlowIndicator getFlow() {
		return _state;
	}

        public void setLightDirection(LightDirection ld) {
		_directionLight = ld;
	}
        
	public void setState(FlowIndicator f) {
		_state = f;
	}

        //TODO: change this up too to add light direction.
	public double distanceToObstacle(double fromPosition) {
		if (_state == FlowIndicator.GO || _state == FlowIndicator.CAUTION) {
                        
			return _nextRoad.distanceToObstacle(fromPosition);
		} else {
			return 0.0;
		}
	}

        //TODO: add logic here for the specific lights
	public boolean accept(Car c, double frontPosition) {
                if(c.isStopped())
                {
                    queueCarCount++;
                }
                successCarCount++;
		if (_state == FlowIndicator.GO || _state == FlowIndicator.CAUTION) {
                        //queueCarCount--;
			return _nextRoad.accept(c, frontPosition);
		} else {
                    
			return false;
		}
	}
}
