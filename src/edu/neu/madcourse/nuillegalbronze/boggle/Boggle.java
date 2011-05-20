package edu.neu.madcourse.nuillegalbronze.boggle;

import edu.neu.madcourse.nuillegalbronze.R;
import edu.neu.madcourse.nuillegalbronze.sudoku.PuzzleView;
import android.app.Activity;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class Boggle extends Activity {

	private BoggleView boggleView;
	private String TAG="Boggle";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
	      super.onCreate(savedInstanceState);
	      setContentView(R.layout.boggle_main);	      
	      Log.d(TAG, "onCreate");		
	      
//	      View continueButton = findViewById(R.id.continue_button);
	//      continueButton.setOnClickListener(this);
	      
	      boggleView = new BoggleView(this);
	      setContentView(boggleView);
	      boggleView.requestFocus();	      
	}
	
	

    	
}
