package edu.uci.assignment3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.SurfaceView;

public class BoardView extends SurfaceView {

    Bitmap mybitmap = BitmapFactory. decodeResource ( getResources() , R.drawable.ic_launcher_background );

    public BoardView(Context c, AttributeSet a, int defStyle){
        super(c, a, defStyle);
    }

    public BoardView(Context c, AttributeSet a){
        super(c, a);
    }

    public BoardView(Context c){
        super(c);
    }

    protected void onDraw ( Canvas c ) {
        c.drawColor(Color.BLACK); // Set the background to black
        Rect dst = new Rect () ;
        dst.set(10 , 30, 20, 40) ; // Set window to place image from (10 ,30) to (20 ,40)
        c.drawBitmap ( mybitmap , null , dst , null ) ; // Draw the bitmap
        }
}
