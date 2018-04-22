package nodomain.boulderdash.gameobjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import nodomain.boulderdash.memory.Memory;
import nodomain.boulderdash.utils.Vector2;

public class SpriteText extends GameObject {

    public String text;
    public Vector2 position;

    private Paint paint;

    public SpriteText(String text, Vector2 position) {
        this.text = text;
        this.position = position;

        paint = new Paint();
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(Color.WHITE);
        paint.setTypeface(Memory.BoulderTypeface);
        paint.setTextSize(60f);
    }

    @Override
    public void Draw(Canvas canvas) {
        canvas.drawText(text, position.X, position.Y, paint);
    }

    @Override
    public void Update() {

    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }
}
