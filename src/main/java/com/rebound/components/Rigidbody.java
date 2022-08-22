package com.rebound.components;

import com.rebound.physics.Bounds;
import com.rebound.physics.Collision;
import com.rebound.physics.CollisionType;
import org.joml.Vector2f;

public class Rigidbody extends Component
{

    public Vector2f velocity;
    private float friction;

    public Rigidbody(float friction)
    {
        this.velocity = new Vector2f();
        this.friction = friction;
    }

    @Override
    public void update(double dt)
    {
        boolean teleportY = false;
        boolean teleportX = false;

        if (gameObject.hasComponent(Bounds.class))
        {
            for (Collision collider : gameObject.getComponent(Bounds.class).findGameObjectsInPath(velocity))
            {
                if (collider.collider.equals(this.gameObject))
                    continue;

                if (collider.type == CollisionType.TOP && velocity.y < 0)
                {
                    velocity.y = 0;
                    gameObject.transform.position.y = collider.collider.transform.position.y + collider.collider.transform.scale.y;
                    teleportY = true;

                    //friction
                    if (velocity.x < 0)
                        velocity.x += friction / 10;
                    else if (velocity.x > 0)
                        velocity.x -= friction / 10;
                }
                else if (collider.type == CollisionType.BOTTOM && velocity.y > 0)
                {
                    velocity.y = 0;
                    gameObject.transform.position.y = collider.collider.transform.position.y - gameObject.transform.scale.y;
                    teleportY = true;

                    //friction
                    if (velocity.x < 0)
                        velocity.x += friction / 10;
                    else if (velocity.x > 0)
                        velocity.x -= friction / 10;
                }

                if (collider.type == CollisionType.RIGHT && velocity.x > 0)
                {
                    velocity.x = 0;
                    gameObject.transform.position.x = collider.collider.transform.position.x - gameObject.transform.scale.x;
                    teleportX = true;

                    //friction
                    if (velocity.y < 0)
                        velocity.y += friction / 10;
                    else if (velocity.y > 0)
                        velocity.y -= friction / 10;
                }
                else if (collider.type == CollisionType.LEFT && velocity.x < 0)
                {
                    velocity.x = 0;
                    gameObject.transform.position.x = collider.collider.transform.position.x + collider.collider.transform.scale.x;
                    teleportX = true;

                    //friction
                    if (velocity.y < 0)
                        velocity.y += friction / 10;
                    else if (velocity.y > 0)
                        velocity.y -= friction / 10;
                }

                for (Component c : gameObject.getAllComponents())
                    c.onCollision(collider);
            }
        }

        if (Math.abs(velocity.x) < 0.1)
            velocity.x = 0;
        if (Math.abs(velocity.y) < 0.1)
            velocity.y = 0;

        if (!teleportX)
            gameObject.transform.position.x += velocity.x * dt;

        if (!teleportY)
            gameObject.transform.position.y += velocity.y * dt;
    }

    public void addForce(Vector2f force)
    {
        velocity.add(force);
    }

    public void move(Vector2f velocity)
    {
        this.velocity = velocity;
    }

    public void moveX(float x)
    {
        this.velocity.x = x;
    }

    public void moveY(float y)
    {
        this.velocity.y = y;
    }

    public Vector2f getVelocity()
    {
        return velocity;
    }

    public float getFriction()
    {
        return friction;
    }

}
