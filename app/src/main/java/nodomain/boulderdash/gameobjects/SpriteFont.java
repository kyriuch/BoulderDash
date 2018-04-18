package nodomain.boulderdash.gameobjects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

import nodomain.boulderdash.utils.Vector2;

public class SpriteFont extends GameObject {
    private Paint paint;
    private Typeface typeface;
    private Vector2 position;
    private String text;

    public SpriteFont(String text, Typeface typeface, Vector2 position, int color, float fontSize) {
        this.text = text;
        this.paint = new Paint();
        this.typeface = typeface;
        this.position = position;

        paint.setTypeface(typeface);
        paint.setColor(color);
        paint.setTextSize(fontSize);
        paint.setTextAlign(Paint.Align.CENTER);
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public Typeface getTypeface() {
        return typeface;
    }

    public void setTypeface(Typeface typeface) {
        this.typeface = typeface;
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

    @Override
    public void Draw(Canvas canvas) {
        canvas.drawText(text, position.X, position.Y, paint);
    }

    @Override
    public void Update() {

    }
}
