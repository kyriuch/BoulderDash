package nodomain.boulderdash.scenes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;

import java.util.ArrayList;

import nodomain.boulderdash.gameobjects.SpriteText;
import nodomain.boulderdash.gameobjects.Clickable;
import nodomain.boulderdash.gameobjects.IGameObject;
import nodomain.boulderdash.gameobjects.TextButton;
import nodomain.boulderdash.scenes.gamescene.GameScene;
import nodomain.boulderdash.utils.Vector2;

public class MenuScene implements Scene {

    private TextButton playGameButton;
    private SpriteText titleText;

    private ArrayList<IGameObject> gameObjects;

    @Override
    public void InitScene() {
        gameObjects = new ArrayList<>();

        // playGameButton

        playGameButton = new TextButton("PLAY GAME", Vector2.Center());

        playGameButton.setClickListener(new Clickable() {
            @Override
            public void OnClick() {
                SceneManager.GetInstance().SetCurrentScene(new GameScene());
            }
        });

        playGameButton.getTextPaint().setColor(Color.BLACK);

        gameObjects.add(playGameButton);

        // titleText

        titleText = new SpriteText("BOULDER DASH", Vector2.Center());
        titleText.getPosition().Y -= 200;
        titleText.getPaint().setTextSize(120f);

        gameObjects.add(titleText);

    }

    @Override
    public void Update() {
        for(IGameObject gameObject : gameObjects) {
            gameObject.Update();
        }
    }

    @Override
    public void Draw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);

        for(IGameObject gameObject : gameObjects) {
            gameObject.Draw(canvas);
        }
    }

    @Override
    public void Terminate() {

    }

    @Override
    public void ReceiveTouchEvent(MotionEvent motionEvent) {
        switch(motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                if(playGameButton.Intersects((int) motionEvent.getX(), (int) motionEvent.getY())) {
                    playGameButton.OnClick();
                }
            }
        }
    }
}
