package nodomain.boulderdash.scenemanagament;

import android.graphics.Canvas;
import android.view.MotionEvent;

public class SceneManager {

    // SINGLETON
    private static final SceneManager ourInstance = new SceneManager();

    public static SceneManager getInstance() {
        return ourInstance;
    }

    private SceneManager() {
    }

    // OTHERS

    public Scene currentScene;

    public void Update() {
        if(currentScene != null) {
            currentScene.Update();
        }
    }

    public void Draw(Canvas canvas) {
        if(currentScene != null) {
            currentScene.Draw(canvas);
        }
    }

    public void ChangeCurrentScene(Scene newScene) {
        currentScene.SwitchToOtherScene(newScene);
    }

    public void ForceChangeScene(Scene newScene) {
        if(currentScene != null) {
            currentScene.StopRunning();
            currentScene.Terminate();
        }

        currentScene = newScene;
        currentScene.Init();
        currentScene.StartRunning();
    }

    public void ReceiveTouchEvent(MotionEvent motionEvent) {
        currentScene.ReceiveTouchEvent(motionEvent);
    }
}
