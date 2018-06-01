package nodomain.boulderdash.scenemanagament.scenes.gamescene.Tiles;

import android.graphics.Canvas;
import android.graphics.Rect;

import nodomain.boulderdash.gameobjects.Sprite;
import nodomain.boulderdash.memory.Memory;
import nodomain.boulderdash.utils.GridHelper;
import nodomain.boulderdash.utils.Time;
import nodomain.boulderdash.utils.Vector2;

public class ExitTile extends Sprite {

    Rect[] rects = new Rect[] {
            GridHelper.getRect(2, 4),
            GridHelper.getRect(1, 4)
    };

    private int currentIndex;
    private double expiredTime;

    public ExitTile(Vector2 position, int width, int height) {
        super(position, width, height, GridHelper.getRect(2, 4));

        expiredTime = 0;
    }

    @Override
    public void Update() {
        expiredTime += Time.DeltaTime;

        if(expiredTime > 0.3) {
            expiredTime = 0;
            currentIndex = currentIndex == 0 ? 1 : 0;
        }
    }

    @Override
    public void Draw(Canvas canvas) {
        canvas.drawBitmap(Memory.SpriteSheet, rects[currentIndex], destRect, paint);
    }
}
