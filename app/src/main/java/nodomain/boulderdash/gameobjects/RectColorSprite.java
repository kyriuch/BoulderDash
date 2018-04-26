package nodomain.boulderdash.gameobjects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import nodomain.boulderdash.utils.Vector2;

public class RectColorSprite extends GameObject {

    private int width;
    private int  height;
    private Paint paint;
    private Rect rect;

    public RectColorSprite(Vector2 position, int width, int height, int color) {
        this.position = position;
        this.width = width;
        this.height = height;

        this.paint = new Paint();
        paint.setColor(color);

        this.rect = new Rect();
        this.rect.left = (int)(position.X);
        this.rect.right = (int)(position.X + this.width);
        this.rect.top = (int)(position.Y);
        this.rect.bottom = (int)(position.Y + this.height);
    }

    @Override
    public void Update() {

    }

    @Override
    public void Draw(Canvas canvas) {
        canvas.drawRect(rect, paint);
    }
}
