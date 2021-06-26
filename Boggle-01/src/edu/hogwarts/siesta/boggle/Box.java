package edu.hogwarts.siesta.boggle;

import android.graphics.Bitmap;
import android.graphics.Canvas;

class Box {

	static Bitmap bitmap;
	double x=0,y=0;
	double size=1;
	int ballIndex=-1;

	public Box(double x,double y,double size) {
		this.x=x;
		this.y=y;
		this.size=size;
	}

	public void draw(Canvas canvas){
		Graphics.draw(canvas,bitmap,(int)x,(int)y,(int)size);
	}

}

