package com.rebound.scenes;

import com.rebound.components.*;
import com.rebound.components.Component;
import com.rebound.dataStructure.Transform;
import com.rebound.objects.GameObject;
import com.rebound.prefabs.Prefabs;
import com.rebound.window.Camera;
import com.rebound.window.Window;
import org.joml.Vector2f;

import java.awt.*;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class GameScene extends Scene
{

    private LevelGenerator levelGenerator;

    @Override
    public void init()
    {
        addGameObjectToScene(Prefabs.generate(
                "Level Generator",
                new Vector2f(),
                new Vector2f(), 0,
                new LevelGenerator()
        ));

        levelGenerator = getGameObject("Level Generator").getComponent(LevelGenerator.class);
        levelGenerator.nextLevelY = 0;

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

        addGameObjectToScene(levelGenerator.getFloor(new Vector2f(64, 160), false));

        addGameObjectToScene(levelGenerator.getKillerFloorPrefab(new Vector2f(300, 224), true, false));

        for (GameObject go : levelGenerator.getSpikedFloor(new Vector2f(128, 380), 0, 0))
            addGameObjectToScene(go);

        levelGenerator.lastObjPos = new Vector2f(128, 380);

        addGameObjectToScene(levelGenerator.getSpikes(new Vector2f(64, 80), new Vector2f(16 * 3, 16)));
        levelGenerator.generate();
    }

    @Override
    public void update(double dt)
    {
        try {
            for (GameObject go : getGameObjects()) {
                // destroys objects below the camera
                if (go.transform.getScreenPosition().y + Camera.yOffset() > Window.getHeight() && !go.equals(levelGenerator.gameObject))
                    go.die();

                go.update(dt);
            }
        }
        catch(ConcurrentModificationException ignore) {
            for (GameObject go : getGameObjects()) {
                // destroys objects below the camera
                if (go.transform.getScreenPosition().y + Camera.yOffset() > Window.getHeight() && !go.equals(levelGenerator.gameObject))
                    go.die();

                go.update(dt);
            }
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
        catch (ConcurrentModificationException ignore) {
            for (GameObject go : getGameObjects())
            {
                for (Component c : go.getAllComponents())
                    c.render(g);
            }
        }
    }

}
