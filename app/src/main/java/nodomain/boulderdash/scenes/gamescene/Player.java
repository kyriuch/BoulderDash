package nodomain.boulderdash.scenes.gamescene;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.ArrayList;

import nodomain.boulderdash.GlobalVariables;
import nodomain.boulderdash.gameobjects.GameObject;
import nodomain.boulderdash.gameobjects.Sprite;

public class Player extends GameObject {

    private MotionEvent currentMotionEvent;
    private float widthRatio;
    private float heightRatio;

    private ArrayList<Sprite> playerSprites;
    private int currentSprite = 0;

    public Player() {
        playerSprites = new ArrayList<>();

        Sprite generalSprite = new Sprite(new Rect(0, 0, 42, 42),
                new Rect(0, 0, 80, 80));

        playerSprites.add(generalSprite);
    }

    @Override
    public void Draw(Canvas canvas) {
        playerSprites.get(currentSprite).Draw(canvas);
    }

    @Override
    public void Update() {
        if(currentMotionEvent != null) {

            // processing motionEvent

            if(currentMotionEvent.getAction() == MotionEvent.ACTION_DOWN
                    || currentMotionEvent.getAction() == MotionEvent.ACTION_POINTER_DOWN) {
                widthRatio = currentMotionEvent.getX() / GlobalVariables.ScreenWidth;
                heightRatio = currentMotionEvent.getY() / GlobalVariables.ScreenHeight;
                Rect rect = playerSprites.get(currentSprite).getDestRect();
                if(Math.abs(0.5f - widthRatio) > Math.abs(0.5f - heightRatio)) {
                    if(widthRatio > 0.5f) {
                        rect.left += 40;
                        rect.right += 40;
                    } else {
                        // to the left
                        rect.left -= 40;
                        rect.right -= 40;
                    }
                } else {
                    if(heightRatio > 0.5f) {
                        // to the bottom
                        rect.top += 40;
                        rect.bottom += 40;
                    } else {
                        // to the top
                        rect.top -= 40;
                        rect.bottom -= 40;
                    }
                }
            }

            currentMotionEvent = null;
        }
    }

    public void UpdateMotionEvent(MotionEvent motionEvent) {
        currentMotionEvent = motionEvent;
    }
}
