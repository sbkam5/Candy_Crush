package edu.uci.assignment3;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class BoardView extends SurfaceView implements SurfaceHolder.Callback{

    Boolean exists;

    Bitmap mybitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background);

    public BoardView(Context c, AttributeSet a, int defStyle){
        super(c, a, defStyle);

        getHolder().addCallback(this); //notify surface holder that you would like to receive Surfaceholder callbacks
        setFocusable(true);  //Important. For some reason
    }

    public BoardView(Context c, AttributeSet a){
        super(c, a);

        getHolder().addCallback(this); //notify surface holder that you would like to receive Surfaceholder callbacks
        setFocusable(true);  //Important. For some reason
    }

    public BoardView(Context c){
        super(c);

        getHolder().addCallback(this); //notify surface holder that you would like to receive Surfaceholder callbacks
        setFocusable(true);  //Important. For some reason
    }

    public void draw_it(Canvas c){
        c.drawColor(Color.BLACK); // Set the background to black
        Rect dst=new Rect() ;
        dst.set(500 , 1500, 1000, 2000) ; // Set window to place image from (10 ,30) to (20 ,40)
        c.drawBitmap ( mybitmap , null , dst , null ) ; // Draw the bitmap

        Candy[][] candies = new Candy[2][2];
        for(int x = 0; x < 2; x++){
            for(int y = 0; y < 2; y++){
                candies[x][y] = new Candy(getResources(), R.drawable.doughnut, x*50,y*50,50,50);
                candies[x][y].draw(c);
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        Canvas c = holder.lockCanvas();
        this.draw_it(c);
        holder.unlockCanvasAndPost(c);

        this.exists = true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder , int format , int width , int height){
        

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){

    }
}
