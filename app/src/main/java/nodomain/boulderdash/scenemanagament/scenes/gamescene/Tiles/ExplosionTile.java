package nodomain.boulderdash.scenemanagament.scenes.gamescene.Tiles;

import android.graphics.Canvas;
import android.graphics.Rect;

import nodomain.boulderdash.gameobjects.Sprite;
import nodomain.boulderdash.memory.Memory;
import nodomain.boulderdash.utils.GridHelper;
import nodomain.boulderdash.utils.Time;
import nodomain.boulderdash.utils.Vector2;
import nodomain.boulderdash.utils.Math;

public class ExplosionTile extends Sprite {

    private static Rect[] rects = {
            GridHelper.getRect(8, 5),
            GridHelper.getRect(9, 5)
    };

    private double rectTimer;

    public ExplosionTile(Vector2 position, int width, int height) {
        super(position, width, height, GridHelper.getRect(8, 5));

        rectTimer = 0;
    }

    @Override
    public void Update() {
        if(rectTimer <= 0.5)
            rectTimer += Time.DeltaTime;



        super.Update();
    }

    @Override
    public void Draw(Canvas canvas) {
        canvas.drawBitmap(Memory.SpriteSheet, rects[Math.Lerp(0, 1, rectTimer * 2)], destRect, paint);
    }
}
