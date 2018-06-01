package nodomain.boulderdash.scenemanagament.scenes.gamescene.Tiles;

import android.graphics.Canvas;

import nodomain.boulderdash.gameobjects.GameObject;

public class Tile {
    private GameObject gameObject;
    private boolean walkableByPlayer;
    private boolean isFalling;

    public Tile(GameObject gameObject) {
        this.gameObject = gameObject;
        walkableByPlayer = false;
        isFalling = false;
    }

    public Tile(GameObject gameObject, boolean walkableByPlayer) {
        this.gameObject = gameObject;
        this.walkableByPlayer = walkableByPlayer;
    }

    public void Update() {
        gameObject.Update();
    }

    public void Draw(Canvas canvas) {
        gameObject.Draw(canvas);
    }

    public boolean isWalkableByPlayer() {
        return walkableByPlayer;
    }

    public void setWalkableByPlayer(boolean walkableByPlayer) {
        this.walkableByPlayer = walkableByPlayer;
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public boolean isFalling() {
        return isFalling;
    }

    public void setFalling(boolean falling) {
        isFalling = falling;
    }
}
