package nodomain.boulderdash.utils;

import android.graphics.Canvas;

import nodomain.boulderdash.GlobalVariables;

public class Vector2 {

    public float X;
    public float Y;

    public Vector2() {
        X = 0f;
        Y = 0f;
    }

    public Vector2(float x, float y) {
        X = x;
        Y = y;
    }

    public static Vector2 Zero() {
        return new Vector2(0f, 0f);
    }

    public static Vector2 One() {
        return new Vector2(1f, 1f);
    }

    public static Vector2 Center() {
        return new Vector2(GlobalVariables.ScreenWidth / 2, GlobalVariables.ScreenHeight / 2);
    }
}
