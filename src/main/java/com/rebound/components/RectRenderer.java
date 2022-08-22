package com.rebound.components;

import com.rebound.dataStructure.Transform;
import com.rebound.window.Camera;

import java.awt.*;

public class RectRenderer extends Component
{

    private Color color;
    private boolean isSolid;

    public RectRenderer(Color color, boolean isSolid)
    {
        this.color = color;
        this.isSolid = isSolid;
    }

    @Override
    public void render(Graphics g)
    {
        Transform t = gameObject.transform;
        g.setColor(this.color);
        if (isSolid)
            g.fillRect((int) (t.position.x + Camera.xOffset()), (int) (t.position.y + Camera.yOffset()), (int) t.scale.x, (int) t.scale.y);
        else
            g.drawRect((int) (t.position.x + Camera.xOffset()), (int) (t.position.y + Camera.yOffset()), (int) t.scale.x, (int) t.scale.y);
    }

    public void setColor(Color color)
    {
        this.color = color;
    }

    public void setSolid(boolean isSolid)
    {
        this.isSolid = isSolid;
    }

}
