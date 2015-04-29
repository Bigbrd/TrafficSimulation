package sim.model;

import java.util.HashSet;
import java.util.Set;
import sim.model.LightController.Direction;
import sim.model.Light.LightDirection;
import sim.swing.AnimatorBuilder;
import sim.model.Road.RoadDirection;

final class RegularSegment implements SegmentStrategy {

	private static LightController[] _lc;
	private static Direction _d;
	private static int _index;
	private static AnimatorBuilder _builder;
        private static LightDirection _ld;

	RegularSegment(LightController[] lc, Direction d, int index,
			AnimatorBuilder builder) {
		_lc = lc;
		_d = d;
		_index = index;
		_builder = builder;
	}

	public Source makeSegment() {
            
            //instead of doing car acceptors as the road, just have the cars travel through the roads.
		CarAcceptor nextLeft = new Road(new Sink(),RoadDirection.L);
                CarAcceptor nextForward = new Road(new Sink(),RoadDirection.F);
                CarAcceptor nextRight = new Road(new Sink(),RoadDirection.R);
                
//                CarAcceptor revNextLeft = new Road(new Sink(),RoadDirection.L);
//                CarAcceptor revNextForward = new Road(new Sink(),RoadDirection.F);
//                CarAcceptor revNextRight = new Road(new Sink(),RoadDirection.R);
//                
                //this is the right side going left and its car acceptor isn't working
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

                                nextLeft = new Road(l,RoadDirection.L);
                                nextForward = new Road(f,RoadDirection.F);
                                nextRight = new Road(r,RoadDirection.R);
                                
                                
                        //left side going left
			if (_d == Direction.EAST) {
                                
				_builder.addEastRoad((Road) nextLeft, _index, i);
                                _builder.addEastRoad((Road) nextForward, _index, i);
                                _builder.addEastRoad((Road) nextRight, _index, i);
				_builder.addEastLight(l, _index, i);
                                _builder.addEastLight(f, _index, i);
                                _builder.addEastLight(r, _index, i);
			} 
                        //left side going right
                        else if (_d == Direction.WEST) {
                               
				_builder.addWestRoad((Road) nextLeft, _index, i);
                                _builder.addWestRoad((Road) nextForward, _index, i);
                                _builder.addWestRoad((Road) nextRight, _index, i);
				_builder.addWestLight(r, _index, i);
                                _builder.addWestLight(f, _index, i);
                                _builder.addWestLight(l, _index, i);
			}
                        //top going down
                        else if (_d == Direction.NORTH) {
				_builder.addNorthRoad((Road) nextLeft, i, _index);
                                _builder.addNorthRoad((Road) nextForward, i, _index);
                                _builder.addNorthRoad((Road) nextRight, i, _index);
				_builder.addNorthLight(r, i, _index);
                                _builder.addNorthLight(f, i, _index);
                                _builder.addNorthLight(l, i, _index);
			}
                        //top going up
                        else if(_d == Direction.SOUTH) {
                                _builder.addSouthRoad((Road) nextLeft, i, _index);
                                _builder.addSouthRoad((Road) nextForward, i, _index);
                                _builder.addSouthRoad((Road) nextRight, i, _index);
				_builder.addSouthLight(l, i, _index);
                                _builder.addSouthLight(f, i, _index);
                                _builder.addSouthLight(r, i, _index);
                        }
                    
		}
                //return next forward if we are going left to right
                //return revNextForward if we are going right to left, and also reverse the building of them
		//return new Source(nextForward);
                Set<CarAcceptor> roads = new HashSet<CarAcceptor>();
                roads.add(nextLeft);
                roads.add(nextForward);
                roads.add(nextRight);
                
                //Set<CarAcceptor> roads = new set{nextLeft, nextForward, nextRight};
                return new Source(roads);
	}
}
