package sim.model;

import java.util.Random;
import java.util.Set;
import sim.agent.Agent;
import sim.agent.TimeServer;
import sim.agent.TimeServerLinked;

/**
 * Starting point of the road that creates Cars between the specified random
 * intervals
 * 
 * @author rodri_000
 *
 */
public final class Source implements Agent {

	private CarAcceptor _next;
        private Set<CarAcceptor> _roads;
	private TimeServer _time;

	//Source(CarAcceptor) {
	//	_next = ca;
        Source(Set<CarAcceptor> roads){
                _roads = roads;
		_time = TimeServerLinked.getInstance();
		enqueue();
	}

	private void enqueue() {
		_time.enqueue(_time.currentTime() + SP.CAR_GEN_DELAY.getValue(), this);

	}

	public void run() {
		Car c = CarFactory.createCar();
                //randomize the car acceptor
                //create the path using Djikstras algorithm,
                //next road needs to be just a pop off from the path based on where its going next
                
                _next = getRandRoad();
		if (_next.accept(c, c.getFrontPosition()) && _next.distanceToObstacle(c.getFrontPosition()) > c.getFrontPosition()) {
                        c.setTimeDelayed(_time.currentTime());
			_time.enqueue(_time.currentTime() + SP.CAR_GEN_DELAY.getValue(),
					this);
		}
	}
        
        private CarAcceptor getRandRoad() {
                //select random road direction
                int size = _roads.size();
                int item = new Random().nextInt(size); // In real life, the Random object should be rather more shared than this
                int i = 0;
                for(CarAcceptor obj : _roads)
                {
                    if (i == item)
                        return obj;
                    i = i + 1;
                }
                return null;
        }
//            
//                int enumRoad = (int)Math.ceil(Math.random()*3);
//                Road.RoadDirection answer;
//                switch(enumRoad) {
//                case 1:
//                    answer = Road.RoadDirection.L;
//                case 2:
//                    answer = Road.RoadDirection.F;
//                case 3:
//                    answer = Road.RoadDirection.R;
//                default:
//                    answer = Road.RoadDirection.F;
//                }
//                
//                return new Road(new Sink(),answer);
//        }
}
