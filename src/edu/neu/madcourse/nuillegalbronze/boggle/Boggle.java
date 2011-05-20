package edu.neu.madcourse.nuillegalbronze.boggle;

import edu.neu.madcourse.nuillegalbronze.R;
import edu.neu.madcourse.nuillegalbronze.sudoku.PuzzleView;
import android.app.Activity;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Boggle extends Activity {

	private static final String TAG = "Boggle";
	private ViewGroup boggleView;

	// dimensions of tiles on the board
	public static final int DICE_WIDTH = 4;
	public static final int DICE_HEIGHT = 4;
	   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
	      super.onCreate(savedInstanceState);
	      setContentView(R.layout.boggle_main);	      
	      Log.d(TAG, "onCreate");		
	      
//	      View continueButton = findViewById(R.id.continue_button);
	//      continueButton.setOnClickListener(this);

	      boggleView = new LinearLayout(this);
	      boggleView.addView(new BoggleStatusView(this));
	      boggleView.addView(new BoggleTileView(this));
	      boggleView.addView(new BoggleMenuButtonView(this));

	      setContentView(boggleView);
	      boggleView.requestFocus();	      
	}
	
	

    	
}
