package io.github.gatimus.ballanimation;

import android.content.Context;
import android.content.SharedPreferences;
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
    private PointF viewSize;
    private Paint bg;
    private ArrayList<Ball> balls;

    public Bounce(Context context, final SharedPreferences sharedPreferences) {
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
                balls = new ArrayList<>();
                int numBalls = (int) (sharedPreferences.getFloat("num_slider", 0.50F)*20);
                for(int i = 0; i<=numBalls; i++){
                    balls.add(new Ball(Ball.BALL,viewSize,75));
                }
                Log.v(TAG, "surfaceChanged");
            }

        });
        Display display = ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Log.v(TAG, display.toString());
        FRAME_TIME = Math.round(1000/display.getRefreshRate());
        Log.v(TAG, "FRAME_TIME:" + String.valueOf(FRAME_TIME));
        handler = new Handler();
        bg = new Paint();
        bg.setStyle(Paint.Style.FILL);
        bg.setColor(Color.WHITE);

        balls = new ArrayList<>();

        run = new Runnable(){
            @Override
            public void run() {
                float speedBalls = sharedPreferences.getFloat("speed_slider", 0.50F)+0.01F;
                for(Ball ball : balls){
                    ball.move(speedBalls);
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


}
