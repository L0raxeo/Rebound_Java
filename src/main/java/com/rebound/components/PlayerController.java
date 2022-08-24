package com.rebound.components;

import com.rebound.input.keyboard.KeyManager;
import com.rebound.physics.Collision;
import com.rebound.physics.CollisionType;
import com.rebound.window.Camera;
import com.rebound.window.Window;
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
            rigidbody.moveX((float) (-3));

        if (KeyManager.isHeld(KeyEvent.VK_RIGHT))
            rigidbody.moveX((float) (3));

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

        // moves camera
        if (Window.getHeight() - gameObject.transform.getScreenPosition().y - Camera.yOffset() > (Window.getHeight() / 4f) * 3)
            Camera.move(new Vector2f(0, 4));

        if (gameObject.transform.position().x + gameObject.transform.scale.x < 0 && rigidbody.velocity.x < 0)
        {
            gameObject.transform.setPosition(new Vector2f(Window.getWidth(), gameObject.transform.position().y));
        }
        else if (gameObject.transform.position().x > Window.getWidth() && rigidbody.velocity.x > 0)
        {
            gameObject.transform.setPosition(new Vector2f(-gameObject.transform.scale.x, gameObject.transform.position().y));
        }
    }

    private void jump()
    {
        if (jumpedSinceGrounded == 0)
        {
            rigidbody.addForce(new Vector2f(0, -10));
            jumpedSinceGrounded++;
        }
        else if (jumpedSinceGrounded == 1)
        {
            rigidbody.addForce(new Vector2f(0, -5));
            jumpedSinceGrounded++;
        }
    }

    @Override
    public void onCollision(Collision collision)
    {
        if (collision.type == CollisionType.BOTTOM)
            jumpedSinceGrounded = 0;

        for (Component c : collision.collider.getAllComponents())
            c.onCollision(collision);
    }

}
