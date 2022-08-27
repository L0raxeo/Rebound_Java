package com.rebound.components;

import com.rebound.objects.GameObject;
import com.rebound.prefabs.Prefabs;
import com.rebound.window.Camera;
import com.rebound.window.Window;
import org.joml.Vector2f;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LevelGenerator extends Component
{

    public int nextGenerationLevel = 0;
    public int nextLevelY = 0;
    public Vector2f lastObjPos;

    @Override
    public void update(double dt)
    {
        if (Camera.getPosition().y > nextGenerationLevel)
            generate();
    }

    public void generate()
    {
        nextLevelY = (int) (lastObjPos.y);
        nextGenerationLevel += 20;

        for (GameObject go : getRandomPrefab(new Vector2f(new Random().nextInt(350 - 64) + 64, lastObjPos.y + (new Random().nextInt(140 - 80) + 80))))
        {
            if (Math.abs(lastObjPos.x - go.transform.position().x) > 350)
                go.transform.position().x -= 60;

            if (!go.hasComponent(MovingFloor.class))
            {
                if (go.transform.position().x < 90)
                    Window.getScene().addGameObjectToScene(getFloor(new Vector2f(go.transform.position().x + 300, lastObjPos.y + (new Random().nextInt(140 - 80) + 80)), true));
                else if (go.transform.position().x + go.transform.scale.x > 425)
                    Window.getScene().addGameObjectToScene(getFloor(new Vector2f(go.transform.position().x - 200, lastObjPos.y + (new Random().nextInt(140 - 80) + 80)), true));
            }

            Window.getScene().addGameObjectToScene(go);

            if (go.transform.position().y > lastObjPos.y)
                lastObjPos = go.transform.position();
        }
    }

    private List<GameObject> getRandomPrefab(Vector2f position)
    {
        if (position.x + 80 > lastObjPos.x && position.x < lastObjPos.x + 80)
        {
            position.x += 64;
        }

        if (position.x + 80 > lastObjPos.x && position.x < lastObjPos.x + 80)
        {
            position.x += -100;
        }

        int r = new Random().nextInt(7);

        return new ArrayList<>(getPrefabById(r, position));
    }

    private List<GameObject> getPrefabById(int id, Vector2f position)
    {
        List<GameObject> list = new ArrayList<>();

        switch (id) {
            case 1 -> list.add(getMovingFloor(position));
            case 2 -> list.addAll(getSpikedFloor(position, 2, 3));
            case 3 -> list.addAll(getFloorKillerWall(position));
            case 4 -> list.addAll(getFloorWall(position));
            case 5 -> list.add(getKillerFloorPrefab(position, true, true));
            case 6 -> list.add(getFloor(position, true));
        }

        return list;
    }

    public GameObject getMovingFloor(Vector2f position)
    {
        return Prefabs.generate(
                "Moving Floor",
                new Vector2f(80, position.y),
                new Vector2f(64, 16),
                new MovingFloor(),
                new BoxBounds(),
                new Rigidbody(1)
        );
    }

    public List<GameObject> getSpikedFloor(Vector2f position, int min, int max)
    {
        List<GameObject> spikedFloor = new ArrayList<>();
        spikedFloor.add(getFloor(position, false));
        int xIndex = new Random().nextInt(max + 1 - min) + min;
        spikedFloor.add(getSpikes(new Vector2f(position.x + (16 * xIndex), position.y + 16), new Vector2f(16, 16)));

        return spikedFloor;
    }

    public List<GameObject> getFloorKillerWall(Vector2f position)
    {
        List<GameObject> floorKillerWall = new ArrayList<>();
        floorKillerWall.add(getFloor(position, true));
        floorKillerWall.add(getKillerWallPrefab(new Vector2f(position.x + 34, position.y + 33), true));
        return floorKillerWall;
    }

    public List<GameObject> getFloorWall(Vector2f position)
    {
        List<GameObject> floorKillerWall = new ArrayList<>();
        floorKillerWall.add(getFloor(position, false));
        floorKillerWall.add(getWallPrefab(new Vector2f(position.x + 24, position.y + 33)));
        return floorKillerWall;
    }

    public GameObject getKillerFloorPrefab(Vector2f position, boolean killInterval, boolean randomX)
    {
        Vector2f size = new Vector2f(64, 16);

        if (randomX)
            size.x = 64 + (16 * new Random().nextInt(4));

        return Prefabs.generate(
                "Killer Floor",
                position,
                size,
                new KillerFloor(killInterval),
                new BoxBounds(),
                new Rigidbody(1)
        );
    }

    public GameObject getKillerWallPrefab(Vector2f position, boolean killInterval)
    {
        return Prefabs.generate(
                "Killer Floor",
                position,
                new Vector2f(16, 33),
                new KillerFloor(killInterval),
                new BoxBounds(),
                new Rigidbody(1)
        );
    }

    public GameObject getFloor(Vector2f position, boolean randomX)
    {
        Vector2f size = new Vector2f(80, 16);

        if (randomX)
            size.x = 64 + (16 * new Random().nextInt(4));

        return Prefabs.generate(
                "Floor",
                position,
                size,
                new BoxBounds(),
                new RectRenderer(Color.LIGHT_GRAY, false)
        );
    }

    public GameObject getWallPrefab(Vector2f position)
    {
        return Prefabs.generate(
                "Wall",
                position,
                new Vector2f(16, 33),
                new RectRenderer(Color.LIGHT_GRAY, false),
                new BoxBounds(),
                new Rigidbody(1)
        );
    }

    public GameObject getSpikes(Vector2f position, Vector2f scale)
    {
        return Prefabs.generate(
                "Spikes",
                position,
                scale,
                new Spikes(Color.ORANGE),
                new Rigidbody(0),
                new BoxBounds()
        );
    }

    @Override
    public void onDestroy() {
        nextGenerationLevel = 0;
        if (lastObjPos == null)
            lastObjPos = new Vector2f();
        else
        {
            lastObjPos.x = 0;
            lastObjPos.y = 0;
        }

        nextLevelY = 0;
    }
}
