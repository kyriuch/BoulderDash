package nodomain.boulderdash.scenemanagament.scenes.gamescene;

import android.graphics.Rect;

import nodomain.boulderdash.gameobjects.Sprite;
import nodomain.boulderdash.utils.Vector2;

public class DirtObstacle extends Sprite {
    public DirtObstacle(Vector2 position, int width, int height) {
        super(position, width, height, new Rect(128, 128, 167, 167));
    }
}
