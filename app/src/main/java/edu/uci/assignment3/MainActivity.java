package edu.uci.assignment3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    //There must be 6 different candies
    //board must be 9 x 9

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BoardView b = new BoardView(this);
        //setContentView(R.layout.activity_main);
        setContentView(b);
    }
}
