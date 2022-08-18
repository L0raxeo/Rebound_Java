package com.rebound.scenes;

import com.rebound.components.Component;
import com.rebound.components.TestComponent;
import com.rebound.objects.GameObject;
import com.rebound.objects.Prefabs;
import org.joml.Vector2f;

import java.awt.*;

public class MainMenu extends Scene
{

    @Override
    public void init()
    {
        addGameObjectToScene(Prefabs.generateSpriteObject("Test Poly", new Vector2f(240, 240), new Vector2f(16, 16), 0, new TestComponent()));
    }

    @Override
    public void update()
    {
        for (GameObject go : getGameObjects())
        {
            go.update();
        }
    }

    @Override
    public void render(Graphics g)
    {
        for (GameObject go : getGameObjects())
        {
            for (Component c : go.getAllComponents())
                c.render(g);
        }
    }

}
