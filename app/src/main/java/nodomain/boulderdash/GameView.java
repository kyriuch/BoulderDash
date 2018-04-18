package nodomain.boulderdash;

import android.content.Context;
import android.graphics.Canvas;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import nodomain.boulderdash.scenes.MenuScene;
import nodomain.boulderdash.scenes.SceneManager;

public class GameView extends SurfaceView implements Runnable {

    private SurfaceHolder surfaceHolder;
    private Thread gameThread;
    private SceneManager sceneManager;

    private boolean running;

    public GameView(Context context) {
        super(context);

        init(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    private void init(Context context) {
        GlobalVariables.Context = context;
        sceneManager = SceneManager.GetInstance();
        sceneManager.SetCurrentScene(new MenuScene());

        surfaceHolder = getHolder();
    }

    public void Pause() {
        running = false;

        try {
            gameThread.join();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void Resume() {
        running = true;

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        GlobalVariables.ScreenHeight = h;
        GlobalVariables.ScreenWidth = w;
    }



    @Override
    public void run() {
        Canvas canvas;

        while(running) {
            if(surfaceHolder.getSurface().isValid()) {
                canvas = surfaceHolder.lockCanvas();
                canvas.save();

                update();
                draw(canvas);

                canvas.restore();
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    private void update() {
        sceneManager.UpdateScene();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        sceneManager.DrawScene(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        sceneManager.ReceiveTouchEvent(event);

        return super.onTouchEvent(event);
    }
}
