package com.rebound.ui;

import org.joml.Vector2i;

import java.awt.*;

public abstract class GuiComponent
{

    protected String name;
    protected boolean hovering;
    protected Vector2i position;
    protected Vector2i scale;
    protected Rectangle bounds;

    public GuiComponent(String name, Vector2i position, Vector2i scale)
    {
        this.name = name;
        this.position = position;
        this.scale = scale;

        this.bounds = new Rectangle(position.x, position.y, scale.x, scale.y);
    }

    public abstract void update();

    public abstract void render(Graphics g);

    public abstract void onClick();

}
