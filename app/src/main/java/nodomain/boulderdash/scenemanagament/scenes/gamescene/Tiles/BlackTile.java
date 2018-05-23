package nodomain.boulderdash.scenemanagament.scenes.gamescene.Tiles;

import android.graphics.Color;

import nodomain.boulderdash.gameobjects.RectColorSprite;
import nodomain.boulderdash.utils.Vector2;

public class BlackTile extends RectColorSprite {
    public BlackTile(Vector2 position, int width, int height) {
        super(position, width, height, Color.BLACK);
    }
}
