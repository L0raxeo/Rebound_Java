package com.rebound.physics;

import com.rebound.dataStructure.Transform;
import com.rebound.objects.GameObject;
import org.joml.Vector2f;

import static com.rebound.physics.CollisionType.*;

public class Collision
{

    public final CollisionType type;
    public final GameObject collider;

    public Collision(Vector2f velocity, Transform originTransform, GameObject collider)
    {
        this.collider = collider;
        this.type = resolveCollision(originTransform, collider.transform, velocity);
    }

    private CollisionType resolveCollision(Transform ot, Transform ct, Vector2f velocity)
    {
        float ox1 = ot.getScreenPosition().x;
        float ox2 = ox1 + ot.scale.x;
        float oy1 = ot.getScreenPosition().y;
        float oy2 = oy1 + ot.scale.y;
        float cx1 = ct.getScreenPosition().x;
        float cx2 = cx1 + ct.scale.x;
        float cy1 = ct.getScreenPosition().y;
        float cy2 = cy1 + ct.scale.y;
        float oCenterX = ot.getScreenPosition().x + (ot.scale.x / 2);
        float oCenterY = ot.getScreenPosition().y + (ot.scale.y / 2);
        float cCenterX = ct.getScreenPosition().x + (ct.scale.x / 2);
        float cCenterY = ct.getScreenPosition().y + (ct.scale.y / 2);

        // Q1
        if (oCenterX < cCenterX && oCenterY < cCenterY)
        {
            if (ox2 - cx1 > oy2 - cy1)
                return BOTTOM;
            else
                return RIGHT;
        }
        // Q2
        else if (oCenterX > cCenterX && oCenterY < cCenterY)
        {
            if (cx2 - ox1 > oy2 - cy1)
                return BOTTOM;
            else return LEFT;
        }
        // Q3
        else if (oCenterX > cCenterX && oCenterY > cCenterY)
        {
            if (cx2 - ox1 > cy2 - oy1)
                return TOP;
            else return LEFT;
        }
        // Q4
        else if (oCenterX < cCenterX && oCenterY > cCenterY)
        {
            if (ox2 - cx1 > cy2 - oy1)
                return TOP;
            else return RIGHT;
        }

        return NONE;
    }

}
