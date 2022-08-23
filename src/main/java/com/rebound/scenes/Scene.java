package com.rebound.scenes;

import com.rebound.components.Component;
import com.rebound.objects.GameObject;
import com.rebound.window.Window;
import org.joml.Vector2f;
import org.joml.Vector4f;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class Scene
{

    protected List<GameObject> gameObjects = new ArrayList<>();
    protected Color backdrop = new Color(0, 0, 0, 0);

    private boolean isRunning = false;

    public Scene() {

    }

    public void init() {

    }

    public void start() {
        for (GameObject go : gameObjects) {
            go.start();
        }
        isRunning = true;
    }

    public void addGameObjectToScene(GameObject go) {
        if (!isRunning) {
            gameObjects.add(go);
        } else {
            gameObjects.add(go);
            go.start();
        }
    }

    public List<GameObject> getGameObjects(String name)
    {
        List<GameObject> gameObjects = new ArrayList<>();

        for (GameObject go : this.gameObjects)
            if (go.getName() != null && go.getName().equals(name))
                gameObjects.add(go);

        return gameObjects;
    }

    public GameObject getGameObject(int gameObjectId) {
        Optional<GameObject> result = this.gameObjects.stream()
                .filter(gameObject -> gameObject.getUid() == gameObjectId)
                .findFirst();
        return result.orElse(null);
    }

    public GameObject getGameObject(String name)
    {
        Optional<GameObject> result = this.gameObjects.stream()
                .filter(gameObject -> gameObject.getName().equals(name))
                .findFirst();
        return result.orElse(null);
    }

    public List<GameObject> getGameObjects(Vector2f coordinates)
    {
        return getGameObjects(coordinates.x, coordinates.y);
    }

    public List<GameObject> getGameObjects(float x, float y)
    {
        List<GameObject> gos = new ArrayList<>();

        for (GameObject go : gameObjects)
        {
            Vector2f pos = go.transform.getScreenPosition();

            float ax = pos.x;
            float ay = pos.y;
            float bx = pos.x + go.transform.scale.x;
            float by = pos.y + go.transform.scale.y;

            if (x > ax && x < bx && y > ay && y < by)
                gos.add(go);
        }

        return gos;
    }

    public List<GameObject> getGameObjects()
    {
        return this.gameObjects;
    }

    public List<GameObject> getGameObjectsWithComponent(Class<? extends Component> componentClass)
    {
        List<GameObject> result = new ArrayList<>();

        for (GameObject go : getGameObjects())
            if (go.hasComponent(componentClass))
                result.add(go);

        return result;
    }

    public abstract void update(double dt);
    public abstract void render(Graphics g);

    public void loadResources()
    {

    }

    protected void setBackdrop(Vector4f color)
    {
        this.backdrop = new Color(color.x, color.y, color.z, color.w);
        setBackdrop();
    }

    protected void setBackdrop(float r, float g, float b)
    {
        this.backdrop = new Color(r, g, b);
        setBackdrop();
    }

    protected void setBackdrop(Color color)
    {
        this.backdrop = color;
        setBackdrop();
    }

    private void setBackdrop()
    {
        Window.setBackdrop(this.backdrop);
    }

}
