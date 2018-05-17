package edu.uci.assignment3;

import android.content.Context;
//import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.*;//.MotionEvent;
import java.util.Random;
import java.util.concurrent.TimeUnit;
//import android.view.SurfaceHolder;
//import android.view.SurfaceView;

public class BoardView extends SurfaceView implements SurfaceHolder.Callback{

    Boolean exists;
    int clicked = 0;
    enum direction {up, down, right, left};
    direction d;
    int x_initial, y_initial, x_final, y_final, c_width, c_height, score = 0, r;
    Candy candies[][] = new Candy[9][9];

    Paint paint = new Paint();
    Random rand;

    Bitmap mybitmap = BitmapFactory.decodeResource(getResources(), R.drawable.jellybean);

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
        rand = new Random();
        rand.setSeed(123456789);
        paint.setColor(Color.BLACK);
        paint.setTextSize(250);
    }

    public void draw_it(Canvas c){
        c.drawColor(Color.WHITE); // Set the background to white
        //Rect dst=new Rect() ;
        //dst.set(10 , 30, 20, 40) ; // Set window to place image from (10 ,30) to (20 ,40)
        //c.drawBitmap ( mybitmap , null , dst , null ) ; // Draw the bitmap

        for(int x = 0; x < 9; x++){
            for(int y = 0; y < 9; y++){
                candies[x][y].draw(c);
            }
        }
        c.drawText(Integer.toString(score), 0, c_height+paint.getTextSize(), paint);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        Canvas c = holder.lockCanvas();

        c_width = c.getWidth();
        c_height = c.getHeight()-250;

        for(int x = 0; x < 9; x++){
            for(int y = 0; y < 9; y++){
                candies[x][y] = new Candy(getResources(), randomCandy(), x * (c_width / 9), y * (c_height / 9), c_width / 9, c_height / 9, r);
            }
        }

        draw_it(c);
        holder.unlockCanvasAndPost(c);

        this.exists = true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder , int format , int width , int height){
        

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){

    }

    @Override
    public boolean onTouchEvent(MotionEvent e){
        if ((e.getAction() == MotionEvent.ACTION_DOWN)&&(clicked == 0)) {
            x_initial = (int) e.getX();
            y_initial = (int) e.getY();

            x_initial = round(x_initial, true);  //find out what section this click was made in
            y_initial = round(y_initial, false);

            clicked++;  //let program know this is an intial click(candy to be moved)
        }
        else if ((e.getAction() == MotionEvent.ACTION_DOWN)&&(clicked == 1)) {
            x_final = (int) e.getX();
            y_final = (int) e.getY();

            x_final = round(x_final, true);
            y_final = round(y_final, false);

            /*Bitmap temp = candies[x_initial][y_initial].pic;
            candies[x_initial][y_initial].pic = candies[x_final][y_final].pic;
            candies[x_final][y_final].pic = temp;*/
            clicked++;  //let program know that this is a secondary click(candy to be replaced)
        }
        else if(e.getAction() == MotionEvent.ACTION_UP && (clicked == 2)){
            Canvas c = getHolder().lockCanvas();  //prep canvas for being drawn upon

            move(x_initial, y_initial, x_final, y_final);

            shiftCheck();
            draw_it(c);
            getHolder().unlockCanvasAndPost(c);
            clicked = 0; //reset clicked back down to 0 to let the program know user is finished making a move.
        }

        return true;
    }

    //this function returns the section that a click was made at
    public int round(int x, Boolean horizontal){
        int sectionX = c_width/9;
        int sectionY = c_height/9;
        int initial, dest = 0;

        if(horizontal) {
            for (int i = 0; i < 9; i++){
                initial = dest;
                dest += sectionX;
                if(x < dest && x > initial){
                    x = i;
                    break;
                }
            }
        }
        else{
            for (int i = 0; i < 9; i++){
                initial = dest;
                dest += sectionY;
                if(x < dest && x > initial){
                    x = i;
                    break;
                }
            }
        }

        return x;
    }

    public void shiftCheck(){  //this method checks to see if any candies are marked to be replaced.
        for(int x = 0; x < 9; x++){
            for(int y =0; y < 9; y++){
                if (candies[x][y].getMark()){
                    for(int z = y; z > 0; z--){
                        candies[x][z].pic = candies[x][z-1].pic; //if a marked candy is found, all of the images above it are shifted down to it.
                        candies[x][z].id = candies[x][z-1].id;
                    }
                    candies[x][0].pic = BitmapFactory.decodeResource(getResources(),randomCandy());  //the top most image in the column is replaced with a random
                    candies[x][0].id = r;
                }
            }
        }
    }

    public int randomCandy(){  //returns the id of a random pic in resources.
        r = rand.nextInt(6);
        int x;

        switch (r){
            case 0: x = R.drawable.orange;
            break;
            case 1: x = R.drawable.doughnut;
            break;
            case 2: x = R.drawable.tootsieroll;
            break;
            case 3: x = R.drawable.jellybean;
            break;
            case 4: x = R.drawable.recess;
            break;
            case 5: x = R.drawable.oreo;
            break;
            default: x = R.drawable.doughnut;
            break;
        }
        return x;
    }


    public void move(int x1,int y1,int x2,int y2){ //moves two candies if their move is valid
        Bitmap pic1 = candies[x1][y1].pic;
        int    i1   = candies[x1][y1].id;
        Bitmap pic2 = candies[x2][y2].pic;
        int    i2   = candies[x2][y2].id;

        candies[x2][y2].pic = pic1;
        candies[x2][y2].id  = i1;
        candies[x1][y1].pic = pic2;
        candies[x1][y1].id  = i2;      //swap the pics and ids of the candies in question

        if(!validMove()){   //validity check
            candies[x2][y2].pic = pic2;
            candies[x2][y2].id  = i2;
            candies[x1][y1].pic = pic1;
            candies[x1][y1].id  = i1;
        }
    }

    //checks if the move is valid
    public boolean validMove(){
        int temp;
        int count = 1;
        Boolean hit = false;
        for(int x = 0; x < 9; x++){
            for(int y = 0; y < 9; y++){
                if(!candies[x][y].getMark()) {
                    temp = candies[x][y].id; //we check every individual candy, unless it is already marked.

                    //horizontal check
                    for (int xn = x+1; xn < 9; xn++) {
                        if (temp == candies[xn][y].id) {
                            count++;           //count how many candies match with the current one.
                        } else {
                            break;           //once a different candy is detected, break this loop.
                        }
                    }
                    if (count >= 3) {    //If count counted that at least 3 candies were lined up, then we mark them and tell code we found a hit.
                        while (count > 0) {
                            count--;
                            candies[x + count][y].mark();
                        }
                        hit = true;
                    }
                    count = 1;  //reset count

                    //vertical check
                    for (int yn = y+1; yn < 9; yn++) {
                        if (temp == candies[x][yn].id) {
                            count++;           //count how many candies match with the current one.
                        } else {
                            break;           //once a different candy is detected, break this loop.
                        }
                    }
                    if (count >= 3) {    //If count counted that at least 3 candies were lined up, then we mark them and tell code we found a hit.
                        while (count > 0) {
                            count--;
                            candies[x][y + count].mark();
                        }
                        hit = true;
                    }
                    count = 1; //reset count
                }
            }
        }
        return hit;
    }

}
