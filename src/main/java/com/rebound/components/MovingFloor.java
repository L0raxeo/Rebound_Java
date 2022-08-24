package com.rebound.components;

import com.rebound.dataStructure.Transform;
import com.rebound.physics.Collision;
import com.rebound.physics.CollisionType;
import com.rebound.window.Camera;

import java.awt.*;
import java.util.Random;

public class MovingFloor extends Component
{

    private int targetX;
    private int velX;

    @Override
    public void start()
    {
        velX = new Random().nextInt(2) + 1;
        targetX = (int) (gameObject.transform.position().x + 256);
    }

    @Override
    public void update(double dt)
    {
        if (gameObject.transform.position().x + gameObject.transform.scale.x > targetX && velX > 0)
        {
            velX = -velX;
            targetX -= 256;
        }
        else if (gameObject.transform.position().x < targetX && velX < 0)
        {
            velX = -velX;
            targetX += 256;
        }

        gameObject.getComponent(Rigidbody.class).moveX(velX);
    }

    @Override
    public void render(Graphics g)
    {
        Transform t = gameObject.transform;
        g.setColor(Color.GREEN);
        g.drawRect((int) (t.getScreenPosition().x + Camera.xOffset()), (int) (t.getScreenPosition().y + Camera.yOffset()), (int) t.scale.x, (int) t.scale.y);
    }

    @Override
    public void onCollision(Collision collision)
    {
        if (collision.origin.getName().equals("Player") && collision.type == CollisionType.BOTTOM)
            collision.origin.getComponent(Rigidbody.class).moveX(velX);
    }
}
