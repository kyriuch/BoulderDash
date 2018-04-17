package nodomain.boulderdash.scenes;

import android.graphics.Canvas;
import android.view.MotionEvent;

public interface Scene {
    void InitScene();
    void Update();
    void Draw(Canvas canvas);
    void Terminate();
    void ReceiveTouchEvent(MotionEvent motionEvent);
}
