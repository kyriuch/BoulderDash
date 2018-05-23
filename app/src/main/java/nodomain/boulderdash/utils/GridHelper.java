package nodomain.boulderdash.utils;

import android.graphics.Rect;

public class GridHelper {
    public static Rect getRect(int xIndex, int yIndex) {
        return new Rect(16 * (xIndex - 1) + 1, 16 * (yIndex - 1) + 1, xIndex * 16, yIndex * 16);
    }
}
