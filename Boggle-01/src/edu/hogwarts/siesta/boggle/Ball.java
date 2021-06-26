package edu.hogwarts.siesta.boggle;

import android.graphics.Bitmap;
import android.graphics.Canvas;

class Ball {

	char letter;
	double x,y;
	double size;
	boolean used=false;

	static Bitmap font;
	static Bitmap bitmap;

	public Ball(char letter,double x,double y,int size) {
		this.letter=letter;
		this.x=x;
		this.y=y;
		this.size=size;
	}

	public void draw(Canvas canvas) {
		Graphics.draw(canvas,bitmap,(int)x,(int)y,(int)size);
		Graphics.drawText(canvas,font,8,8,Character.toString(letter),
				(int)(x+(bitmap.getWidth()*size-8*size)/2),
				(int)(y+(bitmap.getHeight()*size-8*size)/2),
				(int)size);
	}

}

