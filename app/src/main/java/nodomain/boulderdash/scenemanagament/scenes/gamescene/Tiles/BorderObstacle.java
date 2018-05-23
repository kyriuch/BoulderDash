package nodomain.boulderdash.scenemanagament.scenes.gamescene.Tiles;

import nodomain.boulderdash.gameobjects.Sprite;
import nodomain.boulderdash.utils.GridHelper;
import nodomain.boulderdash.utils.Vector2;

public class BorderObstacle extends Sprite {
    public BorderObstacle(Vector2 position, int width, int height) {
        super(position, width, height, GridHelper.getRect(1, 4));
    }
}