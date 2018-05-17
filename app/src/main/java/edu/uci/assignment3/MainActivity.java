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

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void randomMatrix() {
        Random rand = new Random();
        for (int i = 0; i < dim; i++){
            for (int j = 0; j < dim; j++){
                candyIndex[i][j] = rand.nextInt(candyType);
            }
        }
    }

    //moves candy
    public void Move(int x1,int y1,int x2,int y2){
        int pos1 = candyIndex[x1][y1];
        int pos2 = candyIndex[x2][y2];

        candyIndex[x2][y2] = pos1;
        candyIndex[x1][y1] = pos2;

        if(!validMove(x1, y1, x2, y2)){
            candyIndex[x2][y2] = pos2;
            candyIndex[x1][y1] = pos1;
        }
    }

    //checks if the move is valid
    public boolean validMove(int x1,int y1,int x2,int y2){
        if (x1>-1 && x1<dim && y1>-1 && y1<dim && x2>-1 && x2<dim && y2>-1 && y2<dim && checkSurrounding(x1, y1) || checkSurrounding(x2, y2)){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean checkSurrounding(int x, int y){

        int same_candy = 0;

        // checking right
        if(dim - y > 2){

            // if the candies are equal, return true
            if(candyIndex[x][y] == candyIndex[x][y+1] && candyIndex[x][y] == candyIndex[x][y+2]){
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

            if(candyIndex[x][y]==candyIndex[x][y-1] && candyIndex[x][y] == candyIndex[x][y-2]){
                return true;
            }
            else
                same_candy++;
        }
        else
            same_candy++;

        // checking up
        if(x > 1){

            if(candyIndex[x][y] == candyIndex[x-1][y] && candyIndex[x][y] == candyIndex[x-2][y]){
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
        if(dim - x > 2){

            if(candyIndex[x][y] == candyIndex[x+1][y] && candyIndex[x][y] == candyIndex[x+2][y]){
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
        if(dim - y > 1 && y > 0){

            if(candyIndex[x][y] == candyIndex[x][y+1] && candyIndex[x][y] == candyIndex[x][y-1]){
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
        if(dim - x > 1 && x > 0){

            // if equal, return true
            if(candyIndex[x][y] == candyIndex[x+1][y] && candyIndex[x][y] == candyIndex[x-1][y]){
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
