package com.rebound.physics;

import com.rebound.components.Component;
import com.rebound.objects.GameObject;
import com.rebound.window.Window;
import org.joml.Vector2f;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

enum BoundsType {
    BOX,
}

public abstract class Bounds extends Component
{

    protected Rectangle bounds;

    public Bounds(Rectangle bounds)
    {
        this.bounds = bounds;
    }

    @Override
    public void update(double dt)
    {
        bounds.setRect(gameObject.transform.position.x, gameObject.transform.position.y, gameObject.transform.scale.x, gameObject.transform.scale.y);
    }

    public List<Collision> findGameObjectsInPath(Vector2f velocity)
    {
        List<Collision> result = new ArrayList<>();

        Rectangle2D predictedBounds = new Rectangle((int) (bounds.x + velocity.x), (int) (bounds.y + velocity.y), bounds.width, bounds.height);

        for (GameObject go : Window.getScene().getGameObjectsWithComponent(Bounds.class))
        {
            if (go.equals(this.gameObject))
                continue;

            if (predictedBounds.intersects(go.getComponent(Bounds.class).bounds))
            {
                result.add(new Collision(velocity, gameObject.transform, go));
            }
        }

        return result;
    }

}
