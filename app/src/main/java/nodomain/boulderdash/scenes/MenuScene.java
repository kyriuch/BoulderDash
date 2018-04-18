package nodomain.boulderdash.scenes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;

import java.util.Random;

import nodomain.boulderdash.GlobalVariables;
import nodomain.boulderdash.gameobjects.Clickable;
import nodomain.boulderdash.gameobjects.SpriteFont;
import nodomain.boulderdash.utils.Vector2;

public class MenuScene implements Scene {

    private SpriteFont titleFont;

    @Override
    public void InitScene() {
        titleFont = new SpriteFont("BOULDER DASH",
                Typeface.createFromAsset(GlobalVariables.Context.getAssets(), "boulderdash.ttf"),
                Vector2.Center(),
                Color.GREEN,
                30f);

        titleFont.setClickListener(new Clickable() {
            @Override
            public void OnClick() {
                Random random = new Random();
                titleFont.getPaint().setColor(random.nextInt(Color.argb(100, random.nextInt(255), random.nextInt(255), random.nextInt(255))));
            }
        });
    }

    @Override
    public void Update() {

    }

    @Override
    public void Draw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);

        titleFont.Draw(canvas);
    }

    @Override
    public void Terminate() {

    }

    @Override
    public void ReceiveTouchEvent(MotionEvent motionEvent) {
        switch(motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                if(Math.abs(titleFont.getPosition().X - motionEvent.getX()) <= 100
                        && Math.abs(titleFont.getPosition().Y - motionEvent.getY()) <= 100) {
                    titleFont.OnClick();
                }
            }
        }
    }
}
