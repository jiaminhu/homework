package edu.neu.madcourse.nuillegalbronze.boggle;


import edu.neu.madcourse.nuillegalbronze.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class BoggleView extends View{
	
	private String TAG="BoggleView";
	
	private static final int NONE=0;
	private static final int DRAG=1;
	private static final int ZOOM=2;
	private int mode=NONE;

	//---------------------------------------
	
	private static final int ID = 42;
	private final Boggle game;
	
	private float width;
	private float height;
	private float padding;
	
	private float padding_top = 100;
	
	public BoggleView(Context context) {
		super(context);
		game = (Boggle) context;
		
	    setFocusable(true);
	    setFocusableInTouchMode(true);
	    
		Log.d("getw",Float.toString(getWidth()));
	    Log.d(TAG,Float.toHexString(width));
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		width = height = Math.min(w / (Boggle.DICE_WIDTH + 1f), h
				/ (Boggle.DICE_HEIGHT + 1f));
		padding = width / (Math.max(Boggle.DICE_WIDTH, Boggle.DICE_HEIGHT) + 1);
		padding_top = 100;
		
		
		Log.d(TAG, "onSizeChanged: width " + width + ", height " + height);
		super.onSizeChanged(w, h, oldw, oldh);
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		drawBackground(canvas);
		drawTiles(canvas);
		drawWord(canvas);
	}

	protected void drawBackground(Canvas canvas) {
		Paint background = new Paint();
		background.setColor(getResources().getColor(R.color.puzzle_background));
		canvas.drawRect(0, 0, getWidth(), getHeight(), background);
	}
    protected void drawTiles(Canvas canvas) {
        super.onDraw(canvas);
        Rect imageBounds = new Rect();
        
        for (int x = 0; x < Boggle.DICE_WIDTH; x++) {
        	for (int y = 0; y < Boggle.DICE_HEIGHT; y++) {
        		getRect(x, y, imageBounds);
        		
        		game.getTile(x, y).draw(this, canvas, imageBounds, height, width);
        	}
        }
    } 
	private void drawWord(Canvas canvas) {

	}
    
	public boolean onTouchEvent(MotionEvent event){
		dumpEvent(event);
		
		int x = (int) ((event.getX() - (padding / 2f)) / (width + padding));
		int y = (int) ((event.getY() - padding_top - (padding / 2f)) / (height + padding));
		x = Math.min(Math.max(0, x), Boggle.DICE_WIDTH - 1);
		y = Math.min(Math.max(0, y), Boggle.DICE_HEIGHT - 1);
		Log.d(TAG, "x = " + x + ", y = " + y);
		
		switch(event.getAction()&MotionEvent.ACTION_MASK){
		case MotionEvent.ACTION_DOWN:
			select(x, y);			
			mode=DRAG;
			Log.d("actiondown","mode=drag");
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:
			mode=NONE;
			Log.d("mode","mode=none");
			break;
		case MotionEvent.ACTION_MOVE:
			if(mode==DRAG){
				select(x, y);	
			}
			break;
		}

		return true;		
	}

	private void select(int x, int y){
		game.selectTile(x, y);
		Rect selectedArea = new Rect();
		getRect(x, y, selectedArea);
		invalidate(selectedArea);
	}
	
	private void getRect(int x, int y, Rect rect) {
		float paddingX = (x + 1) * padding;
		float paddingY = (y + 1) * padding;
		float left = paddingX + x * width;
		float top = paddingY + y * height + padding_top;
		rect.set((int) (left), (int) (top), (int) (left + width),
				(int) (top + height));
	}

    private void dumpEvent(MotionEvent event){
    	String names[]={"DOWN","UP","MOVE","CANCEL","OUTSIDE","POINT_DOWN",
    			"POINT_UP","7?","8?","9?"};
    	StringBuilder sb=new StringBuilder();
    	int action=event.getAction();
    	int actionCode=action&MotionEvent.ACTION_MASK;
    	sb.append("event ACTION_").append(names[actionCode]);
    	if(actionCode==MotionEvent.ACTION_POINTER_DOWN||
    			actionCode==MotionEvent.ACTION_POINTER_UP){
    		sb.append("pid ").append(action>>MotionEvent.ACTION_POINTER_ID_SHIFT);
    		sb.append(")");
    	}
    	sb.append("[");
    	for(int i=0;i<event.getPointerCount();i++){
    		sb.append("#").append(i);
    		sb.append("pid ").append(event.getPointerId(i));
    		sb.append(")=").append((int)event.getX(i));
    		sb.append(",").append((int)event.getY(i));
    		if(i+1<event.getPointerCount())
    			sb.append(";");
    	}
    	sb.append("]");
    	Log.d(TAG,sb.toString());
    }

}
