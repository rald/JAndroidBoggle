package edu.hogwarts.siesta.boggle;

import android.graphics.Bitmap;
import android.graphics.Canvas;

class Button {

	ButtonState buttonState=ButtonState.BUTTON_UP;

	Bitmap bitmap_up,bitmap_down;
	Bitmap bitmap_current;
	double x,y;
	double size;

	public Button(Bitmap bitmap_up,Bitmap bitmap_down,double x,double y,double size) {
		this.bitmap_up=bitmap_up;
		this.bitmap_down=bitmap_down;
		this.bitmap_current=bitmap_up;
		this.x=x;
		this.y=y;
		this.size=size;
	}

	public void draw(Canvas canvas){
		Graphics.draw(canvas,bitmap_current,(int)x,(int)y,(int)size);
	}

	public boolean update(Canvas canvas,double touchX,double touchY,TouchState touchState) {
		if(touchState==TouchState.TOUCH_DOWN) {
			if(Graphics.inrect((int)touchX,(int)touchY,(int)x,(int)y,(int)(bitmap_current.getWidth()*size),(int)(bitmap_current.getHeight()*size))) {
				buttonState=ButtonState.BUTTON_DOWN;
				bitmap_current=bitmap_down;
				return true;
			}
		} else {
			buttonState=ButtonState.BUTTON_UP;
			bitmap_current=bitmap_up;
		}
		return false;
	}

}

