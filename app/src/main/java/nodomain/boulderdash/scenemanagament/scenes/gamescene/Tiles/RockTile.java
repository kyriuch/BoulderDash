package nodomain.boulderdash.scenemanagament.scenes.gamescene.Tiles;

import android.graphics.Rect;

import nodomain.boulderdash.gameobjects.Sprite;
import nodomain.boulderdash.scenemanagament.scenes.gamescene.Grid;
import nodomain.boulderdash.utils.GridHelper;
import nodomain.boulderdash.utils.Vector2;

public class RockTile extends Sprite {
    public RockTile(Vector2 position, int width, int height) {
        super(position, width, height, GridHelper.getRect(6, 4));
    }
}
