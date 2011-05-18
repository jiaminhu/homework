package edu.neu.madcourse.nuillegalbronze;

import edu.neu.madcourse.nuillegalbronze.R;
import edu.neu.madcourse.nuillegalbronze.sudoku.About;
import edu.neu.madcourse.nuillegalbronze.sudoku.Sudoku;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class Main extends Activity implements OnClickListener {

	   public void onCreate(Bundle savedInstanceState) {
		      super.onCreate(savedInstanceState);
		      setContentView(R.layout.main);
		      
		      AuthorizationChecker.DoAuthorization(getApplicationContext(), this, true);
		      
		      // Set up click listeners for all the buttons
		      View teamMembersButton = findViewById(R.id.team_members_button);
		      teamMembersButton.setOnClickListener(this);
		      View sudokuButton = findViewById(R.id.sudoku_button);
		      sudokuButton.setOnClickListener(this);
		      View createErrorButton = findViewById(R.id.create_error_button);
		      createErrorButton.setOnClickListener(this);
		      View exitButton = findViewById(R.id.exit_button);
		      exitButton.setOnClickListener(this);
		   }
	   
	   public void onClick(View v) {
		      switch (v.getId()) {
		      case R.id.team_members_button:
		         Intent intent = new Intent(this, Members.class);
		         intent.putExtra(Members.PHONE_ID,
		        		 AuthorizationChecker.GetID(getApplicationContext()));
		         startActivity(intent);
		         break;
		         // ...
		      case R.id.sudoku_button:
		         Intent i = new Intent(this, Sudoku.class);
		         startActivity(i);
		         break;
		      // More buttons go here (if any) ...
		      case R.id.create_error_button:
		    	 startActivity(null);
		         break;
		      case R.id.exit_button:
		         finish();
		         break;
		      }
		   }	   
}
