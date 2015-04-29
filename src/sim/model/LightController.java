package sim.model;

import sim.agent.Agent;
import sim.agent.TimeServer;
import sim.agent.TimeServerLinked;
import sim.model.Light.FlowIndicator;
import sim.model.Light.LightDirection;

/**
 * Controller that controls the traffic lights using random values
 * 
 * @author rodri_000
 *
 */
public class LightController implements Agent {

        //Enum used to control which light this is
        
	public enum Direction {
		EAST, WEST, NORTH, SOUTH
	}
        
        //tweak here for the simulation
        public enum Schedule {
                A, B, C, D, E, F
        }

	Light _eastLight_L;
        Light _eastLight_F;
        Light _eastLight_R;
        Light _westLight_L;
        Light _westLight_F;
        Light _westLight_R;
	Light _northLight_L;
        Light _northLight_F;
	Light _northLight_R;
        Light _southLight_L;
        Light _southLight_F;
        Light _southLight_R;
        
	private TimeServer _time;

	LightController() {
		_time = TimeServerLinked.getInstance();
		enqueue();
	}

	private void enqueue() {
		_time.enqueue(_time.currentTime(), this);
	}
	
	/**
	 * USED FOR TESTING PURPOSES ONLY
	 * 
	 * @return
	 */
//	public Light getEastWestLight(){
//		return _eastWestLight;
//	}
	
	/**
	 * USED FOR TESTING PURPOSES ONLY
	 * 
	 * @return
	 */
//	public Light getNorthSouthLight(){
//		return _northSouthLight;
//	}

	public void setLight(Light l, Direction d, LightDirection ld) {
            if(d == Direction.EAST){
                if(ld == LightDirection.L){
                    _eastLight_L = l;
                    _eastLight_L.setState(FlowIndicator.STOP);
                }
                else if(ld == LightDirection.R){
                    _eastLight_R = l;
                    _eastLight_R.setState(FlowIndicator.STOP);
                }
                else{
                    _eastLight_F = l;
                    _eastLight_F.setState(FlowIndicator.GO);
                }
            }
            else if(d == Direction.WEST){
                if(ld == LightDirection.L){
                    _westLight_L = l;
                    _westLight_L.setState(FlowIndicator.STOP);
                }
                else if(ld == LightDirection.R){
                    _westLight_R = l;
                    _westLight_R.setState(FlowIndicator.STOP);
                }
                else{
                    _westLight_F = l;
                    _westLight_F.setState(FlowIndicator.GO);
                }
            }
            else if(d == Direction.NORTH){
                if(ld == LightDirection.L){
                    _northLight_L = l;
                    _northLight_L.setState(FlowIndicator.STOP);
                }
                else if(ld == LightDirection.R){
                    _northLight_R = l;
                    _northLight_R.setState(FlowIndicator.STOP);
                }
                else{
                    _northLight_F = l;
                    _northLight_F.setState(FlowIndicator.STOP);
                }
            }
            else if(d == Direction.SOUTH){
                //d == Direction.South
                if(ld == LightDirection.L){
                    _southLight_L = l;
                    _southLight_L.setState(FlowIndicator.STOP);
                }
                else if(ld == LightDirection.R){
                    _southLight_R = l;
                    _southLight_R.setState(FlowIndicator.STOP);
                }
                else{
                    _southLight_F = l;
                    _southLight_F.setState(FlowIndicator.STOP);
                }
            }
		//initial state is north south both F,R. and east west both just R
	}

	public void run() {
		controlTraffic();//add an input here for what type of scheduling you want (synchronized versus generic switching etc)
	}

	private void controlTraffic() {
            //high level add a switch statement here on the variable and make each scheduling thing it's own method.
            simpleSchedule();
            //change to get the new traffic simulation swappings
            
            //_time.enqueue(_time.currentTime() + SP.TRAFFIC_GTIME.getValue(), this);
//            _southLight_L.setState(FlowIndicator.GO);
//            _southLight_R.setState(FlowIndicator.GO);
//            _southLight_F.setState(FlowIndicator.GO);
//             _southLight_L.setState(FlowIndicator.GO);
//            _southLight_R.setState(FlowIndicator.GO);
//            _southLight_F.setState(FlowIndicator.GO);
//		if (_eastWestLight.getFlow() == FlowIndicator.GO) {
//			_northSouthLight.setState(FlowIndicator.STOP);
//			_time.enqueue(
//					_time.currentTime() + SP.TRAFFIC_GTIME.getValue(), this);
//			_eastWestLight.setState(FlowIndicator.CAUTION);
//		} else if (_eastWestLight.getFlow() == FlowIndicator.CAUTION) {
//			_time.enqueue(
//					_time.currentTime() + SP.TRAFFIC_YTIME.getValue(), this);
//			_eastWestLight.setState(FlowIndicator.STOP);
//			_northSouthLight.setState(FlowIndicator.GO);
//		} else if (_northSouthLight.getFlow() == FlowIndicator.GO) {
//			_eastWestLight.setState(FlowIndicator.STOP);
//			_time.enqueue(
//					_time.currentTime() + SP.TRAFFIC_GTIME.getValue(), this);
//			_northSouthLight.setState(FlowIndicator.CAUTION);
//		} else if (_northSouthLight.getFlow() == FlowIndicator.CAUTION) {
//			_time.enqueue(
//					_time.currentTime() + SP.TRAFFIC_YTIME.getValue(), this);
//			_northSouthLight.setState(FlowIndicator.STOP);
//			_eastWestLight.setState(FlowIndicator.GO);
//		}
	}
        
        //add it independent based on the cars there, if there is a car waiting then switch
        //check how many cars can be successful, wait time per car as well, 2 weighted values per queue size and wait time of car.
        //add the time variable of average car length to the light class
        //FR FR NS, then lefts EW, FRFR EW, then lefts NS, just run off of queue size as a factor
        private void simpleSchedule(){
            
            if(_southLight_F.getFlow() == FlowIndicator.GO && _northLight_F.getFlow() == FlowIndicator.GO)
            {
                _eastLight_F.setState(FlowIndicator.STOP);
                _westLight_F.setState(FlowIndicator.STOP);
                _time.enqueue(_time.currentTime() + SP.TRAFFIC_GTIME.getValue(), this);
                _southLight_F.setState(FlowIndicator.CAUTION);
                _northLight_F.setState(FlowIndicator.CAUTION);
            }
            else if(_southLight_F.getFlow() == FlowIndicator.CAUTION && _northLight_F.getFlow() == FlowIndicator.CAUTION)
            {
                _time.enqueue(_time.currentTime() + SP.TRAFFIC_YTIME.getValue(), this);
		_southLight_F.setState(FlowIndicator.STOP);
                _northLight_F.setState(FlowIndicator.STOP);
		_eastLight_F.setState(FlowIndicator.GO);
                _westLight_F.setState(FlowIndicator.GO);
            }
            else if(_eastLight_F.getFlow() == FlowIndicator.GO && _westLight_F.getFlow() == FlowIndicator.GO)
            {
                _southLight_F.setState(FlowIndicator.STOP);
                _northLight_F.setState(FlowIndicator.STOP);
                _time.enqueue(_time.currentTime() + SP.TRAFFIC_GTIME.getValue(), this);
                _eastLight_F.setState(FlowIndicator.CAUTION);
                _westLight_F.setState(FlowIndicator.CAUTION);
            }
            else if(_eastLight_F.getFlow() == FlowIndicator.CAUTION && _westLight_F.getFlow() == FlowIndicator.CAUTION)
            {
                _time.enqueue(_time.currentTime() + SP.TRAFFIC_YTIME.getValue(), this);
		_eastLight_F.setState(FlowIndicator.STOP);
                _westLight_F.setState(FlowIndicator.STOP);
		_southLight_F.setState(FlowIndicator.GO);
                _northLight_F.setState(FlowIndicator.GO);
            }
        }
}
