package edu.neu.madcourse.nuillegalbronze.boggle;

import edu.neu.madcourse.nuillegalbronze.R;
import edu.neu.madcourse.nuillegalbronze.sudoku.Game;
import edu.neu.madcourse.nuillegalbronze.sudoku.Prefs;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class BoggleView extends View{
	
	private String TAG="BoggleView";
	private int width= (int) Math.round((getWidth() * 0.92 / 4));    // width of one tile
	private int height=width;   // height of one tile
	private int selX;       // X index of selection
	private int selY;       // Y index of selection
	
	private boolean refreshNeeded;
	
	private int upper_bound;
	private int lower_bound;
	private int left_bound;
	private int right_bound;
	private final Rect selRect = new Rect();	
	private Bitmap bitmap;
	private ShapeDrawable rect=new ShapeDrawable();

	static final int NONE=0;
	static final int DRAG=1;
	static final int ZOOM=2;
	int mode=NONE;
	PointF start=new PointF();
	PointF mid=new PointF();
	float oldDist=1f;		
	private TileView[] tiles;

//	private final Boggle game;

	public BoggleView(Context context) {
		super(context);
		refreshNeeded=true;
		tiles=new TileView[16];
		for(int i=0;i<16;i++){
			tiles[i]=new TileView(context);
		}
	//	this.game = (Boggle) context;
	    setFocusable(true);
	    setFocusableInTouchMode(true);
	    
		Log.d("getw",Float.toString(getWidth()));
	    Log.d(TAG,Float.toHexString(width));
	    
//	    upper_bound = (getHeight() - 4 * width ) * 3/8;
//	    lower_bound = getHeight() - 4 * width-upper_bound;
//	    left_bound = (float) (getWidth()*0.02);
//	    right_bound = (float) (getWidth()*0.98);
	}
	

	@Override
	public void onDraw(Canvas canvas) {

		   width = (int) Math.round((getWidth() * 0.92 / 4));
		   height = (int) Math.round((getHeight() * 0.15 ));
	   upper_bound = (int)Math.round((getHeight() - 4 * height ) / 2);
	   lower_bound = upper_bound + 4 * height;
	   left_bound = (int) Math.round((getWidth()*0.04));
	   right_bound = left_bound + 4 * width;
	  bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

	  
	   // Draw the background...
	   Paint background = new Paint();
	   background.setColor(getResources().getColor(
	         R.color.puzzle_background));
	   canvas.drawRect(0, 0, getWidth(), getHeight(), background);

	   rect.setBounds(left_bound, lower_bound, left_bound+width, lower_bound-height);
	   rect.getPaint().setColor(Color.GREEN);
Log.d("ondraw","sth");
	   // Draw the board...
	      
       // Define colors for the grid lines
       Paint dark = new Paint();
       dark.setColor(getResources().getColor(R.color.puzzle_dark));

       Paint hilite = new Paint();
       hilite.setColor(getResources().getColor(R.color.puzzle_hilite));
       
       Paint light = new Paint();
	   light.setColor(getResources().getColor(R.color.puzzle_light));

//Log.d("upper_bound",Float.toString(upper_bound));
//Log.d("lower_bound",Float.toString(lower_bound));
//Log.d("left_bound",Float.toString(left_bound));
//Log.d("right_bound",Float.toString(right_bound) );
	   for(int i=0;i<5;i++){
		   canvas.drawLine(left_bound,
				   upper_bound + i * height,
				   right_bound,
				   upper_bound + i * height,
				   dark);
		   canvas.drawLine(left_bound + i * width,
				   lower_bound,
				   left_bound + i * width,
				   upper_bound,
				   dark);
		   
	   }
	   

	      // Define color and style for numbers
	      Paint foreground = new Paint(Paint.ANTI_ALIAS_FLAG);
	      foreground.setColor(getResources().getColor(
	            R.color.puzzle_foreground));
	      foreground.setStyle(Style.FILL);
	      foreground.setTextSize(height * 0.75f);
	      foreground.setTextScaleX(width / height);
	      foreground.setTextAlign(Paint.Align.CENTER);	      
	      // Draw the selection...
	      Log.d(TAG, "selRect=" + selRect);
	      Paint selected = new Paint();
	      selected.setColor(getResources().getColor(
	            R.color.puzzle_selected));
	      canvas.drawRect(selRect, selected);
	   }	

	public boolean onTouchEvent(MotionEvent event){
		dumpEvent(event);
		switch(event.getAction()&MotionEvent.ACTION_MASK){
		case MotionEvent.ACTION_DOWN:
			select((int) ((event.getX()-left_bound) / width),
		            (int) ((event.getY()-upper_bound) / height));			
//			savedMatrix.set(matrix);
//			start.set(event.getX(),event.getY());
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
				select((int) ((event.getX()-left_bound) / width),
			            (int) ((event.getY()-upper_bound) / height));
			}
			break;
			
		
		}
//		view.setImageMatrix(matrix);
		return true;		
	}

	   
	private void select(int x, int y){
		selX = Math.min(Math.max(x, 0), 3);
		selY = Math.min(Math.max(y, 0), 3);
		getRect(selX, selY, selRect);
	      Paint selected = new Paint();
	      selected.setColor(getResources().getColor(
	            R.color.puzzle_selected));
	      
	 //   rect=new ShapeDrawable();  
	    rect.setBounds(selRect);
	    rect.getPaint().setColor(getResources().getColor(
	            R.color.puzzle_selected));

		invalidate(selRect);

		
	}
	
	private void getRect(int x, int y, Rect rect){
		rect.set(left_bound+(x*width),upper_bound+(y*height),
				left_bound+(x*width+width),upper_bound+(y*height)+height);
		
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
