package nodomain.boulderdash;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;

import nodomain.boulderdash.memory.Memory;

public class MainActivity extends Activity {

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initGlobalVariables();
        cacheAssets();

        gameView = setupGameView();

        setContentView(gameView);
    }

    private void initGlobalVariables() {
        GlobalVariables.Context = this;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        GlobalVariables.ScreenWidth = displayMetrics.widthPixels;
        GlobalVariables.ScreenHeight = displayMetrics.heightPixels;
    }

    private void cacheAssets() {
        Memory.BoulderTypeface = Typeface.createFromAsset(this.getAssets(), "boulderdash.ttf");
        Memory.SpriteSheet = BitmapFactory.decodeResource(this.getResources(), R.drawable.spritesheet);
        Memory.SpriteSheet = Memory.SpriteSheet.copy(Bitmap.Config.ARGB_8888, true);

        int excludeColor = Color.rgb(25, 29, 25);

        for(int x = 0; x < Memory.SpriteSheet.getWidth(); x++) {
            for(int y = 0; y < Memory.SpriteSheet.getHeight(); y++) {
                if(Memory.SpriteSheet.getPixel(x, y) == excludeColor) {
                    Memory.SpriteSheet.setPixel(x, y, Color.BLACK);
                }
            }
        }

        System.out.println(Memory.SpriteSheet.getWidth());
        System.out.println(Memory.SpriteSheet.getHeight());
    }

    private GameView setupGameView() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        return new GameView(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.Pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.Resume();
    }
}
