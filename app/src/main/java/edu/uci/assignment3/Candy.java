package edu.uci.assignment3;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Candy {
    private int x, y, width, height;
    public int id;
    Bitmap pic;
    Rect dest;
    private Boolean marked = false;


    public Candy(Resources res, int id, int x, int y, int width, int height, int r){
        pic = BitmapFactory.decodeResource(res, id);
        this.id = r;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.dest = new Rect();
    }

    public void setLocation(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void setSize(int w, int h){
        width = w;
        height = h;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public void draw(Canvas c){
        dest.set(x,y, x+width, y+height);
        c.drawBitmap(this.pic, null, dest, null);
    }

    public void mark(){
        marked = true;
    }

    public void unmark(){
        marked = false;
    }

    public Boolean getMark(){
        return marked;
    }
}
