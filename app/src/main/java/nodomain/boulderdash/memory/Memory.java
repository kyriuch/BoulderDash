package nodomain.boulderdash.memory;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.media.MediaPlayer;

import nodomain.boulderdash.GlobalVariables;
import nodomain.boulderdash.R;

public class Memory {
    public static Typeface BoulderTypeface;
    public static Bitmap SpriteSheet;
    public static MediaPlayer dirtSound;
    public static MediaPlayer emptySound;
    public static MediaPlayer pickDiamondSound;
    public static MediaPlayer explosionSound;
    public static MediaPlayer crackSound;
    public static MediaPlayer diamondFallSound;
    public static MediaPlayer timeoutOneSound;
    public static MediaPlayer timeoutTwoSound;
    public static MediaPlayer timeoutThreeSound;
    public static MediaPlayer timeoutFourSound;
    public static MediaPlayer timeoutFiveSound;
    public static MediaPlayer finishedSound;
    public static MediaPlayer backgroundMusic;

    public static void cacheSounds() {
        dirtSound = MediaPlayer.create(GlobalVariables.Context, R.raw.walk_earth);
        emptySound = MediaPlayer.create(GlobalVariables.Context, R.raw.walk_empty);
        pickDiamondSound = MediaPlayer.create(GlobalVariables.Context, R.raw.diamond_collect);
        explosionSound = MediaPlayer.create(GlobalVariables.Context, R.raw.explosion);
        crackSound = MediaPlayer.create(GlobalVariables.Context, R.raw.crack);
        diamondFallSound = MediaPlayer.create(GlobalVariables.Context, R.raw.diamond_1);
        timeoutOneSound = MediaPlayer.create(GlobalVariables.Context, R.raw.timeout_9);
        timeoutTwoSound = MediaPlayer.create(GlobalVariables.Context, R.raw.timeout_8);
        timeoutThreeSound = MediaPlayer.create(GlobalVariables.Context, R.raw.timeout_7);
        timeoutFourSound = MediaPlayer.create(GlobalVariables.Context, R.raw.timeout_6);
        timeoutFiveSound = MediaPlayer.create(GlobalVariables.Context, R.raw.timeout_5);
        finishedSound = MediaPlayer.create(GlobalVariables.Context, R.raw.finished);
        backgroundMusic = MediaPlayer.create(GlobalVariables.Context, R.raw.bd1);
        backgroundMusic.setLooping(true);
    }
}
