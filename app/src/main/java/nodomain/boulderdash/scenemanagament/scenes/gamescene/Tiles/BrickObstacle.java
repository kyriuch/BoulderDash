package nodomain.boulderdash.scenemanagament.scenes.gamescene.Tiles;

import android.graphics.Rect;

import nodomain.boulderdash.gameobjects.Sprite;
import nodomain.boulderdash.utils.GridHelper;
import nodomain.boulderdash.utils.Vector2;

public class BrickObstacle extends Sprite {
    public BrickObstacle(Vector2 position, int width, int height) {
        super(position, width, height, GridHelper.getRect(3, 4));
    }
}
