package com.rebound.scenes;

import com.rebound.components.Component;
import com.rebound.objects.GameObject;

import java.awt.*;
import java.util.ConcurrentModificationException;

public class MainScene extends Scene
{

    @Override
    public void init()
    {

    }

    @Override
    public void update(double dt)
    {
        for (GameObject go : getGameObjects())
        {
            go.update(dt);
        }
    }

    @Override
    public void render(Graphics g)
    {
        try
        {
            for (GameObject go : getGameObjects())
            {
                for (Component c : go.getAllComponents())
                    c.render(g);
            }
        }
        catch (ConcurrentModificationException ignore) {}
    }

}
