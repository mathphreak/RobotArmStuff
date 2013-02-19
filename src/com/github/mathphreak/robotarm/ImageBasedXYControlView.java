package com.github.mathphreak.robotarm;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * TODO: document your custom view class.
 */
public class ImageBasedXYControlView extends View {
	private Drawable robotArmField = getResources().getDrawable(R.drawable.field);
	private final int FIELD_PIXELS_PER_CM = 10;
	private static final String DEBUG_TAG = "XYPos";
	private int scaleX = 1;
	private int scaleY = 1;
	private int offsetX = 0;
	private int offsetY = 0;
	private GestureDetectorCompat detector;

	public ImageBasedXYControlView(Context context) {
		super(context);
		init(null, 0);
	}

	public ImageBasedXYControlView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0);
	}

	public ImageBasedXYControlView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		init(attrs, defStyle);
	}

	private void init(AttributeSet attrs, int defStyle) {
		// Load attributes (if we have any HA!)
		detector = new GestureDetectorCompat(getContext(), new GestureListener());
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// TODO: consider storing these as member variables to reduce
		// allocations per draw cycle.
		int paddingLeft = getPaddingLeft();
		int paddingTop = getPaddingTop();
		int paddingRight = getPaddingRight();
		int paddingBottom = getPaddingBottom();

		int contentWidth = getWidth() - paddingLeft - paddingRight;
		int contentHeight = getHeight() - paddingTop - paddingBottom;

		// Draw the example drawable on top of the text.
		if (robotArmField != null) {
			double fieldWidth = robotArmField.getIntrinsicWidth();
			double fieldHeight = robotArmField.getIntrinsicHeight();
			double widthScale = (paddingLeft + contentWidth) / fieldWidth;
			double heightScale = (paddingTop + contentHeight) / fieldHeight;
			if (widthScale < heightScale) {
				heightScale = widthScale;
			} else {
				widthScale = heightScale;
			}
			Log.d(DEBUG_TAG, "Width scale is " + widthScale + " and height scale is " + heightScale);
			robotArmField.setBounds(paddingLeft, paddingTop, (int)(widthScale * fieldWidth), (int)(heightScale * fieldHeight));
			robotArmField.draw(canvas);
		}
		
		System.out.println(FIELD_PIXELS_PER_CM);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		detector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}
	
	private class GestureListener extends GestureDetector.SimpleOnGestureListener {
		private static final String DEBUG_TAG = "XYPosGestures";

		@Override
		public boolean onDoubleTap(MotionEvent e) {
			// TODO Auto-generated method stub
			return super.onDoubleTap(e);
		}

		@Override
		public boolean onDown(MotionEvent e) {
			// TODO Auto-generated method stub
			return super.onDown(e);
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			// TODO Auto-generated method stub
			return super.onScroll(e1, e2, distanceX, distanceY);
		}
	}
}
