package com.rebound.scenes;

import com.rebound.components.*;
import com.rebound.components.Component;
import com.rebound.objects.GameObject;
import com.rebound.prefabs.Prefabs;
import org.joml.Vector2f;

import java.awt.*;
import java.util.ConcurrentModificationException;

public class MainScene extends Scene
{

    @Override
    public void init()
    {
        addGameObjectToScene(Prefabs.generate(
                "Test",
                new Vector2f(240, 0),
                new Vector2f(32, 32), 0,
                new TestComponent(),
                new Rigidbody(1),
                new Physics2D(),
                new BoxBounds(new Rectangle(240, 0, 32, 32))
        ));

        addGameObjectToScene(Prefabs.generate(
                "Test_Platform",
                new Vector2f(50, 400),
                new Vector2f(400, 32), 0,
                new BoxBounds(new Rectangle(50, 400, 400, 32)),
                new TestPlatform()
        ));

        addGameObjectToScene(Prefabs.generate(
                "Test_Wall",
                new Vector2f(100, 50),
                new Vector2f(32, 400), 0,
                new TestPlatform(),
                new BoxBounds(new Rectangle(100, 40, 32, 200))
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
