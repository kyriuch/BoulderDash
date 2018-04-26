package nodomain.boulderdash.gameobjects;

import android.graphics.Canvas;

import nodomain.boulderdash.utils.Vector2;

public abstract class GameObject {

    protected Vector2 position;

    public void Move(float x) {
        position.X += x;
    }

    public void Move(float x, float y) {
        position.X += x;
        position.Y += y;
    }

    public abstract void Update();
    public abstract void Draw(Canvas canvas);
}
