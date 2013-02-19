package com.github.mathphreak.robotarm;

import android.app.Activity;
import android.os.Bundle;

public class RobotControlActivity extends Activity {
	
	/* things that need to happen:
	 * GUI ---------------------------- bluetooth
	 * [x]          x position          [ ]
	 * [x]          y position          [ ]
	 * [x]      z position (height)     [ ]
	 * [?]  claw rotation around z-axis [ ] // GUI location = sketchy, and also...
	 * [?] claw angle not around z-axis [ ] // implementation = Math.pow(sketchy, 2)
	 * [x]         claw openness        [ ] // <-- using a stock slider...
	 * [x]    automatic/manual control  [ ]
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_robot_control);
	}

}
