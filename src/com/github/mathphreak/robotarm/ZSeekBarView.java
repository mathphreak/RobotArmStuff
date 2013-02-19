package com.github.mathphreak.robotarm;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * The Z seek bar.
 */
public class ZSeekBarView extends View {
	private float currZ = 0;
	private float centerX;
	private float topY;
	private float bottomY;
	private Paint innerColor = new Paint(Paint.ANTI_ALIAS_FLAG);
	private Paint outerColor = new Paint(Paint.ANTI_ALIAS_FLAG);
	private Paint centerLineColor = new Paint(Paint.ANTI_ALIAS_FLAG);
	private Paint positionLineColor = new Paint(Paint.ANTI_ALIAS_FLAG);
	@SuppressWarnings("unused")
	private final String TAG = "ZBarSeekView";

	public ZSeekBarView(Context context) {
		super(context);
		init(null, 0);
	}

	public ZSeekBarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0);
	}

	public ZSeekBarView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs, defStyle);
	}

	private void init(AttributeSet attrs, int defStyle) {
		outerColor.setColor(getResources().getColor(android.R.color.holo_blue_light));
		innerColor.setColor(getResources().getColor(android.R.color.holo_blue_dark));
		centerLineColor.setColor(getResources().getColor(android.R.color.darker_gray));
		centerLineColor.setStyle(Style.STROKE);
		positionLineColor.setColor(getResources().getColor(android.R.color.holo_blue_dark));
		positionLineColor.setStyle(Style.STROKE);
		positionLineColor.setStrokeWidth(PIX_FROM_DP(2));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		centerX = canvas.getWidth() / 2;
		topY = PIX_FROM_DP(16);
		bottomY = canvas.getHeight() - topY;
		
		this.constrainZ();
		
		// draw the line
		canvas.drawLine(centerX, topY, centerX, bottomY, centerLineColor);
		canvas.drawLine(centerX, topY, centerX, currZ, positionLineColor);

		float indicatorRadius = PIX_FROM_DP(4);
		float indicatorOuterRadius = PIX_FROM_DP(12);
		canvas.drawCircle(centerX, currZ, indicatorOuterRadius, outerColor);
		canvas.drawCircle(centerX, currZ, indicatorRadius, innerColor);
	}
	
	private float PIX_FROM_DP(float dp) {
		return (float) (dp * getResources().getDisplayMetrics().densityDpi / 160.0);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		switch (event.getActionMasked()) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_MOVE:
			currZ = event.getY();
			this.invalidate();
			return true;
		}
		return false;
	}
	
	private void constrainZ() {
		if (currZ > bottomY) {
			currZ = bottomY;
		} else if (currZ < topY) {
			currZ = topY;
		}
	}
}
