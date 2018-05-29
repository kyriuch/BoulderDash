package nodomain.boulderdash.scenemanagament.scenes.gamescene;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;

import nodomain.boulderdash.GlobalVariables;
import nodomain.boulderdash.gameobjects.RectColorSprite;
import nodomain.boulderdash.scenemanagament.Scene;
import nodomain.boulderdash.scenemanagament.scenes.gamescene.Tiles.DiamondTile;
import nodomain.boulderdash.utils.Time;
import nodomain.boulderdash.utils.Math;
import nodomain.boulderdash.utils.Vector2;

public class GameScene extends Scene {

    private Grid grid;

    private double expiredTime;
    private boolean isIncreasing;
    private RectColorSprite rectColorSprite;

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

        rectColorSprite.Draw(canvas);
    }

    @Override
    protected void Init() {
        grid = new Grid(1, 84, 84);

        expiredTime = 0;
        isIncreasing = true;

        rectColorSprite = new RectColorSprite(new Vector2(0, 0),
                GlobalVariables.ScreenWidth, 60, Color.BLACK);

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
