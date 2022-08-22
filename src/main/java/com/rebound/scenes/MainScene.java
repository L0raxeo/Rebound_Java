package com.rebound.scenes;

import com.rebound.components.*;
import com.rebound.components.Component;
import com.rebound.objects.GameObject;
import com.rebound.prefabs.Prefabs;
import com.rebound.window.Window;
import org.joml.Vector2f;

import java.awt.*;
import java.util.ConcurrentModificationException;

public class MainScene extends Scene
{

    @Override
    public void init()
    {
        addGameObjectToScene(Prefabs.generate(
                "Player",
                new Vector2f(240, 200),
                new Vector2f(32, 32), 0,
                new Rigidbody(1),
                new Physics2D(),
                new BoxBounds(new Rectangle()),
                new PlayerController(),
                new RectRenderer(Color.RED, true)
        ));

        addGameObjectToScene(Prefabs.generate(
                "Floor",
                new Vector2f(50, 400),
                new Vector2f(400, 32), 0,
                new BoxBounds(new Rectangle()),
                new RectRenderer(Color.GRAY, false)
        ));
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
