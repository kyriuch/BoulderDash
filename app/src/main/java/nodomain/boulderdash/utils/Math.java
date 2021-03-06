package nodomain.boulderdash.utils;

public class Math {
    public static float Clamp(float min, float max, float value) {
        if(value < min)
            return min;
        else if(value > max)
            return max;

        return value;
    }

    public static int Lerp(int min, int max, double value) {
        return (int)(min + value * (max - min));
    }

    public static float Lerp(float min, float max, float value) {
        return min + value * (max - min);
    }
}
