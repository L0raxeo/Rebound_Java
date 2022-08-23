package com.rebound.scenes;

import com.rebound.components.*;
import com.rebound.components.Component;
import com.rebound.objects.GameObject;
import com.rebound.prefabs.Prefabs;
import com.rebound.ui.GuiText;
import com.rebound.window.Camera;
import com.rebound.window.Window;
import org.joml.Vector2f;

import java.awt.*;
import java.util.ConcurrentModificationException;

public class GameScene extends Scene
{

    private int lastPlatformY;

    @Override
    public void init()
    {
        addGameObjectToScene(Prefabs.generate(
                "Player",
                new Vector2f((Window.getWidth() / 2f) - 16, 128),
                new Vector2f(32, 32), 0,
                new Rigidbody(1),
                new Physics2D(),
                new BoxBounds(),
                new PlayerController(),
                new RectRenderer(Color.RED, true)
        ));

        createFirstLevel();
    }

    private void createFirstLevel()
    {
        addGameObjectToScene(Prefabs.generate(
                "Floor",
                new Vector2f((Window.getWidth() / 2f) - 208, 64),
                new Vector2f(418, 32), 0,
                new BoxBounds(),
                new RectRenderer(Color.LIGHT_GRAY, false)
        ));

        addGameObjectToScene(Prefabs.generate(
                "Platform",
                new Vector2f(64, 160),
                new Vector2f(64, 16), 0,
                new BoxBounds(),
                new RectRenderer(Color.LIGHT_GRAY, false)
        ));

        addGameObjectToScene(Prefabs.generate(
                "Platform",
                new Vector2f(300, 224),
                new Vector2f(64, 16), 0,
                new BoxBounds(),
                new RectRenderer(Color.LIGHT_GRAY, false)
        ));

        addGameObjectToScene(Prefabs.generate(
                "Platform",
                new Vector2f(128, 380),
                new Vector2f(64, 16), 0,
                new BoxBounds(),
                new RectRenderer(Color.LIGHT_GRAY, false)
        ));

        addGameObjectToScene(Prefabs.generate(
                "Spikes",
                new Vector2f(64, 80),
                new Vector2f(16, 16), 0,
                new Spikes(Color.ORANGE),
                new Rigidbody(0),
                new DeathTrigger(),
                new BoxBounds()
        ));

        lastPlatformY = 380;
    }

    @Override
    public void update(double dt)
    {
        for (GameObject go : getGameObjects())
        {
            // destroys objects below the camera
            if (go.transform.getScreenPosition().y + Camera.yOffset() > Window.getHeight())
                go.die();

            go.update(dt);
        }

        getGameObjects().removeIf(GameObject::isDead);
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
