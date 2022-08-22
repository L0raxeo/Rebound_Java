package com.rebound.window;

import org.joml.Vector2f;

public class Camera
{

    private static Camera instance;

    private static float xOffset;
    private static float yOffset;

    public static void reset()
    {
        xOffset = 0;
        yOffset = 0;
    }

    // position on a normal coordinate plane
    public static void setPosition(Vector2f position)
    {
        xOffset = -position.x;
        yOffset = position.y;
    }

    public static void move(Vector2f vel)
    {
        xOffset -= vel.x;
        yOffset += vel.y;
    }


    public static float xOffset()
    {
        return xOffset;
    }

    public static float yOffset()
    {
        return yOffset;
    }

}
