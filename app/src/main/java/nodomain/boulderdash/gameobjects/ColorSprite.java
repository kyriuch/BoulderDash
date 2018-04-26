package nodomain.boulderdash.gameobjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import nodomain.boulderdash.memory.Memory;
import nodomain.boulderdash.utils.Vector2;

public class ColorSprite extends GameObject {

    private int color;

    public ColorSprite(int color) {
        this.color = color;
    }

    @Override
    public void Update() {

    }

    @Override
    public void Draw(Canvas canvas) {
        canvas.drawColor(color);
    }

    public void SetColor(int color) {
        this.color = color;
    }
}
