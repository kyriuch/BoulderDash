package nodomain.boulderdash.scenemanagament.scenes.gamescene;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.view.MotionEvent;

import java.util.Locale;

import nodomain.boulderdash.GlobalVariables;
import nodomain.boulderdash.gameobjects.RectColorSprite;
import nodomain.boulderdash.gameobjects.Sprite;
import nodomain.boulderdash.gameobjects.Text;
import nodomain.boulderdash.memory.Memory;
import nodomain.boulderdash.scenemanagament.Scene;
import nodomain.boulderdash.scenemanagament.SceneManager;
import nodomain.boulderdash.scenemanagament.scenes.MenuScene;
import nodomain.boulderdash.scenemanagament.scenes.gamescene.Tiles.DiamondTile;
import nodomain.boulderdash.utils.GridHelper;
import nodomain.boulderdash.utils.Time;
import nodomain.boulderdash.utils.Math;
import nodomain.boulderdash.utils.Vector2;

public class GameScene extends Scene {

    // ANIMATIONS VARIABLES
    private double expiredTime;


    // UPPER TEXTS
    private RectColorSprite rectColorSprite;

    private Text gameTimeText;
    private double gameTime;

    private Text scoreText;
    private int score;

    private Text diamondsText;
    private int diamonds;

    private Text diamondsToCollectText;
    private Text diamondsBeforeCollectionText;
    private Text diamondsAfterCollectionText;
    private Sprite[] diamondsAfterCollect;
    private Sprite diamondBeforeCollect;

    private boolean diamondsCollected;

    // OTHERS
    private Grid grid;

    private double diedTimer;
    private boolean playerDied;

    private boolean playerFinished;
    private double finishTimer;

    private int lives;
    private int timeoutState;
    private int currentLevel;

    @Override
    protected void Update() {
        super.Update();

        expiredTime += Time.DeltaTime;

        if (expiredTime >= 1) {
            expiredTime = 0;
        }

        if (gameTime > Time.DeltaTime && !playerDied && !playerFinished)
            gameTime -= Time.DeltaTime;

        if (timeoutState != (int) gameTime) {
            timeoutState = (int) gameTime;

            switch (timeoutState) {
                case 5:
                    Memory.timeoutFiveSound.start();
                    break;
                case 4:
                    Memory.timeoutFourSound.start();
                    break;
                case 3:
                    Memory.timeoutThreeSound.start();
                    break;
                case 2:
                    Memory.timeoutTwoSound.start();
                    break;
                case 1:
                    Memory.timeoutOneSound.start();
                    break;
                case 0:
                    PlayerDied();
                    grid.PlayerDied();
                    break;
            }

        }

        gameTimeText.SetText(String.valueOf(((int) gameTime)));

        DiamondTile.currentIndex = Math.Lerp(0, 7, expiredTime);

        grid.Update();

        if (playerDied) {
            diedTimer += Time.DeltaTime;

            if (diedTimer >= 3) {
                if (lives > 0)
                    restart(currentLevel);
                else
                    SceneManager.getInstance().ForceChangeScene(new MenuScene());
            }
        }

        if (playerFinished) {
            finishTimer += Time.DeltaTime;

            if (finishTimer <= 2) {
                scoreText.SetText(String.format(Locale.getDefault(), "%06d", Math.Lerp(0, (int) gameTime, finishTimer / 2) + score));
                gameTimeText.SetText(String.format(Locale.getDefault(), "%d", (int) gameTime - Math.Lerp(0, (int) gameTime, finishTimer / 2)));
            } else {
                gameTimeText.SetText(String.format(Locale.getDefault(), "%d", 0));
            }

            if (finishTimer >= 5) {
                currentLevel++;

                if (currentLevel < 4) {
                    restart(currentLevel);
                } else {
                    SceneManager.getInstance().ForceChangeScene(new MenuScene());
                }
            }
        }
    }

    @Override
    protected void Draw(Canvas canvas) {
        super.Draw(canvas);

        grid.Draw(canvas);

        rectColorSprite.Draw(canvas);
        gameTimeText.Draw(canvas);
        scoreText.Draw(canvas);
        diamondsText.Draw(canvas);

        if (diamondsCollected) {
            for (Sprite DiamondsAfterCollect : diamondsAfterCollect) {
                DiamondsAfterCollect.Draw(canvas);
            }

            diamondsAfterCollectionText.Draw(canvas);
        } else {
            diamondsBeforeCollectionText.Draw(canvas);
            diamondsToCollectText.Draw(canvas);
            diamondBeforeCollect.Draw(canvas);
        }
    }

    @Override
    protected void Init() {
        diamondsCollected = false;
        playerDied = false;

        currentLevel = 1;
        grid = new Grid(currentLevel, 64, 64, this);

        expiredTime = 0;

        rectColorSprite = new RectColorSprite(new Vector2(0, 0),
                GlobalVariables.ScreenWidth, 50, Color.BLACK);


        // UPPER TEXTS
        gameTime = 150;
        timeoutState = (int) gameTime;
        gameTimeText = new Text(String.valueOf(((int) gameTime)), new Vector2(GlobalVariables.ScreenWidth / 2 + 150, 35));
        gameTimeText.SetTextSize(46);

        score = 0;
        scoreText = new Text(String.format(Locale.getDefault(), "%06d", score), new Vector2(GlobalVariables.ScreenWidth - 110, 35));
        scoreText.SetTextSize(46);

        diamonds = 0;
        diamondsText = new Text(String.format(Locale.getDefault(), "%02d", diamonds), new Vector2(GlobalVariables.ScreenWidth / 2 - 150, 35));
        diamondsText.SetTextSize(46);
        diamondsText.SetColor(Color.YELLOW);

        diamondsToCollectText = new Text(String.format(Locale.getDefault(), "%02d", grid.diamondsToCollect), new Vector2(40, 35));
        diamondsToCollectText.SetTextSize(46);
        diamondsToCollectText.SetColor(Color.YELLOW);

        diamondBeforeCollect = new Sprite(new Vector2(85, 6), 35, 35, GridHelper.getRect(1, 5));
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        diamondBeforeCollect.SetColorFilter(new ColorMatrixColorFilter(colorMatrix));

        diamondsBeforeCollectionText = new Text(String.format(Locale.getDefault(), "%02d", grid.diamondsBeforeCollection), new Vector2(160, 35));
        diamondsBeforeCollectionText.SetTextSize(46);

        diamondsAfterCollect = new Sprite[3];

        for (int i = 0; i < diamondsAfterCollect.length; i++) {
            diamondsAfterCollect[i] = new Sprite(new Vector2(10 + i * 50, 6), 35, 35, GridHelper.getRect(1, 5));
            diamondsAfterCollect[i].SetColorFilter(new ColorMatrixColorFilter(colorMatrix));
        }

        diamondsAfterCollectionText = new Text(String.format(Locale.getDefault(), "%02d", grid.diamondsAfterCollection), new Vector2(200, 35));
        diamondsAfterCollectionText.SetTextSize(46);

        lives = 3;

        super.Init();
    }

    private void restart(int level) {
        playerFinished = false;
        diamondsCollected = false;
        playerDied = false;
        expiredTime = 0;

        grid.Restart(level);

        gameTime = 150;
        gameTimeText.SetText(String.valueOf(((int) gameTime)));

        diamonds = 0;
        diamondsText.SetText(String.format(Locale.getDefault(), "%02d", diamonds));

        diamondsToCollectText.SetText(String.format(Locale.getDefault(), "%02d", grid.diamondsToCollect));
        diamondsBeforeCollectionText.SetText(String.format(Locale.getDefault(), "%02d", grid.diamondsBeforeCollection));
        diamondsAfterCollectionText.SetText(String.format(Locale.getDefault(), "%02d", grid.diamondsAfterCollection));

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

    private void PlayerScored(int score) {
        this.score += score;
        scoreText.SetText(String.format(Locale.getDefault(), "%06d", this.score));
    }

    public void DiamondPickedUp() {
        this.diamonds += 1;
        this.diamondsText.SetText(String.format(Locale.getDefault(), "%02d", diamonds));

        if (diamonds == grid.diamondsToCollect) {
            diamondsCollected = true;
            grid.OpenExit();
        }

        if (diamondsCollected) {
            PlayerScored(grid.diamondsAfterCollection);
        } else {
            PlayerScored(grid.diamondsBeforeCollection);
        }

    }

    public void PlayerDied() {
        Memory.explosionSound.start();
        playerDied = true;
        lives--;
        diedTimer = 0;
    }

    public void PlayerFinished() {
        Memory.finishedSound.start();
        playerFinished = true;
        finishTimer = 0;
    }

    public boolean DidPlayerFinish() {
        return playerFinished;
    }
}
