package nodomain.boulderdash.scenemanagament.scenes.gamescene;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.view.MotionEvent;

import java.text.DecimalFormat;
import java.util.Locale;

import nodomain.boulderdash.GlobalVariables;
import nodomain.boulderdash.gameobjects.RectColorSprite;
import nodomain.boulderdash.gameobjects.Sprite;
import nodomain.boulderdash.gameobjects.Text;
import nodomain.boulderdash.scenemanagament.Scene;
import nodomain.boulderdash.scenemanagament.scenes.gamescene.Tiles.DiamondTile;
import nodomain.boulderdash.utils.GridHelper;
import nodomain.boulderdash.utils.Time;
import nodomain.boulderdash.utils.Math;
import nodomain.boulderdash.utils.Vector2;

public class GameScene extends Scene {

    // ANIMATIONS VARIABLES
    private double expiredTime;
    private boolean isIncreasing;


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

        if(gameTime > Time.DeltaTime)
            gameTime -= Time.DeltaTime;

        gameTimeText.SetText(String.valueOf(((int) gameTime)));

        DiamondTile.currentIndex = Math.Lerp(0, 3, expiredTime * 2);

        grid.Update();
    }

    @Override
    protected void Draw(Canvas canvas) {
        super.Draw(canvas);

        grid.Draw(canvas);

        rectColorSprite.Draw(canvas);
        gameTimeText.Draw(canvas);
        scoreText.Draw(canvas);
        diamondsText.Draw(canvas);

        if(diamondsCollected) {
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
        grid = new Grid(1, 64, 64, this);

        expiredTime = 0;
        isIncreasing = true;

        rectColorSprite = new RectColorSprite(new Vector2(0, 0),
                GlobalVariables.ScreenWidth, 50, Color.BLACK);


        // UPPER TEXTS
        gameTime = 150;
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

        for(int i = 0; i < diamondsAfterCollect.length; i++) {
            diamondsAfterCollect[i] = new Sprite(new Vector2(10 + i * 50, 6), 35, 35, GridHelper.getRect(1, 5));
            diamondsAfterCollect[i].SetColorFilter(new ColorMatrixColorFilter(colorMatrix));
        }

        diamondsAfterCollectionText = new Text(String.format(Locale.getDefault(), "%02d", grid.diamondsAfterCollection), new Vector2(200, 35));
        diamondsAfterCollectionText.SetTextSize(46);


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

    private void PlayerScored(int score) {
        this.score += score;
        scoreText.SetText(String.format(Locale.getDefault(), "%06d", this.score));
    }

    public void DiamondPickedUp() {
        this.diamonds += 1;
        this.diamondsText.SetText(String.format(Locale.getDefault(), "%02d", diamonds));

        if(diamonds == grid.diamondsToCollect) {
            diamondsCollected = true;
            grid.OpenExit();
        }

        if(diamondsCollected) {
            PlayerScored(grid.diamondsAfterCollection);
        } else {
            PlayerScored(grid.diamondsBeforeCollection);
        }

    }
}
