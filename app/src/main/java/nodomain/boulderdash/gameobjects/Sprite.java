package nodomain.boulderdash.gameobjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;

import nodomain.boulderdash.memory.Memory;
import nodomain.boulderdash.utils.Vector2;

public class Sprite extends GameObject {

    protected int width;
    protected int height;
    protected Rect srcRect;

    protected Rect destRect;
    protected Paint paint;

    public Sprite(Vector2 position, int width, int height, Rect srcRect) {
        this.position = position;
        this.width = width;
        this.height = height;
        this.srcRect = srcRect;
        this.paint = new Paint();

        this.destRect = new Rect();
        destRect.left = (int)(position.X);
        destRect.right = (int)(position.X + width);
        destRect.top = (int)(position.Y);
        destRect.bottom = (int)(position.Y + height);
    }

    @Override
    public void Update() {

    }

    @Override
    public void Draw(Canvas canvas) {
        canvas.drawBitmap(Memory.SpriteSheet, srcRect, destRect, paint);
    }

    @Override
    public void Move(float x) {
        super.Move(x);

        destRect.left = (int) position.X;
        destRect.right = (int)(position.X + width);
    }

    @Override
    public void Move(float x, float y) {
        super.Move(x, y);

        destRect.left = (int) position.X;
        destRect.right = (int)(position.X + width);
        destRect.top = (int)(position.Y);
        destRect.bottom = (int)(position.Y + height);
    }

    public void SetColorFilter(ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }

    public void UpdateDestRect(Vector2 newPosition) {
        this.destRect = new Rect();
        destRect.left = (int) (newPosition.X);
        destRect.right = (int) (newPosition.X + width);
        destRect.top = (int) (newPosition.Y);
        destRect.bottom = (int) (newPosition.Y + height);
    }
}
