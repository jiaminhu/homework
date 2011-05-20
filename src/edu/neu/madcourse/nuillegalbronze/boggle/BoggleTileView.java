package edu.neu.madcourse.nuillegalbronze.boggle;

import edu.neu.madcourse.nuillegalbronze.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

public class BoggleTileView extends View {
	
	protected static int tileWidth;
	protected static int tileHeight;

    protected static int mXTileCount=4;
    protected static int mYTileCount=4;

    private static int mXOffset=0;
    private static int mYOffset=0;


    /**
     * A hash that maps integer handles specified by the subclasser to the
     * drawable that will be used for that reference
     */
    private Bitmap[] mTileArray; 

    /**
     * A two-dimensional array of integers in which the number represents the
     * index of the tile that should be drawn at that locations
     */
    private int[][] mTileGrid;
    
    private final Paint mPaint = new Paint();	
    public BoggleTileView(Context context) {
        super(context);
	}
    
    public void setSize(int width,int height){
    	tileWidth=width;
    	tileHeight=height;
    }

    public void resetTiles(int tilecount) {
    	mTileArray = new Bitmap[tilecount];
    }
    
    public void loadTile(Drawable tile) {
        Bitmap bitmap = Bitmap.createBitmap(tileWidth, tileHeight, Bitmap.Config.ARGB_8888);
        
        Canvas canvas = new Canvas(bitmap);
        tile.setBounds(0, 0, tileWidth, tileHeight);
        tile.draw(canvas);
    }    
    
    public void clearTiles() {
        for (int x = 0; x < mXTileCount; x++) {
            for (int y = 0; y < mYTileCount; y++) {
                setTile(0, x, y);
            }
        }
    }
    
    public void setTile(int tileindex, int x, int y) {
        mTileGrid[x][y] = tileindex;
    }
    
    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    //    canvas.drawBitmap(bitmap, left, top, mPaint);

  //                  canvas.drawBitmap(mTileArray[mTileGrid[x][y]], 
  //                  		mXOffset + x * tileWidth,
  //                  		mYOffset + y * tileHeight,
  //                  		mPaint);


    }    
}
