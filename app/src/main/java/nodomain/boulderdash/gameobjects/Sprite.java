package nodomain.boulderdash.gameobjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import nodomain.boulderdash.memory.Memory;

public class Sprite extends GameObject {

    private Rect srcRect;
    private Rect destRect;
    private Paint paint;

    public Sprite(Rect srcRect, Rect destRect) {
        this.srcRect = srcRect;
        this.destRect = destRect;

        paint = new Paint();
        paint.setColor(Color.BLACK);

    }

    @Override
    public void Draw(Canvas canvas) {
        canvas.drawBitmap(Memory.SpriteSheet, srcRect, destRect, paint);
    }

    @Override
    public void Update() {

    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public Rect getDestRect() {
        return destRect;
    }

    public void setDestRect(Rect destRect) {
        this.destRect = destRect;
    }
}
