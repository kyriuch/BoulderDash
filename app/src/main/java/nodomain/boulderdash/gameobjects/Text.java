package nodomain.boulderdash.gameobjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import nodomain.boulderdash.memory.Memory;
import nodomain.boulderdash.utils.Vector2;

public class Text extends GameObject {

    private String text;

    private Paint paint;

    public Text(String text, Vector2 position) {
        this.text = text;
        this.position = position;

        this.paint = new Paint();
        paint.setTypeface(Memory.BoulderTypeface);
        paint.setTextSize(60f);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(Color.WHITE);
    }

    @Override
    public void Update() {

    }

    @Override
    public void Draw(Canvas canvas) {
        canvas.drawText(text, position.X, position.Y, paint);
    }

    public void SetTextSize(float size) {
        paint.setTextSize(size);
    }

}
