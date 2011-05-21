package edu.neu.madcourse.nuillegalbronze.boggle;

import edu.neu.madcourse.nuillegalbronze.R;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.view.View;

public class Tile {
	private char letter;
	private boolean selected;
	
	
	public Tile(char letter, boolean selected) {
		this.letter = letter;
		this.selected = selected;
	}
	
	public char getLetter() {
		return letter;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public void draw(View view, Canvas canvas, Rect rect, float height, float width) {
		// Draw the dice
		Drawable myImage = view.getResources().getDrawable(R.drawable.blank_die);
		myImage.setBounds(rect);
		myImage.draw(canvas);

		if (selected) {
			Paint p = new Paint();
			p.setColor(R.color.puzzle_selected);
			canvas.drawRect(rect, p);
		}

		// Draw dice letters.
		Paint foreground = new Paint(Paint.ANTI_ALIAS_FLAG);
		foreground.setColor(view.getResources().getColor(R.color.puzzle_foreground));
		foreground.setStyle(Style.FILL);
		foreground.setTextSize(height * 0.75f);
		foreground.setTextScaleX(width / height);
		foreground.setTextAlign(Paint.Align.CENTER);

		FontMetrics fm = foreground.getFontMetrics();

		canvas.drawText(String.valueOf(letter), 
				rect.left + width / 2, 
				rect.top + height / 2 - (fm.ascent + fm.descent) / 2, 
				foreground);
	}
}
