package nodomain.boulderdash.scenemanagament.scenes.gamescene;

import android.graphics.Canvas;
import android.view.MotionEvent;

import nodomain.boulderdash.scenemanagament.Scene;
import nodomain.boulderdash.scenemanagament.scenes.gamescene.Tiles.DiamondTile;
import nodomain.boulderdash.utils.Time;
import nodomain.boulderdash.utils.Math;

public class GameScene extends Scene {

    private Grid grid;

    private double expiredTime;
    private boolean isIncreasing;

    @Override
    protected void Update() {
        super.Update();

        if (isIncreasing) {
            expiredTime += Time.DeltaTime;

            if(expiredTime >= 0.5) {
                expiredTime = 0.5;
                isIncreasing = false;
            }
        } else {
            expiredTime -= Time.DeltaTime;

            if (expiredTime <= 0) {
                expiredTime = 0;
                isIncreasing = true;
            }
        }

        DiamondTile.currentIndex = Math.Lerp(0, 3, expiredTime * 2);

        grid.Update();
    }

    @Override
    protected void Draw(Canvas canvas) {
        super.Draw(canvas);

        grid.Draw(canvas);
    }

    @Override
    protected void Init() {
        grid = new Grid(1, 84, 84);

        expiredTime = 0;
        isIncreasing = true;

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
        grid.ReceiveTouchEvent(motionEvent);
    }
}
