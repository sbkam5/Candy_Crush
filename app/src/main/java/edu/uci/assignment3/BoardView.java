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

    Bitmap mybitmap = BitmapFactory.decodeResource(getResources(), R.drawable.orange);

    public BoardView(Context c, AttributeSet a, int defStyle){
        super(c, a, defStyle);

    }

    public BoardView(Context c, AttributeSet a){
        super(c, a);
    }

    public BoardView(Context c){
        super(c);

        getHolder().addCallback(this); //notify surface holder that you would like to receive Surfaceholder callbacks
        setFocusable(true);  //Important. For some reason


    }

    @Override
    protected void onDraw(Canvas c){
        c.drawColor(Color.BLACK); // Set the background to black
        Rect dst=new Rect() ;
        dst.set(10 , 30, 20, 40) ; // Set window to place image from (10 ,30) to (20 ,40)
        c.drawBitmap ( mybitmap , null , dst , null ) ; // Draw the bitmap
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        this.exists = true;

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder , int format , int width , int height){

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){

    }
}
