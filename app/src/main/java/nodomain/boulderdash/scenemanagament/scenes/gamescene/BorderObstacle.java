package nodomain.boulderdash.scenemanagament.scenes.gamescene;

import android.graphics.Rect;

import nodomain.boulderdash.gameobjects.Sprite;
import nodomain.boulderdash.utils.Vector2;

public class BorderObstacle extends Sprite {
    public BorderObstacle(Vector2 position, int width, int height) {
        super(position, width, height, new Rect(0, 128, 41, 167));
    }
}