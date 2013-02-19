package com.github.mathphreak.robotarm;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * The XY seek bar.
 */
public class XYSeekBarView extends View {
	private float startX;
	private float startY;
	private float currX;
	private float currY;
	private boolean isPanning = false;
	private Paint innerColor = new Paint(Paint.ANTI_ALIAS_FLAG);
	private Paint outerColor = new Paint(Paint.ANTI_ALIAS_FLAG);
	private Paint crosshairColor = new Paint(Paint.ANTI_ALIAS_FLAG);
	private Paint epicColor = new Paint(Paint.ANTI_ALIAS_FLAG);
	private final String TAG = "XYBarSeekView";

	public XYSeekBarView(Context context) {
		super(context);
		init(null, 0);
	}

	public XYSeekBarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0);
	}

	public XYSeekBarView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs, defStyle);
	}

	private void init(AttributeSet attrs, int defStyle) {
		outerColor.setColor(getResources().getColor(android.R.color.holo_blue_light));
		innerColor.setColor(getResources().getColor(android.R.color.holo_blue_dark));
		crosshairColor.setColor(getResources().getColor(android.R.color.holo_orange_dark));
		crosshairColor.setStyle(Style.STROKE);
		crosshairColor.setStrokeWidth(PIX_FROM_DP(4));
		epicColor.setColor(getResources().getColor(android.R.color.darker_gray));
		epicColor.setStyle(Style.STROKE);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if (isPanning) {
			// TODO: consider storing these as member variables to reduce
			// allocations per draw cycle.
			Log.d(TAG, "Drawing touched");
			float indicatorRadius = PIX_FROM_DP(4);
			float indicatorOuterRadius = PIX_FROM_DP(12);
			canvas.drawCircle(startX, startY, indicatorOuterRadius, outerColor);
			canvas.drawCircle(startX, startY, indicatorRadius, innerColor);
			
			// draw the crosshair too
			canvas.drawCircle(currX, currY, indicatorOuterRadius, crosshairColor);
			
			// draw the line
			canvas.drawLine(startX, startY, currX, currY, epicColor);
		}
	}
	
	private float PIX_FROM_DP(float dp) {
		return (float) (dp * getResources().getDisplayMetrics().densityDpi / 160.0);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		switch (event.getActionMasked()) {
		case MotionEvent.ACTION_DOWN:
			isPanning = true;
			startX = event.getX();
			startY = event.getY();
			currX = startX;
			currY = startY;
			Log.d(TAG, "Touching with X " + startX + " and Y " + startY);
			this.invalidate();
			return true;
		case MotionEvent.ACTION_MOVE:
			currX = event.getX();
			currY = event.getY();
			this.invalidate();
			return true;
		case MotionEvent.ACTION_UP:
			isPanning = false;
			this.invalidate();
			return true;
		}
		return false;
	}
}
