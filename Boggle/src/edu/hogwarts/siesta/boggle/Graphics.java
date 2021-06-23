package edu.hogwarts.siesta.boggle;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

class Graphics {

	static void draw(Canvas canvas,Bitmap bitmap,int x,int y,int size) {
		int w=bitmap.getWidth();
		int h=bitmap.getHeight();
		int sx0=0;
		int sy0=0;
		int sx1=sx0+w;
		int sy1=sy0+h;
		int dx0=x;
		int dy0=y;
		int dx1=dx0+w*size;
		int dy1=dy0+h*size;
		Rect src=new Rect(sx0,sy0,sx1,sy1);
		Rect dst=new Rect(dx0,dy0,dx1,dy1);
		Paint paint=new Paint();
		paint.setFilterBitmap(false);
		canvas.drawBitmap(bitmap,src,dst,paint);
	}

	static void drawChar(Canvas canvas,Bitmap font,int w,int h,char ch,int x,int y,int size) {
		int sx0=(ch%16)*w;
		int sy0=(ch/16)*h;
		int sx1=sx0+w;
		int sy1=sy0+h;
		int dx0=x;
		int dy0=y;
		int dx1=dx0+w*size;
		int dy1=dy0+h*size;
		Rect src=new Rect(sx0,sy0,sx1,sy1);
		Rect dst=new Rect(dx0,dy0,dx1,dy1);
		Paint paint=new Paint();
		paint.setFilterBitmap(false);
		canvas.drawBitmap(font,src,dst,paint);
	}

	static void drawText(Canvas canvas,Bitmap font,int w,int h,String text,int x,int y,int size) {
		int posx=x;
		int posy=y;
		for(int i=0;i<text.length();i++) {
			drawChar(canvas,font,w,h,text.charAt(i),posx,posy,size);
			posx+=w*size;
			if(posx+w*size>=canvas.getWidth()) {
				posx=0;
				posy+=h*size;
			}
		}
	}

	static void wave(Canvas canvas,Bitmap font,int w,int h,String text,int cx,int cy,int size,long startTime) {
		long elapsedTime=System.currentTimeMillis()-startTime;
		double pos=elapsedTime*0.125;
		for(int i=0;i<text.length();i++) {
			double posx=(pos+w*size*i)%(Animation.SCREEN_WIDTH-w*size)+cx;
			double posy=h*size+Math.sin(pos/(h*size/2)+i)*(h*size/2)+cy;
			drawChar(canvas,font,w,h,text.charAt(i),(int)posx,(int)posy,size);
		}
	}

	static boolean inrect(int x,int y,int rx,int ry,int w,int h) {
		return x>=rx && x<=rx+w && y>=ry && y<=ry+h;
	}

}
