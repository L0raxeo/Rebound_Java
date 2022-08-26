package com.rebound.components;

import com.rebound.dataStructure.Transform;
import com.rebound.physics.Collision;
import com.rebound.window.Camera;

import java.awt.*;
import java.util.Random;

public class KillerFloor extends Component
{

    private final boolean killInterval;
    private boolean isKilling = true;
    private long nextInterval;

    public KillerFloor(boolean killInterval)
    {
        this.killInterval = killInterval;

        if (killInterval)
            if (new Random().nextBoolean())
                nextInterval = System.currentTimeMillis();
            else
                nextInterval = System.currentTimeMillis() + new Random().nextInt(2500);
    }

    @Override
    public void update(double dt)
    {
        if (killInterval)
        {
            if (System.currentTimeMillis() > nextInterval)
            {
                isKilling = !isKilling;
                nextInterval = System.currentTimeMillis() + 2500;
            }
        }
    }

    @Override
    public void render(Graphics g)
    {
        Transform t = gameObject.transform;

        if (isKilling)
        {
            g.setColor(Color.ORANGE);
            g.drawRect((int) (t.getScreenPosition().x + Camera.xOffset()), (int) (t.getScreenPosition().y + Camera.yOffset()), (int) t.scale.x, (int) t.scale.y);
        }
        else
        {
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect((int) (t.getScreenPosition().x + Camera.xOffset()), (int) (t.getScreenPosition().y + Camera.yOffset()), (int) t.scale.x, (int) t.scale.y);
        }
    }

    @Override
    public void onCollision(Collision collision)
    {
        if (collision.origin.hasComponent(PlayerController.class) && isKilling)
            collision.origin.die();
    }

}
