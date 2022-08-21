package com.rebound.components;

import com.rebound.input.keyboard.KeyManager;
import org.joml.Vector2f;

import java.awt.*;
import java.awt.event.KeyEvent;

public class TestComponent extends Component
{

    @Override
    public void update(double dt)
    {
        if (KeyManager.onPress(KeyEvent.VK_UP))
            gameObject.getComponent(Rigidbody.class).addForce(new Vector2f(0, -10));

        if (KeyManager.onPress(KeyEvent.VK_LEFT))
            gameObject.getComponent(Rigidbody.class).addForce(new Vector2f(-5, 0));

        if (KeyManager.onPress(KeyEvent.VK_RIGHT))
            gameObject.getComponent(Rigidbody.class).addForce(new Vector2f(5, 0));

        if (KeyManager.onPress(KeyEvent.VK_SPACE))
        {
            gameObject.getComponent(Rigidbody.class).velocity.x = 0;
            gameObject.getComponent(Rigidbody.class).velocity.y = 0;
        }
    }

    @Override
    public void render(Graphics g)
    {
        g.setColor(Color.WHITE);
        g.fillRect((int) gameObject.transform.position.x, (int) gameObject.transform.position.y, (int) gameObject.transform.scale.x, (int) gameObject.transform.scale.y);
    }

}
