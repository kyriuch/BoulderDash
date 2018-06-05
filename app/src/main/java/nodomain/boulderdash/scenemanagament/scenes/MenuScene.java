package nodomain.boulderdash.scenemanagament.scenes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;

import java.io.IOException;

import nodomain.boulderdash.gameobjects.ColorSprite;
import nodomain.boulderdash.gameobjects.Text;
import nodomain.boulderdash.memory.Memory;
import nodomain.boulderdash.scenemanagament.Scene;
import nodomain.boulderdash.scenemanagament.SceneManager;
import nodomain.boulderdash.scenemanagament.scenes.gamescene.GameScene;
import nodomain.boulderdash.utils.Time;
import nodomain.boulderdash.utils.Vector2;

public class MenuScene extends Scene {

    private Text text;
    private Text tapText;
    private double elapsedTime;

    private boolean isSwitching;

    private final double fadingTime = 1.0;
    private final double switchTime = 1.5;
    private ColorSprite colorSprite;
    private Scene nextScene;

    @Override
    protected void Init() {
        super.Init();

        text = new Text("BOULDER DASH", Vector2.Center());
        text.Move(0, -50f);
        text.SetTextSize(120f);

        tapText = new Text("TAP TO PLAY", Vector2.Center());
        tapText.Move(0, 40);
        tapText.SetTextSize(70f);

        gameObjects.add(text);
        gameObjects.add(tapText);

        isSwitching = false;
        colorSprite = new ColorSprite(Color.argb(0, 0, 0, 0));
        Memory.backgroundMusic.start();
    }

    @Override
    protected void Terminate() {
    }

    @Override
    protected void SwitchToOtherScene(Scene scene) {
        elapsedTime = 0;
        isSwitching = true;
        gameObjects.add(colorSprite);
        nextScene = scene;
    }

    @Override
    protected void ReceiveTouchEvent(MotionEvent motionEvent) {
        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            SwitchToOtherScene(new GameScene());
        }
    }

    @Override
    protected void Update() {
        super.Update();

        if(isSwitching) {
            elapsedTime += Time.DeltaTime;

            if(elapsedTime <= fadingTime) {
                colorSprite.SetColor(Color.argb((int)(elapsedTime / fadingTime * 255), 0, 0, 0));
            }

            if(elapsedTime >= switchTime) {
                isSwitching = false;

                SceneManager.getInstance().ForceChangeScene(nextScene);
            }
        }
    }

    @Override
    protected void Draw(Canvas canvas) {
        super.Draw(canvas);


    }
}
