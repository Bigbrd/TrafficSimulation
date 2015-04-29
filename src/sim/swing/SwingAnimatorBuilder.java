package sim.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import sim.model.SP;
import sim.swing.AnimatorBuilder;
import sim.model.Car;
import sim.model.Light;
import sim.model.Light.FlowIndicator;
import sim.model.Light.LightDirection;
import sim.model.LightController.Direction;
import sim.model.Road.RoadDirection;
import sim.model.Road;
import sim.util.Animator;
import sim.util.SwingAnimator;
import sim.util.SwingAnimatorPainter;

/**
 * A class for building Animators.
 */
public class SwingAnimatorBuilder implements AnimatorBuilder {
	MyPainter _painter;

	public SwingAnimatorBuilder() {
		_painter = new MyPainter();
	}

	public Animator getAnimator() {
		if (_painter == null) {
			throw new IllegalStateException();
		}
		Animator returnValue = new SwingAnimator(_painter, "Traffic Simulator",
				VP.displayWidth, VP.displayHeight, VP.displayDelay);
		_painter = null;
		return returnValue;
	}

	private static double skipInit = VP.gap;
	private static double skipRoad = VP.gap + SP.ROAD_LENGTH_UI.getValue();
	// SP.ROAD_LENGTH_UI.getValue();
	private static double skipCar = VP.gap + VP.elementWidth;
	private static double skipRoadCar = skipRoad + skipCar;
        
        private static double skipLight = VP.lightgap;
        private static double skipRoadGap = VP.roadgap;
        private static double skipMoveLights = VP.moveLightsRange;
        
        private static double skipNeighborRoadGap = VP.neighborroadgap;

//	public void addLight(Light d, int i, int j) {
//		double x = skipInit + skipRoad + j * skipRoadCar;
//		double y = skipInit + skipRoad + i * skipRoadCar;
//                if(d.getLightDirection() == LightDirection.L)
//                {
//                    x -= skipLight;
//                }
//                else if (d.getLightDirection() == LightDirection.R)
//                {
//                    x+= skipLight;
//                }
//		Translator t = new TranslatorWE(x, y, SP.CAR_WIDTH_UI.getValue(),
//				VP.elementWidth, VP.scaleFactor);
//		_painter.addLight(d, t);
//	}
        public void addEastLight(Light d, int i, int j) {
		double x = skipInit + skipRoad + j * skipRoadCar;
		double y = skipInit + skipRoad + i * skipRoadCar;
                
                //adjust the lights to be for the direction
                x+=skipMoveLights + VP.fixLightRange;
                y-=skipMoveLights;
                
                if(d.getLightDirection() == LightDirection.L)
                {
                    y+= skipLight;
                }
                else if (d.getLightDirection() == LightDirection.R)
                {
                    y-= skipLight;
                }
		Translator t = new TranslatorWE(x, y, SP.CAR_WIDTH_UI.getValue(),
				VP.elementWidth, VP.scaleFactor);
		_painter.addLight(d, t, Direction.EAST);
	}
        public void addWestLight(Light d, int i, int j) {
		double x = skipInit + skipRoad + j * skipRoadCar;
		double y = skipInit + skipRoad + i * skipRoadCar;
                
                //adjust the lights to be for the direction
                x-=skipMoveLights + VP.fixLightRange;
                y+=skipMoveLights;
                
                if(d.getLightDirection() == LightDirection.L)
                {
                    y-= skipLight;
                }
                else if (d.getLightDirection() == LightDirection.R)
                {
                    y+= skipLight;
                }
		Translator t = new TranslatorWE(x, y, SP.CAR_WIDTH_UI.getValue(),
				VP.elementWidth, VP.scaleFactor);
		_painter.addLight(d, t, Direction.WEST);
	}
          public void addNorthLight(Light d, int i, int j) {
		double x = skipInit + skipRoad + j * skipRoadCar;
		double y = skipInit + skipRoad + i * skipRoadCar;
                
                //adjust the lights to be for the direction
                x-=skipMoveLights;
                y-=skipMoveLights + VP.fixLightRange;
                
                if(d.getLightDirection() == LightDirection.L)
                {
                    x += skipLight;
                }
                else if (d.getLightDirection() == LightDirection.R)
                {
                    x-= skipLight;
                }
		Translator t = new TranslatorWE(x, y, SP.CAR_WIDTH_UI.getValue(),
				VP.elementWidth, VP.scaleFactor);
		_painter.addLight(d, t, Direction.NORTH);
	}
        public void addSouthLight(Light d, int i, int j) {
		double x = skipInit + skipRoad + j * skipRoadCar;
		double y = skipInit + skipRoad + i * skipRoadCar;
                
                //adjust the lights to be for the direction
                x+=skipMoveLights;
                y+=skipMoveLights + VP.fixLightRange;
                
                if(d.getLightDirection() == LightDirection.L)
                {
                    x -= skipLight;
                }
                else if (d.getLightDirection() == LightDirection.R)
                {
                    x+= skipLight;
                }
		Translator t = new TranslatorWE(x, y, SP.CAR_WIDTH_UI.getValue(),
				VP.elementWidth, VP.scaleFactor);
		_painter.addLight(d, t, Direction.SOUTH);
	}

//	public void addHorizontalRoad(Road l, int i, int j, boolean eastToWest) {
//		double x = skipInit + j * skipRoadCar;
//		double y = skipInit + skipRoad + i * skipRoadCar;
//		Translator t = eastToWest ? new TranslatorEW(x, y,
//				SP.ROAD_LENGTH_UI.getValue(), VP.elementWidth, VP.scaleFactor)
//				: new TranslatorWE(x, y, SP.ROAD_LENGTH_UI.getValue(),
//						VP.elementWidth, VP.scaleFactor);
//		_painter.addRoad(l, t);
//	}
//
//	public void addVerticalRoad(Road l, int i, int j, boolean southToNorth) {
//		double x = skipInit + skipRoad + j * skipRoadCar;
//		double y = skipInit + i * skipRoadCar;
//		Translator t = southToNorth ? new TranslatorSN(x, y,
//				SP.ROAD_LENGTH_UI.getValue(), VP.elementWidth, VP.scaleFactor)
//				: new TranslatorNS(x, y, SP.ROAD_LENGTH_UI.getValue(),
//						VP.elementWidth, VP.scaleFactor);
//		_painter.addRoad(l, t);
//	}
        
        public void addEastRoad(Road l, int i, int j){
                double x = skipInit + j * skipRoadCar;
		double y = skipInit + skipRoad + i * skipRoadCar;
                y -= skipRoadGap;
                
                if(l.getRoadDirection() == RoadDirection.L)
                {
                    y += skipNeighborRoadGap;
                }
                else if (l.getRoadDirection() == RoadDirection.R)
                {
                    y -= skipNeighborRoadGap;
                }
                
		Translator t = new TranslatorEW(x, y, SP.ROAD_LENGTH_UI.getValue(),
				 VP.elementWidth, VP.scaleFactor);
		_painter.addRoad(l, t, Direction.EAST);
        }
        public void addWestRoad(Road l, int i, int j){
                double x = skipInit + j * skipRoadCar;
		double y = skipInit + skipRoad + i * skipRoadCar;
                y += skipRoadGap;
                
                if(l.getRoadDirection() == RoadDirection.L)
                {
                    y -= skipNeighborRoadGap;
                }
                else if (l.getRoadDirection() == RoadDirection.R)
                {
                    y+= skipNeighborRoadGap;
                }
                
		Translator t = new TranslatorWE(x, y, SP.ROAD_LENGTH_UI.getValue(),
				 VP.elementWidth, VP.scaleFactor);
		_painter.addRoad(l, t, Direction.WEST);
        }
        public void addNorthRoad(Road l, int i, int j){
                double x = skipInit + skipRoad + j * skipRoadCar;
		double y = skipInit + i * skipRoadCar;
                x -= skipRoadGap;
                
                if(l.getRoadDirection() == RoadDirection.L)
                {
                    x += skipNeighborRoadGap;
                }
                else if (l.getRoadDirection() == RoadDirection.R)
                {
                    x -= skipNeighborRoadGap;
                }
                
		Translator t = new TranslatorNS(x, y, SP.ROAD_LENGTH_UI.getValue(),
						VP.elementWidth, VP.scaleFactor);
		_painter.addRoad(l, t, Direction.NORTH);
        }
        public void addSouthRoad(Road l, int i, int j){
                double x = skipInit + skipRoad + j * skipRoadCar;
		double y = skipInit + i * skipRoadCar;
                x += skipRoadGap;
                
                if(l.getRoadDirection() == RoadDirection.L)
                {
                    x -= skipNeighborRoadGap;
                }
                else if (l.getRoadDirection() == RoadDirection.R)
                {
                    x += skipNeighborRoadGap;
                }
                
		Translator t = new TranslatorSN(x, y, SP.ROAD_LENGTH_UI.getValue(),
						VP.elementWidth, VP.scaleFactor);
		_painter.addRoad(l, t, Direction.SOUTH);
        }

//        public void updateEastRoadQueue(Road l, int i, int j){
//                
//        }
        
	/** Class for drawing the Model. */
	private static class MyPainter implements SwingAnimatorPainter {

		/**
		 * Pair of a model element <code>x</code> and a translator
		 * <code>t</code>.
		 */
		private static class Element<T> {
			T x;
			Translator t;
                        Direction d;

			Element(T x, Translator t) {
				this.x = x;
				this.t = t;
			}

                        Element(T x, Translator t, Direction d) {
                            this.x = x;
                            this.t = t;
                            this.d = d;
                        }
		}

		private List<Element<Road>> _roadElements;
		private List<Element<Light>> _lightElements;

		MyPainter() {
			_roadElements = new ArrayList<Element<Road>>();
			_lightElements = new ArrayList<Element<Light>>();
		}

		void addLight(Light x, Translator t, Direction d) {
			_lightElements.add(new Element<Light>(x, t, d));
		}

		void addRoad(Road x, Translator t, Direction d) {
			_roadElements.add(new Element<Road>(x, t, d));
		}

		public void paint(Graphics g) {
			// This method is called by the swing thread, so may be called
			// at any time during execution...

			// First draw the background elements
			for (Element<Light> e : _lightElements) {
				if (e.x.getFlow() == FlowIndicator.GO) {
					g.setColor(Color.GREEN);
                                        
				} else if (e.x.getFlow() == FlowIndicator.CAUTION) {
					g.setColor(Color.YELLOW);
                                        
				} else if (e.x.getFlow() == FlowIndicator.STOP) {
					g.setColor(Color.RED);
                                        
				}
				XGraphics.fillOval(g, e.t, 0, 0, SP.CAR_WIDTH_UI.getValue(),
						VP.elementWidth);
                                String direction = e.x.getLightDirection().toString();
                                g.setColor(Color.BLACK);
                                if(e.d == Direction.NORTH)
                                {
                                    g.drawString(direction, (int)e.t._tX+VP.lightLetter_X, (int)e.t._tY+VP.lightLetter_Y);
                                }
                                else
                                {
                                    g.drawString(direction, (int)e.t._tX+VP.lightLetter_X, (int)e.t._tY+VP.lightLetter_Y);
                                }
                                g.setColor(Color.BLUE);
                                String successCarCount = Integer.toString(e.x.successCarCount);
                                g.drawString(successCarCount, (int)(1.5*e.t._tX), (int)(1.5*e.t._tY));
                                
//                                g.setColor(Color.MAGENTA);
//                                String queueCount = Integer.toString(e.x.queueCarCount);
//                                g.drawString(queueCount, (int)(.75*e.t._tX), (int)(.75*e.t._tY));
//                                g.setColor(Color.BLACK);
			}
			g.setColor(Color.BLACK);
			for (Element<Road> e : _roadElements) {
				XGraphics.fillRect(g, e.t, 0, 0, SP.ROAD_LENGTH_UI.getValue(),
						VP.elementWidth);
			}

			// Then draw the foreground elements
                        int i=1;
                        int j = 1;
			for (Element<Road> e : _roadElements) {
                            
//                            //get all cars that are at 0 mph go through each of the list and get the speeds of each to count++
//                                String queueCount = Integer.toString(e.x.getNumberOfStoppedCars());
//                                g.setColor(Color.BLUE);
//                                g.drawString(queueCount, (int)e.t._tX, (int)e.t._tY);
//                                g.setColor(Color.BLACK);
                                g.setColor(Color.MAGENTA);
                                String queueCount = Integer.toString(e.x.getNumberOfStoppedCars());
                                g.drawString(queueCount, (int)(.75*e.t._tX), (int)(.75*e.t._tY));
                                g.setColor(Color.BLACK);
                                //alternative is to just get the speed of the car and then change it in the Iterator thing.
                                
                                g.setColor(Color.ORANGE);
                                
                                for(double t : e.x._carDelays){
                                    String carDelayData = Double.toString(t);
                                    g.drawString(carDelayData, (int)(770), (int)(500 + 25*j));
                                    j++;
                                }
                                
                                g.setColor(Color.BLACK);
                                
				// iterate through a copy because e.x.getCars() may change
				// during iteration...
				double roadLength = e.x.getLength();
				Iterator<Car> it = e.x.getCars().iterator();
                                
				while (it.hasNext()) {
					Car d = it.next();
					g.setColor(d.getColor());
					double normalizedPosition = d.getFrontPosition()
							/ roadLength;
                                        
                                        
                                        
					XGraphics.fillRect(g, e.t, normalizedPosition
							* SP.ROAD_LENGTH_UI.getValue(), 0, d.getLength(),
							VP.elementWidth);
                                        
                                        g.setColor(Color.RED);
                                        String carData = Double.toString(d.getTimeDelayed());
                                        //g.drawString(carData, (int)(760), (int)(20 + 25*i));
                                        g.setColor(Color.BLACK);
                                        i++;
                                        
                                        //16 == R for EW and L for NS
                                        //0 == inside lane
                                        //-16 == R for NS and L for EW
                                        
				}
			}
		}
	}
}
