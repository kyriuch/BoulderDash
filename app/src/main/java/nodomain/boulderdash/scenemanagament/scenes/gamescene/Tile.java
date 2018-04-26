package nodomain.boulderdash.scenemanagament.scenes.gamescene;

import android.graphics.Canvas;

import nodomain.boulderdash.gameobjects.GameObject;

public class Tile {
    private GameObject gameObject;

    public Tile(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public void Update() {
        gameObject.Update();
    }

    public void Draw(Canvas canvas) {
        gameObject.Draw(canvas);
    }
}
