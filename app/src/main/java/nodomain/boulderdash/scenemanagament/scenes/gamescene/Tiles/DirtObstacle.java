package nodomain.boulderdash.scenemanagament.scenes.gamescene.Tiles;

import nodomain.boulderdash.gameobjects.Sprite;
import nodomain.boulderdash.utils.GridHelper;
import nodomain.boulderdash.utils.Vector2;

public class DirtObstacle extends Sprite {
    public DirtObstacle(Vector2 position, int width, int height) {
        super(position, width, height, GridHelper.getRect(4, 4));
    }
}
