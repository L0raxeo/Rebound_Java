package com.rebound.scenes;

import com.rebound.components.*;
import com.rebound.components.Component;
import com.rebound.dataStructure.AssetPool;
import com.rebound.file.FileLoader;
import com.rebound.objects.GameObject;
import com.rebound.prefabs.Prefabs;
import com.rebound.ui.GuiText;
import com.rebound.window.Camera;
import com.rebound.window.Window;
import org.joml.Vector2f;
import org.joml.Vector2i;

import java.awt.*;
import java.io.IOException;
import java.util.ConcurrentModificationException;

public class GameScene extends Scene
{

    private LevelGenerator levelGenerator;
    private GameObject player;
    private int points = 0;

    @Override
    public void loadResources()
    {
        setBackdrop(Color.DARK_GRAY);
        AssetPool.getFont("assets/fonts/default_font.ttf", 24);
    }

    @Override
    public void init()
    {
        addGameObjectToScene(Prefabs.generate(
                "Player",
                new Vector2f((Window.getWidth() / 2f) - 16, 128),
                new Vector2f(32, 32),
                new Rigidbody(1),
                new Physics2D(),
                new BoxBounds(),
                new PlayerController(),
                new RectRenderer(Color.RED, true)
        ));

        player = getGameObject("Player");

        createFirstLevel();
    }

    private void createFirstLevel()
    {
        addGameObjectToScene(Prefabs.generate(
                "Level Generator",
                new Vector2f(),
                new Vector2f(),
                new LevelGenerator()
        ));

        levelGenerator = getGameObject("Level Generator").getComponent(LevelGenerator.class);
        levelGenerator.nextLevelY = 0;

        addGameObjectToScene(Prefabs.generate(
                "Floor",
                new Vector2f((Window.getWidth() / 2f) - 208, 64),
                new Vector2f(418, 32),
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
    public void gui(Graphics g)
    {
        GuiText.drawString(g, String.valueOf(points), new Vector2i(16, 32), false, Color.LIGHT_GRAY, AssetPool.getFont("assets/fonts/default_font.ttf", 24));
    }

    @Override
    public void update(double dt)
    {
        if (player != null && points < (int) player.transform.position().y)
            points = (int) player.transform.position().y;

        try {
            for (GameObject go : getGameObjects()) {
                // destroys objects below the camera
                if (go.transform.getScreenPosition().y + Camera.yOffset() > Window.getHeight() && !go.equals(levelGenerator.gameObject))
                    go.die();

                go.update(dt);
            }
        }
        catch(ConcurrentModificationException e) {
            try
            {
                for (GameObject go : getGameObjects()) {
                    // destroys objects below the camera
                    if (go.transform.getScreenPosition().y + Camera.yOffset() > Window.getHeight() && !go.equals(levelGenerator.gameObject))
                        go.die();

                    go.update(dt);
                }
            }
            catch (ConcurrentModificationException ignore) {}
        }

        getGameObjects().removeIf(GameObject::isDead);
    }

    @Override
    public void render(Graphics g)
    {
        gui(g);

        try
        {
            for (GameObject go : getGameObjects())
            {
                for (Component c : go.getAllComponents())
                    c.render(g);
            }
        }
        catch (ConcurrentModificationException e) {
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

    @Override
    public void onDestroy()
    {
        try {
            if (Integer.parseInt(FileLoader.readFile("assets/saves/save.txt")) < points)
                FileLoader.writeFile("assets/saves/save.txt", String.valueOf(points));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Camera.reset();
        points = 0;

        getGameObjects().clear();
    }

}
