package nodomain.boulderdash.gameobjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import nodomain.boulderdash.memory.Memory;
import nodomain.boulderdash.utils.Vector2;

public class TextButton extends GameObject {

    // text
    private Paint textPaint;
    private Vector2 position;
    private String text;

    // buttonRect
    private Rect buttonRect;
    private Paint rectPaint;

    public TextButton(String text, Vector2 position) {
        this.text = text;
        this.position = position;

        textPaint = new Paint();
        textPaint.setTextSize(60f);
        textPaint.setTypeface(Memory.BoulderTypeface);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setColor(Color.WHITE);

        int height, halfWidth;

        buttonRect = new Rect();

        textPaint.getTextBounds(this.text, 0, this.text.length(), buttonRect);

        height = buttonRect.height();
        halfWidth = buttonRect.width() / 2;

        buttonRect = new Rect((int)this.position.X - halfWidth - 50, (int)this.position.Y - height - 50,
                (int)this.position.X + halfWidth + 50, (int)this.position.Y + 50);

        rectPaint = new Paint();
        rectPaint.setColor(Color.GRAY);
    }

    @Override
    public void Draw(Canvas canvas) {
        canvas.drawRect(buttonRect, rectPaint);
        canvas.drawText(text, position.X, position.Y, textPaint);
    }

    @Override
    public void Update() {

    }

    public boolean Intersects(int x, int y) {
        return buttonRect.contains(x, y);
    }

    public Paint getTextPaint() {
        return textPaint;
    }

    public void setTextPaint(Paint textPaint) {
        this.textPaint = textPaint;
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

    public Rect getButtonRect() {
        return buttonRect;
    }

    public void setButtonRect(Rect buttonRect) {
        this.buttonRect = buttonRect;
    }

    public Paint getRectPaint() {
        return rectPaint;
    }

    public void setRectPaint(Paint rectPaint) {
        this.rectPaint = rectPaint;
    }
}
