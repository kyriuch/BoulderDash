package nodomain.boulderdash.scenemanagament;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;

import nodomain.boulderdash.gameobjects.GameObject;

public abstract class Scene {

    private boolean isRunning;
    protected ArrayList<GameObject> gameObjects;

    protected void Update() {
        if(isRunning) {
            for(GameObject gameObject : gameObjects) {
                gameObject.Update();
            }
        }
    }

    protected void Draw(Canvas canvas) {
        if(isRunning) {
            for (GameObject gameObject : gameObjects) {
                gameObject.Draw(canvas);
            }
        }
    }

    protected void Init() {
        gameObjects = new ArrayList<>();
    }

    protected void StartRunning() {
        isRunning = true;
    }
    protected void StopRunning() {
        isRunning = false;
    }
    protected abstract void Terminate();
    protected abstract void SwitchToOtherScene(Scene scene);
    protected abstract void ReceiveTouchEvent(MotionEvent motionEvent);
}
