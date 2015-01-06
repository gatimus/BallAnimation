package io.github.gatimus.ballanimation;

import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Comparator;
import java.util.Random;

public class Ball implements Comparable<Ball> {

    @Override
    public int compareTo(@NonNull Ball another) {
        int compareRadius = (int) another.radius;
        return (int)this.radius - compareRadius;
    }

    public static Comparator<Ball> BallCompar = new Comparator<Ball>(){
        @Override
        public int compare(Ball ball1, Ball ball2) {
            return ball1.compareTo(ball2);
        }
    };

    private static final String TAG = "Ball:";
    public static final int BALL = 0;
    public static final int SHADOW = 1;
    public PointF position;
    private PointF viewSize;
    public float radius;
    public Paint paint;
    private boolean directionX;
    private boolean directionY;
    private boolean directionZ;
    private int speed;

    public Ball(int type, PointF viewSize, float startRadius){
        Log.v(TAG, "construct");
        Random random = new Random();
        this.viewSize = viewSize;
        this.position = new PointF(viewSize.x/2, viewSize.y/2);
        this.paint = new Paint();
        this.paint.setStyle(Paint.Style.FILL);
        if(type == BALL){
            this.paint.setARGB(255, random.nextInt(200), random.nextInt(200), random.nextInt(200));
            this.radius = random.nextFloat() * 50 + 50;
        } else if(type == SHADOW){
            this.paint.setColor(Color.LTGRAY);
            this.paint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.INNER));
            this.position = new PointF(viewSize.x+startRadius-(startRadius/(float)Math.PI), viewSize.y+startRadius-(startRadius/(float)Math.PI));
            this.radius = 50-(startRadius-49);
        }
        this.directionX = random.nextBoolean();
        this.directionY = random.nextBoolean();
        this.directionZ = random.nextBoolean();
        this.speed = random.nextInt(20) + 5;
    } //constructor

    public Ball getShadow(){
        return new Ball(Ball.SHADOW, position, radius);
    } //getShadow

    public void move(float speed){
        float delta = this.speed * speed;

        if(directionX){
            position.x = position.x + delta;
        } else{
            position.x = position.x - delta;
        }
        if(directionY){
            position.y = position.y + delta;
        } else{
            position.y = position.y - delta;
        }
        if(directionZ){
            radius = radius + delta/5;
        } else {
            radius = radius - delta/5;
        }

        if(radius >= 100 || radius <= 50){
            directionZ = !directionZ;
        }
        if(position.y + radius >= viewSize.y-delta-2 || position.y - radius <= delta+2){
            directionY = !directionY;
            directionZ = !directionZ;
        }
        if(position.x + radius >= viewSize.x-delta-2 || position.x - radius <= delta+2){
            directionX = !directionX;
            directionZ = !directionZ;
        }

    } //move

} //class
