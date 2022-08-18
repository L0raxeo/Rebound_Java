package com.rebound.components;

import com.rebound.objects.GameObject;

import java.awt.*;

public abstract class Component
{

    // component class in general
    private static int ID_COUNTER = 0;
    // associated with individual components/objects
    private int uid = -1;

    private boolean disabled = false;
    private boolean isDebug = false;

    public transient GameObject gameObject = null;

    public void start()
    {

    }

    public void update(){}

    public void render(Graphics g) {}

    public void generateId()
    {
        if (this.uid == -1)
            this.uid = ID_COUNTER++;
    }

    public int getUid()
    {
        return this.uid;
    }

    public static void init(int maxId)
    {
        ID_COUNTER = maxId;
    }

    public void disable()
    {
        this.disabled = true;
    }

    public void enable()
    {
        this.disabled = false;
    }

    public boolean isDisabled()
    {
        return this.disabled;
    }

    public void setDebug(boolean isDebug)
    {
        this.isDebug = isDebug;

        if (isDebug)
            disable();
    }

    /**
     * @return whether this component is part of the debug/F3 menu
     */
    public boolean isDebug()
    {
        return isDebug;
    }

}
