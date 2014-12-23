package io.github.gatimus.ballanimation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Collections;

public class Bounce extends SurfaceView {

    private static final String TAG = "Bounce:";
    private final int FRAME_TIME;
    private SurfaceHolder holder;
    private Handler handler;
    private Runnable run;
    private PointF position;
    private PointF viewSize;
    private float radius;
    private int deltaY;
    private int deltaX;
    private int deltaZ;
    private Paint bg;
    private Paint color;
    private Paint shadow;
    private ArrayList<Ball> balls;

    public Bounce(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                Log.v(TAG, "surfaceDestroyed");
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Log.v(TAG, "surfaceCreated");
                setWillNotDraw(false);
                Canvas c = holder.lockCanvas(null);
                draw(c);
                holder.unlockCanvasAndPost(c);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                viewSize = new PointF(width,height);
                position = new PointF(viewSize.x/2, viewSize.y /2);

                for(int i = 0; i<=15; i++){
                    balls.add(new Ball(0,viewSize,75));
                }
                Log.v(TAG, "surfaceChanged");
            }

        });
        Display display = ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Log.v(TAG, display.toString());
        FRAME_TIME = Math.round(1000/display.getRefreshRate());
        Log.v(TAG, "FRAME_TIME:" + String.valueOf(FRAME_TIME));
        handler = new Handler();
        radius = 75;

        deltaY = 5;
        deltaX = 5;
        deltaZ = 1;
        bg = new Paint();
        bg.setStyle(Paint.Style.FILL);
        bg.setColor(Color.WHITE);
        /*
        color = new Paint();
        color.setStyle(Paint.Style.FILL);
        color.setColor(Color.RED);
        shadow = new Paint();
        shadow.setStyle(Paint.Style.FILL);
        shadow.setColor(Color.LTGRAY);
        shadow.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.INNER));
        */
        balls = new ArrayList<>();

        run = new Runnable(){
            @Override
            public void run() {
                /*
                if(radius >= 100 || radius <= 50){
                    deltaZ = deltaZ * -1;
                }
                if(position.y + radius >= viewSize.y-5 || position.y - radius <= 5){
                    deltaY = deltaY * -1;
                }
                if(position.x + radius >= viewSize.x-5 || position.x - radius <= 5){
                    deltaX = deltaX * -1;
                }
                position.y = position.y + deltaY;
                position.x = position.x + deltaX;
                radius = radius + deltaZ;
                */
                for(Ball ball : balls){
                    ball.move(15);
                }
                //Log.v(TAG, "Update Position");
                invalidate();
            } //run()
        }; //run
    } //constructor

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPaint(bg);
        /*
        canvas.drawCircle(position.x+radius-(radius/(float)Math.PI), position.y+radius-(radius/(float)Math.PI), 50-(radius-49), shadow);
        canvas.drawCircle(position.x, position.y, radius, color);
*/
        Collections.sort(balls, Ball.BallCompar);
        for(Ball ball : balls){
            Ball shadow = ball.getShadow();
            canvas.drawCircle(shadow.position.x, shadow.position.y, shadow.radius, shadow.paint);
        }

        for(Ball ball : balls){
            canvas.drawCircle(ball.position.x, ball.position.y, ball.radius, ball.paint);
        }
        //Log.v(TAG, "Draw");
        handler.postDelayed(run, FRAME_TIME);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        viewSize = new PointF(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
