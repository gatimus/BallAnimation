package io.github.gatimus.ballanimation;

import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.Log;

public class Ball {

    private static final String TAG = "Ball:";
    public static final int BALL = 0;
    public static final int SHADOW = 1;
    public PointF position;
    public float radius;
    public Paint paint;

    public Ball(int type, PointF start, float startRadius){
        Log.v(TAG, "construct");
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        if(type == BALL){
            //TODO random color
            position = start;
            radius = startRadius;
        } else if(type == SHADOW){
            paint.setColor(Color.LTGRAY);
            paint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.INNER));
            position = new PointF(start.x+startRadius-(startRadius/(float)Math.PI), start.y+startRadius-(startRadius/(float)Math.PI));
            radius = 50-(startRadius-49);
        }
        //TODO random directions
    } //constructor

    public Ball getShadow(){
        return new Ball(Ball.SHADOW, position, radius);
    } //getShadow

    public void move(int delta){
        //TODO
    } //move

} //class
