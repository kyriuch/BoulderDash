package nodomain.boulderdash.scenes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;

import nodomain.boulderdash.GlobalVariables;

public class MenuScene implements Scene {

    Paint paint;

    @Override
    public void InitScene() {
        paint = new Paint();
        Typeface typeface = Typeface.createFromAsset(GlobalVariables.Context.getAssets(), "boulderdash.ttf");
        paint.setTypeface(typeface);
        paint.setTextSize(30);
        paint.setColor(Color.GREEN);
        paint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    public void Update() {

    }

    @Override
    public void Draw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        canvas.drawText("BOULDER DASH", GlobalVariables.ScreenWidth / 2, GlobalVariables.ScreenHeight / 2, paint);
    }

    @Override
    public void Terminate() {

    }

    @Override
    public void ReceiveTouchEvent(MotionEvent motionEvent) {

    }
}
