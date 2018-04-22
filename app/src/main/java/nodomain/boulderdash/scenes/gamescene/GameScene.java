package nodomain.boulderdash.scenes.gamescene;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;

import nodomain.boulderdash.gameobjects.GameObject;
import nodomain.boulderdash.gameobjects.IGameObject;
import nodomain.boulderdash.scenes.Scene;

public class GameScene implements Scene {

    Player player;

    ArrayList<GameObject> gameObjects;

    @Override
    public void InitScene() {
        gameObjects = new ArrayList<>();

        player = new Player();

        gameObjects.add(player);
    }

    @Override
    public void Update() {
        for(IGameObject gameObject : gameObjects) {
            gameObject.Update();
        }
    }

    @Override
    public void Draw(Canvas canvas) {
        for(IGameObject gameObject : gameObjects) {
            gameObject.Draw(canvas);
        }
    }

    @Override
    public void Terminate() {

    }

    @Override
    public void ReceiveTouchEvent(MotionEvent motionEvent) {
        player.UpdateMotionEvent(motionEvent);
    }
}
