package edu.uci.assignment3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.*;

public class MainActivity extends AppCompatActivity {

    //There must be 6 different candies
    //board must be 9 x 9
    public int dim = 9;
    private int candyType = 6;
    private int start_x;
    private Boolean GameStart;
    private int [][]candyIndex;
    Candy candy;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BoardView b = new BoardView(this);
        //setContentView(R.layout.activity_main);
        setContentView(b);
        //candyIndex = new int[dim][dim];
    }


}
