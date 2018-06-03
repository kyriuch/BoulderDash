package nodomain.boulderdash.utils;

import android.graphics.Rect;

public class GridHelper {
    public static Rect getRect(int xIndex, int yIndex) {
        return new Rect(32 * (xIndex - 1) + 1, 32 * (yIndex - 1) + 1, xIndex * 32, yIndex * 32);
    }
}
