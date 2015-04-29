package sim.swing;

/**
 * Static class for visualization parameters.
 */
class VP {

	private VP() {
	}

	/** Width of model elements, in meters */
	static double elementWidth = 15.0;
	/** Gap between model elements, in meters */
	static double gap = 42;//8;
	/** Width of the displayed graphics window, in pixels */
	static int displayWidth = 1000;
	/** Height of the displayed graphics window, in pixels */
	static int displayHeight = 1000;
	/** Delay introduced into each graphics update, in milliseconds */
	static int displayDelay = 5;
        
	/** Scale factor relating model space to pixels, in pixels/meter */
	static double scaleFactor = 1;
        
        /**Gap for each lights individual gap center next to each other, in meters*/
        static double lightgap = 15;//20;
        
        //lights group from the middle spot
        static double moveLightsRange = 27;//76;
        //move light adjustment
        static double fixLightRange = 14;
        
        //letter gapping, stays constant
        static int lightLetter_X = 5;
        static int lightLetter_Y = 12;
        
        //queue string gapping, stays constant
        static int queueNumber_X = 10;
        static int queueNumber_Y = 30;
        
        //gap for the main directions of the road
        static double roadgap = 25;
        
        //gap for the individual directions of each road
        static double neighborroadgap = 16;
}
