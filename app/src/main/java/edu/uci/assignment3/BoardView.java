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
    int x_initial, y_initial, x_final, y_final, c_width, c_height, score = 0;
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
                int r = randomCandy();
                candies[x][y] = new Candy(getResources(), r, x * (c_width / 9), y * (c_height / 9), c_width / 9, c_height / 9);
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
                    }
                    candies[x][0].pic = BitmapFactory.decodeResource(getResources(),randomCandy());  //the top most image in the column is replaced with a random
                }
            }
        }
    }

    public int randomCandy(){  //returns the id of a random pic in resources.
        int r = rand.nextInt(6);
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
        Bitmap pic2 = candies[x2][y2].pic;

        candies[x2][y2].pic = pic1;
        candies[x1][y1].pic = pic2;

        if(!validMove(x1, y1, x2, y2)){   //validity check
            candies[x2][y2].pic = pic2;
            candies[x1][y1].pic = pic1;
        }
    }

    //checks if the move is valid
    public boolean validMove(int x1,int y1,int x2,int y2){
        if (x1>-1 && x1<9 && y1>-1 && y1<9 && x2>-1 && x2<9 && y2>-1 && y2<9 && checkSurrounding(x1, y1) || checkSurrounding(x2, y2)){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean checkSurrounding(int x, int y){

        int same_candy = 0;

        // checking right
        if(9 - y > 2){

            // if the candies are equal, return true
            if(candies[x][y].pic == candies[x][y+1].pic && candies[x][y].pic == candies[x][y+2].pic){
                candies[x][y].mark();
                candies[x][y+1].mark();
                candies[x][y+2].mark();
                return true;
            }
            else {
                same_candy++;
            }
        }
        else {
            same_candy++;
        }

        // checking left
        if(y > 1){

            if(candies[x][y].pic ==candies[x][y-1].pic && candies[x][y].pic == candies[x][y-2].pic){
                candies[x][y].mark();
                candies[x][y-1].mark();
                candies[x][y-2].mark();
                return true;
            }
            else
                same_candy++;
        }
        else
            same_candy++;

        // checking up
        if(x > 1){

            if(candies[x][y].pic == candies[x-1][y].pic && candies[x][y].pic == candies[x-2][y].pic){
                candies[x][y].mark();
                candies[x-1][y].mark();
                candies[x-2][y].mark();
                return true;
            }
            else {
                same_candy++;
            }
        }
        else {
            same_candy++;
        }


        // checking down
        if(9 - x > 2){

            if(candies[x][y].pic == candies[x+1][y].pic && candies[x][y].pic == candies[x+2][y].pic){
                candies[x][y].mark();
                candies[x+1][y].mark();
                candies[x+2][y].mark();
                return true;
            }
            else {
                same_candy++;
            }
        }
        else {
            same_candy++;
        }


        // checking one left, one right
        if(9 - y > 1 && y > 0){

            if(candies[x][y].pic == candies[x][y+1].pic && candies[x][y].pic == candies[x][y-1].pic){
                candies[x][y].mark();
                candies[x][y+1].mark();
                candies[x][y-1].mark();
                return true;
            }
            else {
                same_candy++;
            }
        }
        else {
            same_candy++;
        }


        // checking one up, one down
        if(9 - x > 1 && x > 0){

            // if equal, return true
            if(candies[x][y].pic == candies[x+1][y].pic && candies[x][y].pic == candies[x-1][y].pic){
                candies[x+1][y].mark();
                candies[x][y].mark();
                candies[x-1][y].mark();
                return true;
            }
            else {
                same_candy++;
            }
        }
        else {
            same_candy++;
        }

        if(same_candy == 6) {
            return false;
        }
        else {
            return true;
        }

    }
}
