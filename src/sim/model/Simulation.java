package sim.model;

import sim.agent.TimeServer;
import sim.agent.TimeServerLinked;
import sim.model.LightController;
import sim.model.LightController.Direction;
import sim.swing.AnimatorBuilder;
import sim.swing.SwingAnimatorBuilder;
import sim.util.Animator;

public final class Simulation {

	private AnimatorBuilder _builder;
	private Animator _animator;
	private TimeServer _time;
	private boolean _isAlternating;
	private boolean _disposed;

	Simulation() {
		_builder = new SwingAnimatorBuilder();
		_time = TimeServerLinked.getInstance();
		_isAlternating = SP.PATTERN.getPatternValue();
		makeGrid(SP.DIMENSION.getDimValue());
		_animator = _builder.getAnimator();
		_time.addObserver(_animator);
	}

	private void makeGrid(int[] dimension) {
		int row = dimension[0];
		int col = dimension[1];
		LightController[][] eastWest = new LightController[row][col];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				eastWest[i][j] = new LightController();
			}
		}
		LightController[][] northSouth = rotate(eastWest);
		createSegments(row, col, eastWest, northSouth);
	}

	private void createSegments(int row, int col, LightController[][] eastWest,
			LightController[][] northSouth) {
		if (_isAlternating) {
			_isAlternating = false;
			for (int i = 0; i < row; i++) {
				createAlternatingPattern(eastWest, i, Direction.WEST);
                                _isAlternating = !_isAlternating;
                                createAlternatingPattern(eastWest, i, Direction.EAST);
				
			}
			_isAlternating = false;
			for (int i = 0; i < col; i++) {
				createAlternatingPattern(northSouth, i, Direction.NORTH);
                                _isAlternating = !_isAlternating;
                                createAlternatingPattern(northSouth, i, Direction.SOUTH);
				
			}
		} 
//                        else {
//			for (int i = 0; i < row; i++) {
//				RegularSegment regSeg_E = new RegularSegment(eastWest[i],
//						Direction.EAST, i, _builder);
//                                RegularSegment regSeg_W = new RegularSegment(eastWest[i],
//						Direction.WEST, i, _builder);
//				regSeg_E.makeSegment();
//                                regSeg_W.makeSegment();
//			}
//			for (int i = 0; i < col; i++) {
//				RegularSegment regSeg_N = new RegularSegment(northSouth[i],
//						Direction.NORTH, i, _builder);
//                                RegularSegment regSeg_S = new RegularSegment(northSouth[i],
//						Direction.SOUTH, i, _builder);
//				regSeg_N.makeSegment();
//				regSeg_S.makeSegment();
//			}
//		}
	}

	private void createAlternatingPattern(LightController[][] lc, int i,
			Direction d) {
		if (_isAlternating) {
			ReverseSegment revSeg = new ReverseSegment(lc[i], d, i, _builder);
			revSeg.makeSegment();
		} else {
			RegularSegment regSeg = new RegularSegment(lc[i], d, i, _builder);
			regSeg.makeSegment();
		}
	}

	private LightController[][] rotate(LightController[][] orig) {
		LightController[][] value = new LightController[orig[0].length][orig.length];
		for (int i = 0; i < orig[0].length; i++) {
			for (int j = 0; j < orig.length; j++) {
				value[i][j] = orig[j][orig[0].length - i - 1];
			}
		}
		return value;
	}

	public void dispose() {
		_animator.dispose();
		_disposed = true;
	}

	public void run() {
		if (_disposed)
			throw new IllegalStateException();
		_time.run(SP.RUN_TIME.getValue());
	}

}
