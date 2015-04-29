package sim.model;

import java.util.HashSet;
import java.util.Set;
import sim.model.LightController.Direction;
import sim.model.Light.LightDirection;
import sim.swing.AnimatorBuilder;
import sim.model.Road.RoadDirection;


public final class ReverseSegment implements SegmentStrategy {

	private static LightController[] _lc;
	private static Direction _d;
	private static int _index;
	private static AnimatorBuilder _builder;
        private static LightDirection _ld;


	ReverseSegment(LightController[] lc, Direction d, int index,
			AnimatorBuilder builder) {
		_lc = lc;
		_d = d;
		_index = index;
		_builder = builder;
	}

	public Source makeSegment() {
		//CarAcceptor next = new Road(new Sink());
		CarAcceptor nextLeft = new Road(new Sink(),RoadDirection.L);
                CarAcceptor nextForward = new Road(new Sink(),RoadDirection.F);
                CarAcceptor nextRight = new Road(new Sink(),RoadDirection.R);
		
                for (int i = _lc.length - 1; i >= 0; i--) {
                    //this is for the LFR setting
			Light l = new Light(nextLeft);
                        l.setLightDirection(LightDirection.L);
                        Light f = new Light(nextForward);
                        f.setLightDirection(LightDirection.F);
                        Light r = new Light(nextRight);
                        r.setLightDirection(LightDirection.R);
			_lc[i].setLight(l, _d, LightDirection.L);
                        _lc[i].setLight(f, _d, LightDirection.F);
                        _lc[i].setLight(r, _d, LightDirection.R);
			
                        
			if (_d == Direction.EAST) {
				_builder.addEastRoad((Road) nextLeft, _index, i);
                                _builder.addEastRoad((Road) nextForward, _index, i);
                                _builder.addEastRoad((Road) nextRight, _index, i);
				_builder.addEastLight(l, _index, i);
                                _builder.addEastLight(f, _index, i);
                                _builder.addEastLight(r, _index, i);
			} 
                        else if (_d == Direction.WEST) {
				_builder.addWestRoad((Road) nextLeft, _index, i);
                                _builder.addWestRoad((Road) nextForward, _index, i);
                                _builder.addWestRoad((Road) nextRight, _index, i);
				_builder.addWestLight(l, _index, i);
                                _builder.addWestLight(f, _index, i);
                                _builder.addWestLight(r, _index, i);
			}
                        else if (_d == Direction.NORTH) {
				_builder.addNorthRoad((Road) nextLeft, _index, i);
                                _builder.addNorthRoad((Road) nextForward, _index, i);
                                _builder.addNorthRoad((Road) nextRight, _index, i);
				_builder.addNorthLight(l, i, _index);
                                _builder.addNorthLight(f, i, _index);
                                _builder.addNorthLight(r, i, _index);
			}
                        else if(_d == Direction.SOUTH) {
                                _builder.addSouthRoad((Road) nextLeft, _index, i);
                                _builder.addSouthRoad((Road) nextForward, _index, i);
                                _builder.addSouthRoad((Road) nextRight, _index, i);
				_builder.addSouthLight(l, i, _index);
                                _builder.addSouthLight(f, i, _index);
                                _builder.addSouthLight(r, i, _index);
                        }
                    nextLeft = new Road(l,RoadDirection.L);
                        nextForward = new Road(f,RoadDirection.F);
                        nextRight = new Road(r,RoadDirection.R);
		}    
                
			//next = new Road(l);
		if (_d == Direction.EAST) {
			//_builder.addEastRoad((Road) next, _index, _lc.length);
                        _builder.addEastRoad((Road) nextLeft, _index, _lc.length);
                        _builder.addEastRoad((Road) nextForward, _index, _lc.length);
                        _builder.addEastRoad((Road) nextRight, _index, _lc.length);
		}
                //this is the right side going right
                else if (_d == Direction.WEST) {
			//_builder.addWestRoad((Road) next, _index, _lc.length);
                        _builder.addWestRoad((Road) nextLeft, _index, _lc.length);
                        _builder.addWestRoad((Road) nextForward, _index, _lc.length);
                        _builder.addWestRoad((Road) nextRight, _index, _lc.length);
		}
                else if (_d == Direction.NORTH) {
			//_builder.addNorthRoad((Road) next, _lc.length, _index);
                        _builder.addNorthRoad((Road) nextLeft, _lc.length, _index);
                        _builder.addNorthRoad((Road) nextForward, _lc.length, _index);
                        _builder.addNorthRoad((Road) nextRight, _lc.length, _index);
		}
                else if (_d == Direction.SOUTH){
			//_builder.addSouthRoad((Road) next, _lc.length, _index);
                        _builder.addSouthRoad((Road) nextLeft, _lc.length, _index);
                        _builder.addSouthRoad((Road) nextForward, _lc.length, _index);
                        _builder.addSouthRoad((Road) nextRight, _lc.length, _index);
                }
                
                Set<CarAcceptor> roads = new HashSet<CarAcceptor>();
                roads.add(nextLeft);
                roads.add(nextForward);
                roads.add(nextRight);
                return new Source(roads);
		//return new Source(nextForward);
	}
}
