package com.rebound.components;

import com.rebound.input.keyboard.KeyManager;
import com.rebound.physics.Collision;
import com.rebound.physics.CollisionType;
import org.joml.Vector2f;

import java.awt.event.KeyEvent;

public class PlayerController extends Component
{

    private Rigidbody rigidbody;
    private int jumpedSinceGrounded = 0;
    private boolean isGrounded;

    @Override
    public void start()
    {
        rigidbody = gameObject.getComponent(Rigidbody.class);
    }

    @Override
    public void update(double dt)
    {
        if (KeyManager.isHeld(KeyEvent.VK_LEFT))
            rigidbody.moveX(-3);

        if (KeyManager.isHeld(KeyEvent.VK_RIGHT))
            rigidbody.moveX(3);

        if (KeyManager.onRelease(KeyEvent.VK_LEFT))
        {
            rigidbody.moveX(0);
            rigidbody.addForce(new Vector2f(-1, 0));
        }

        if (KeyManager.onRelease(KeyEvent.VK_RIGHT))
        {
            rigidbody.moveX(0);
            rigidbody.addForce(new Vector2f(1, 0));
        }

        if (KeyManager.onPress(KeyEvent.VK_UP))
            jump();
    }

    private void jump()
    {
        if (jumpedSinceGrounded < 2)
        {
            rigidbody.addForce(new Vector2f(0, -10));
            jumpedSinceGrounded++;
        }
    }

    @Override
    public void onCollision(Collision collision)
    {
        if (collision.type == CollisionType.BOTTOM)
            jumpedSinceGrounded = 0;
    }

}
