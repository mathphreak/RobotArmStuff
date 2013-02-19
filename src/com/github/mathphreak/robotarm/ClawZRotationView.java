package com.github.mathphreak.robotarm;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Rotation around the Z-axis.
 */
public class ClawZRotationView extends View {
	private float currAngle;
	private float startAngle;
	private Paint clawStandinColor = new Paint(Paint.ANTI_ALIAS_FLAG);
	@SuppressWarnings("unused")
	private final String TAG = "ClawZRotationView";

	public ClawZRotationView(Context context) {
		super(context);
		init(null, 0);
	}

	public ClawZRotationView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0);
	}

	public ClawZRotationView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs, defStyle);
	}

	private void init(AttributeSet attrs, int defStyle) {
		clawStandinColor.setColor(getResources().getColor(android.R.color.holo_orange_dark));
		clawStandinColor.setStyle(Style.STROKE);
		clawStandinColor.setStrokeWidth(PIX_FROM_DP(4));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		int padding = this.getPaddingTop();
		int height = canvas.getHeight();
		int width = canvas.getWidth();
		float halfWidth = width / 2F;
		float halfHeight = height / 2F;
		float quarterWidth = width / 4F;
//		double startRad = startAngle * 180 / Math.PI;
//		float cos = 1 + (float) Math.cos(startRad);
//		float sin = 1 + (float) Math.sin(startRad);

		canvas.save();
		canvas.rotate(currAngle, halfWidth, halfHeight);
		canvas.drawLine(padding, halfHeight, padding + quarterWidth, halfHeight, clawStandinColor);
		canvas.drawLine(quarterWidth * 3F - padding, halfHeight, width - padding, halfHeight, clawStandinColor);
//		canvas.drawLine(halfWidth, halfHeight, halfWidth * cos, halfHeight * sin, clawStandinColor);
		canvas.restore();
	}
	
	private float PIX_FROM_DP(float dp) {
		return (float) (dp * getResources().getDisplayMetrics().densityDpi / 160.0);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		switch (event.getActionMasked()) {
		case MotionEvent.ACTION_DOWN:
			startAngle = (float) ((180 / Math.PI) * Math.atan2(event.getY(), event.getX())) - currAngle;
			currAngle = 0;
			this.invalidate();
			return true;
		case MotionEvent.ACTION_MOVE:
			currAngle = (float) ((180 / Math.PI) * Math.atan2(event.getY(), event.getX())) - startAngle;
			this.invalidate();
			return true;
		case MotionEvent.ACTION_UP:
			this.invalidate();
			return true;
		}
		return false;
	}
}
