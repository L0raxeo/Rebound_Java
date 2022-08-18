package com.rebound.objects;

import com.rebound.components.Component;
import org.joml.Vector2f;

public class Prefabs
{

    public static GameObject generateSpriteObject(String name, Vector2f pos, Vector2f size, int zIndex, Component... comps)
    {
        GameObject go = new GameObject(name, new Transform(pos, size), zIndex);

        for (Component c : comps)
            go.addComponent(c);

        return go;
    }

}
