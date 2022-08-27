package com.rebound.prefabs;

import com.rebound.components.Component;
import com.rebound.objects.GameObject;
import com.rebound.dataStructure.Transform;
import org.joml.Vector2f;

public class Prefabs
{

    public static GameObject generate(String name, Vector2f pos, Vector2f size, Component... comps)
    {
        GameObject go = new GameObject(name, new Transform(pos, size));

        for (Component c : comps)
            go.addComponent(c);

        return go;
    }

}
