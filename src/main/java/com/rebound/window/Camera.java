package com.rebound.window;

import org.joml.Vector2f;

public class Camera
{

    private static float xOffset;
    private static float yOffset;

    public static void reset()
    {
        xOffset = 0;
        yOffset = 0;
    }

    /**
     * @param position world position
     */
    public static void setPosition(Vector2f position)
    {
        xOffset = -position.x;
        yOffset = position.y;
    }

    public static Vector2f getPosition()
    {
        return new Vector2f(-xOffset(), yOffset());
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
