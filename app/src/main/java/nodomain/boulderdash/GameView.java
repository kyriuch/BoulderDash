package nodomain.boulderdash;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import nodomain.boulderdash.scenemanagament.SceneManager;
import nodomain.boulderdash.scenemanagament.scenes.MenuScene;
import nodomain.boulderdash.utils.Time;

public class GameView extends SurfaceView implements Runnable {

    private SurfaceHolder surfaceHolder;
    private Thread gameThread;

    private boolean running;

    private SceneManager sceneManager;

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
        sceneManager = SceneManager.getInstance();
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

        Time.LastTime = System.nanoTime();
        sceneManager.ForceChangeScene(new MenuScene());

        while(running) {
            if(surfaceHolder.getSurface().isValid()) {
                canvas = surfaceHolder.lockCanvas();
                canvas.save();

                Time.DeltaTime = (System.nanoTime() - Time.LastTime) / 1000000000.0;
                Time.LastTime = System.nanoTime();

                update();
                draw(canvas);

                canvas.restore();
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    private void update() {
        sceneManager.Update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        sceneManager.Draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        sceneManager.ReceiveTouchEvent(event);

        return true;
    }
}
