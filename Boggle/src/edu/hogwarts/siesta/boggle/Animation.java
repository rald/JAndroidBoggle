package edu.hogwarts.siesta.boggle;



import android.content.Context;
//import android.content.SharedPreferences;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
// import android.graphics.Rect;

//import android.media.AudioAttributes;
//import android.media.AudioManager;
//import android.media.MediaPlayer;
//import android.media.SoundPool;

import android.view.View;
import android.view.MotionEvent;

// import android.widget.Toast;

//import java.io.BufferedReader;
//import java.io.InputStream;
//import java.io.InputStreamReader;

import java.util.Random;



public class Animation extends View {

	static final int SCREEN_WIDTH=768;
	static final int SCREEN_HEIGHT=1024;

	static final int BOARD_WIDTH=4;
	static final int BOARD_HEIGHT=4;

	static String dice[] = {
		"AAEEGN",
		"ABBJOO",
		"ACHOPS",
		"AFFKPS",
		"AOOTTW",
		"CIMOTU",
		"DEILRX",
		"DELRVY",
		"DISTTY",
		"EEGHNW",
		"EEINSU",
		"EHRTVW",
		"EIOSST",
		"ELRTTY",
		"HIMNUQ",
		"HLNNRZ"
	};

	static Random random = new Random();

	boolean gameover;

	Context context;

	GameState gameState;

	TouchState touchState=null;
	double touchX=0,touchY=0;

	int offsetX=0,offsetY=0;
	int offsetBoardX=0,offsetBoardY=0;

	char[] board;

	Bitmap font;

	Bitmap big_box;
	Bitmap big_ball;

	Bitmap n_arrow;
	Bitmap e_arrow;
	Bitmap s_arrow;
	Bitmap w_arrow;

	Bitmap ne_arrow;
	Bitmap nw_arrow;
	Bitmap se_arrow;
	Bitmap sw_arrow;

	Bitmap list_button_up;
	Bitmap close_button_up;

	Bitmap list_button_down;
	Bitmap close_button_down;

	boolean isListButtonDown;

	Box[] boxes;
	Ball[] balls;

	Button listButton;

	long startTime=0;
	long elapsedTime=0;
	long remainingTime=0;
	long allottedTime=0;
	long remainingSecs=0;
	long prevRemainingSecs=0;

	int score=0;
	int highscore=0;



	public Animation(Context context) {

		super(context);

		this.context=context;

		setBackgroundColor(Color.BLACK);

		font=BitmapFactory.decodeResource(context.getResources(),R.drawable.font);

		big_box=BitmapFactory.decodeResource(context.getResources(),R.drawable.big_box);
		big_ball=BitmapFactory.decodeResource(context.getResources(),R.drawable.big_ball);

		n_arrow=BitmapFactory.decodeResource(context.getResources(),R.drawable.n_arrow);
		e_arrow=BitmapFactory.decodeResource(context.getResources(),R.drawable.e_arrow);
		s_arrow=BitmapFactory.decodeResource(context.getResources(),R.drawable.s_arrow);
		w_arrow=BitmapFactory.decodeResource(context.getResources(),R.drawable.w_arrow);

		ne_arrow=BitmapFactory.decodeResource(context.getResources(),R.drawable.ne_arrow);
		nw_arrow=BitmapFactory.decodeResource(context.getResources(),R.drawable.nw_arrow);
		se_arrow=BitmapFactory.decodeResource(context.getResources(),R.drawable.se_arrow);
		sw_arrow=BitmapFactory.decodeResource(context.getResources(),R.drawable.sw_arrow);

		list_button_up=BitmapFactory.decodeResource(context.getResources(),R.drawable.list_button_up);
		close_button_up=BitmapFactory.decodeResource(context.getResources(),R.drawable.close_button_up);

		list_button_down=BitmapFactory.decodeResource(context.getResources(),R.drawable.list_button_down);
		close_button_down=BitmapFactory.decodeResource(context.getResources(),R.drawable.close_button_down);

		isListButtonDown=false;

		Ball.font=font;
		Ball.bitmap=big_ball;
		Box.bitmap=big_box;

		gameState=GameState.GAME_INIT;
	}



	@Override
	public void onDraw(Canvas canvas) {

		super.onDraw(canvas);



		if(gameState==GameState.GAME_INIT) {

			offsetX=(canvas.getWidth()-SCREEN_WIDTH)/2;
			offsetY=(canvas.getHeight()-SCREEN_HEIGHT)/2;

			offsetBoardX=128+offsetX;
			offsetBoardY=256+16+offsetX+32+16;

			shuffleDice();

			board=new char[BOARD_WIDTH*BOARD_HEIGHT];

			int k=0;
			for(int j=0;j<BOARD_HEIGHT;j++) {
				for(int i=0;i<BOARD_WIDTH;i++) {
					board[k]=dice[k].charAt(random.nextInt(dice[k].length()));
					k++;
				}
			}

			boxes=new Box[BOARD_WIDTH*BOARD_HEIGHT];

			k=0;
			for(int j=0;j<BOARD_HEIGHT;j++) {
				for(int i=0;i<BOARD_WIDTH;i++) {
					int size=8;
					int x=i*Box.bitmap.getWidth()*size+offsetBoardX;
					int y=j*Box.bitmap.getHeight()*size+offsetBoardY;
					boxes[k]=new Box(x,y,size);
					k++;
				}
			}

			balls=new Ball[BOARD_WIDTH*BOARD_HEIGHT];

			k=0;
			for(int j=0;j<BOARD_HEIGHT;j++) {
				for(int i=0;i<BOARD_WIDTH;i++) {
					int size=8;
					int x=i*Ball.bitmap.getWidth()*size+offsetBoardX;
					int y=j*Ball.bitmap.getHeight()*size+offsetBoardY;
					balls[k]=new Ball(board[k],x,y,size);
					k++;
				}
			}


			int x=offsetX+SCREEN_WIDTH-8*16-4;
			int y=offsetY+SCREEN_HEIGHT-8*16-4;

			listButton=new Button(list_button_up,list_button_down,x,y,8);

			startTime=System.currentTimeMillis();
			elapsedTime=0;
			remainingTime=0;
			allottedTime=3*60*1000;

			gameover=false;

			gameState=GameState.GAME_PLAY;

		}



		if(!gameover) {
			elapsedTime=System.currentTimeMillis()-startTime;
			remainingTime=allottedTime-elapsedTime;
			if(remainingTime<0) remainingTime=0;
		}



		Paint paint = new Paint();

		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.parseColor("#00AAFF"));
		paint.setFilterBitmap(false);

		canvas.drawRect(offsetX,offsetY,offsetX+SCREEN_WIDTH,offsetY+SCREEN_HEIGHT,paint);



		if(gameState==GameState.GAME_PLAY) {



			int k=0;

/*
[			for(int j=0;j<BOARD_HEIGHT;j++) {
				for(int i=0;i<BOARD_WIDTH;i++) {
					int size=8;
					int charWidth=8;
					int charHeight=8;
					int x=i*charWidth*size+offsetX;
					int y=j*charHeight*size+offsetY;
					Graphics.drawChar(canvas,font,charWidth,charHeight,board[k],x,y,size);
					k++;
				}
			}
*/

			k=0;
			for(int j=0;j<BOARD_HEIGHT;j++) {
				for(int i=0;i<BOARD_WIDTH;i++) {
					boxes[k++].draw(canvas);
				}
			}


/*
			int x=offsetX-32;
			int y=offsetY-32;

			Graphics.draw(canvas,n_arrow,x,y,8);
			Graphics.draw(canvas,e_arrow,x,y,8);
			Graphics.draw(canvas,w_arrow,x,y,8);
			Graphics.draw(canvas,s_arrow,x,y,8);
			Graphics.draw(canvas,ne_arrow,x,y,8);
			Graphics.draw(canvas,nw_arrow,x,y,8);
			Graphics.draw(canvas,se_arrow,x,y,8);
			Graphics.draw(canvas,sw_arrow,x,y,8);

*/

			k=0;
			for(int j=0;j<BOARD_HEIGHT;j++) {
				for(int i=0;i<BOARD_WIDTH;i++) {
					balls[k++].draw(canvas);
				}
			}

			listButton.draw(canvas);

			isListButtonDown=listButton.update(canvas,touchX,touchY,touchState);


		}





		Graphics.drawText(canvas,font,8,8,String.format("Hi    %6d",highscore),offsetX,offsetY,8);

		Graphics.drawText(canvas,font,8,8,String.format("Score %6d",score),offsetX,offsetY+1*8*8,8);

		long min=(remainingTime/1000/60);
		long sec=(remainingTime-min*1000*60)/1000;

		Graphics.drawText(canvas,font,8,8,String.format("Time  %3d:%02d",min,sec),offsetX,offsetY+2*8*8,8);

		Graphics.drawText(canvas,font,8,8,String.format("Words %6s",0+"/"+0),offsetX,offsetY+3*8*8,8);

		invalidate();

	}



	@Override
	public boolean onTouchEvent(MotionEvent event) {
		touchX=event.getX();
		touchY=event.getY();
		switch(event.getAction()) {
			case MotionEvent.ACTION_DOWN: touchState=TouchState.TOUCH_DOWN; break;
			case MotionEvent.ACTION_MOVE: touchState=TouchState.TOUCH_MOVE; break;
			case MotionEvent.ACTION_UP:   touchState=TouchState.TOUCH_UP;   break;
		}
		return true;
	}



	static void shuffleDice() {
		for(int i=dice.length-1;i>0;i--) {
			int j=random.nextInt(i+1);
			String t=dice[i];
			dice[i]=dice[j];
			dice[j]=t;
		}
	}



}



