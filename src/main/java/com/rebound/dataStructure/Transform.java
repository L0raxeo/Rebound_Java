package com.rebound.dataStructure;

import com.rebound.window.Window;
import org.joml.Vector2f;

import java.awt.*;

public class Transform
{

    private Vector2f position;

    public Vector2f screenPosition;

    public Vector2f scale;

    public Transform()
    {
        init(new Vector2f(), new Vector2f());
    }

    public Transform(Vector2f position)
    {
        init(position, new Vector2f());
    }

    public Transform(Vector2f position, Vector2f scale)
    {
        init(position, scale);
    }

    /**
     * Screen to world coordinates gets translated here
     */
    public void init(Vector2f position, Vector2f scale)
    {
        // world to screen coordinates
        this.position = new Vector2f(position.x, Window.getHeight() - position.y);
        this.scale = scale;
    }

    // returns world coordinates
    public Vector2f position()
    {
        return new Vector2f(position.x, Window.getHeight() - position.y);
    }

    public Vector2f getScreenPosition()
    {
        return this.position;
    }

    /**
     * @param position in world coords
     */
    public void setPosition(Vector2f position)
    {
        this.position = new Vector2f(position.x, Window.getHeight() - position.y);
    }

    public void setScreenPosition(Vector2f position)
    {
        this.position = position;
    }

    public Transform copy()
    {
        return new Transform(new Vector2f(this.position), new Vector2f(this.scale));
    }

    public void copy(Transform to)
    {
        to.position.set(this.position);
        to.scale.set(this.scale);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) return false;
        if (!(obj instanceof Transform)) return false;

        Transform t = (Transform) obj;
        return t.position.equals(this.position) && t.scale.equals(this.scale);
    }

}
