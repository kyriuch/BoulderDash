package nodomain.boulderdash.scenes;

import android.graphics.Canvas;
import android.view.MotionEvent;

public class SceneManager {
    private static final SceneManager ourInstance = new SceneManager();

    public static SceneManager GetInstance() {
        return ourInstance;
    }

    private Scene currentScene;

    private SceneManager() {
    }

    public void UpdateScene() {
        currentScene.Update();
    }

    public void DrawScene(Canvas canvas) {
        currentScene.Draw(canvas);
    }

    public void ReceiveTouchEvent(MotionEvent motionEvent) {
        currentScene.ReceiveTouchEvent(motionEvent);
    }

    public void SetCurrentScene(Scene scene) {
        if(currentScene != null) {
            currentScene.Terminate();
        }

        this.currentScene = scene;

        currentScene.InitScene();
    }
}
