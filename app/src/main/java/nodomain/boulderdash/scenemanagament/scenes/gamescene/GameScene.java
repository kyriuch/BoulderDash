package nodomain.boulderdash.scenemanagament.scenes.gamescene;

import android.graphics.Canvas;
import android.view.MotionEvent;

import nodomain.boulderdash.scenemanagament.Scene;

public class GameScene extends Scene {

    private Grid grid;


    @Override
    protected void Update() {
        super.Update();

        grid.Update();
    }

    @Override
    protected void Draw(Canvas canvas) {
        super.Draw(canvas);

        grid.Draw(canvas);
    }

    @Override
    protected void Init() {
        grid = new Grid(1, 100, 100);

        super.Init();
    }

    @Override
    protected void Terminate() {

    }

    @Override
    protected void SwitchToOtherScene(Scene scene) {

    }

    @Override
    protected void ReceiveTouchEvent(MotionEvent motionEvent) {

    }
}
