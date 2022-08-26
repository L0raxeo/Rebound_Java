package com.rebound.components;

import com.rebound.dataStructure.Transform;
import com.rebound.physics.Collision;
import com.rebound.window.Camera;

import java.awt.*;

public class Spikes extends Component
{
    private final Color color;

    public Spikes(Color color)
    {
        this.color = color;
    }

    @Override
    public void render(Graphics g)
    {
        Transform t = gameObject.transform;
        for (int i = 0; i < t.scale.x / 16; i++)
        {
            int[] xPoints = {
                    (int) (((t.getScreenPosition().x + Camera.xOffset()) + (16 / 2)) + (16 * i)),
                    (int) (((t.getScreenPosition().x + Camera.xOffset())) + (16 * i)),
                    (int) (((t.getScreenPosition().x + Camera.xOffset()) + 16 + (16 * i)))
            };
            int[] yPoints = {(int) (t.getScreenPosition().y + Camera.yOffset()), (int) (t.getScreenPosition().y + t.scale.y + Camera.yOffset()), (int) (t.getScreenPosition().y + t.scale.y + Camera.yOffset())};

            g.setColor(color);
            g.drawPolygon(xPoints, yPoints, 3);
        }
    }

    @Override
    public void onCollision(Collision collision)
    {
        if (collision.origin.hasComponent(PlayerController.class))
            collision.origin.die();
    }

}
