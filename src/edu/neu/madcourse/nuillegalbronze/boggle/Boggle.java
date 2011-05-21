package edu.neu.madcourse.nuillegalbronze.boggle;

import java.util.ArrayList;
import java.util.List;

import edu.neu.madcourse.nuillegalbronze.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class Boggle extends Activity {

	private static final String TAG = "Boggle";
	private BoggleView boggleView;

	// dimensions of tiles on the board
	public static final int DICE_WIDTH = 4;
	public static final int DICE_HEIGHT = 4;
	   
	private Tile[] tiles;
	private ArrayList<Tile> selectedTiles;
	private int score = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
	      super.onCreate(savedInstanceState);
	      setContentView(R.layout.boggle_main);	      
	      Log.d(TAG, "onCreate");		
	      initializeTiles();
	
	      boggleView = new BoggleView(this);
	      setContentView(boggleView);
	      boggleView.requestFocus();	      
	}
	
	private void initializeTiles() {
		//TODO:
	      Tile a = new Tile('A', false);
	      Tile b = new Tile('B', false);
	      Tile c = new Tile('C', false);
	      Tile d = new Tile('D', false);
	      
	      Tile e = new Tile('E', false);
	      Tile f = new Tile('F', false);
	      Tile g = new Tile('G', false);
	      Tile h = new Tile('H', false);
	      
	      Tile i = new Tile('I', false);
	      Tile j = new Tile('J', false);
	      Tile k = new Tile('K', false);
	      Tile l = new Tile('L', false);
	      
	      Tile m = new Tile('M', false);
	      Tile n = new Tile('N', false);
	      Tile o = new Tile('O', false);
	      Tile p = new Tile('P', false);
	      
	      tiles = new Tile[] { a, b, c, d,
	    		  e, f, g, h,
	    		  i, j, k, l,
	    		  m, n, o, p };
	      selectedTiles = new ArrayList<Tile>();
	}
	
	protected int getScore() {
		return score;
	}
	
	int oldx;
	int oldy;
	
	public void selectTile(int x, int y) {
		if (oldx != x || oldy != y) {
			Tile t = getTile(x, y);
			
			if (selectedTiles.contains(t)) {
				selectedTiles.remove(t);
				Log.d(TAG, "REMOVING TILE: " + x + ", " + y);
				t.setSelected(false);
			} else {
				selectedTiles.add(t);
				t.setSelected(true);
				Log.d(TAG, "ADDING TILE: " + x + ", " + y);
			}
		}
		
		oldx = x;
		oldy = y;
	}
	
	public List<Tile> getSelectedTiles() {
		return selectedTiles;
	}

	/** Return a string for the tile at the given coordinates */
	public String getTileString(int x, int y) {
		return String.valueOf(getTile(x, y));
	}

	/** Return the tile at the given coordinates */
	public Tile getTile(int x, int y) {
		return tiles[y * DICE_HEIGHT + x];
	}
}
