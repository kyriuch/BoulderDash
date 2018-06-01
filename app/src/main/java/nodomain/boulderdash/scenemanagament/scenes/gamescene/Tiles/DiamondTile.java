package nodomain.boulderdash.scenemanagament.scenes.gamescene.Tiles;

import android.graphics.Canvas;
import android.graphics.Rect;

import nodomain.boulderdash.gameobjects.Sprite;
import nodomain.boulderdash.memory.Memory;
import nodomain.boulderdash.utils.GridHelper;
import nodomain.boulderdash.utils.Vector2;

public class  DiamondTile extends Sprite {

    private final static Rect[] animationRects = {
            GridHelper.getRect(1, 5),
            GridHelper.getRect(2, 5),
            GridHelper.getRect(2, 6),
            GridHelper.getRect(1, 6),GridHelper.getRect(1, 5),
            GridHelper.getRect(1, 7),
            GridHelper.getRect(2, 7),
            GridHelper.getRect(2, 8),
            GridHelper.getRect(1, 8),
    };

    public static int currentIndex;

    public DiamondTile(Vector2 position, int width, int height) {
        super(position, width, height, new Rect());
    }

    @Override
    public void Draw(Canvas canvas) {
        canvas.drawBitmap(Memory.SpriteSheet, animationRects[currentIndex], destRect, paint);
    }
}
