package com.rebound.components;

import com.rebound.objects.GameObject;
import com.rebound.physics.Collision;
import com.rebound.physics.Trigger;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class Component
{

    // component class in general
    private static int ID_COUNTER = 0;
    // associated with individual components/objects
    private int uid = -1;

    private boolean disabled = false;

    public transient GameObject gameObject = null;

    public void start()
    {

    }

    public void update(double dt){}

    public void render(Graphics g) {}

    public void collision(Collision collision) {}

    public void trigger(Trigger trigger) {}

    public void onCollision(Collision collision) {}

    public void generateId()
    {
        if (this.uid == -1)
            this.uid = ID_COUNTER++;
    }

    public int getUid()
    {
        return this.uid;
    }

    public static void init(int maxId)
    {
        ID_COUNTER = maxId;
    }

    public void disable()
    {
        this.disabled = true;
    }

    public void enable()
    {
        this.disabled = false;
    }

    public boolean isDisabled()
    {
        return this.disabled;
    }

    /**
     * Builds a polygon from a set of points, rotated around a point, at the
     * specified rotation angle.
     *
     * @param centerX the int center x coordinate around which to rotate
     * @param centerY the int center y coordinate around which to rotate
     * @param xp the int[] of x points which make up our polygon points. This
     *           array is parallel to the yp array where each index in this array
     *           corresponds to the same index in the yp array.
     * @param yp the int[] of y points which make up our polygon points. This
     *           array is parallel to the xp array where each index in this array
     *           corresponds to the same index in the xp array.
     * @param rotationAngle the double angle in which to rotate the provided
     *                      coordinates (specified in degrees).
     * @return a Polygon of the provided coordinates rotated around the center point
     *         at the specified angle.
     * @throws IllegalArgumentException when the provided x points array is not the
     *                                  same length as the provided y points array
     */
    public Polygon buildPolygon(int centerX, int centerY, int[] xp, int[] yp, double rotationAngle) throws IllegalArgumentException {
        // copy the arrays so that we dont manipulate the originals, that way we can
        // reuse them if necessary
        int[] xpoints = Arrays.copyOf(xp,xp.length);
        int[] ypoints = Arrays.copyOf(yp,yp.length);
        if(xpoints.length != ypoints.length){
            throw new IllegalArgumentException("The provided x points are not the same length as the provided y points.");
        }

        // create a list of Point2D pairs
        ArrayList<Point2D> list = new ArrayList<>();
        for(int i = 0; i < ypoints.length; i++){
            list.add(new Point2D.Double(xpoints[i], ypoints[i]));
        }

        // create an array which will hold the rotated points
        Point2D[] rotatedPoints = new Point2D[list.size()];

        // rotate the points
        AffineTransform transform = AffineTransform.getRotateInstance(Math.toRadians(rotationAngle), centerX, centerY);
        transform.transform(list.toArray(new Point2D[0]), 0, rotatedPoints, 0, rotatedPoints.length);

        // build the polygon from the rotated points and return it
        int[] ixp = new int[list.size()];
        int[] iyp = new int[list.size()];
        for(int i = 0; i < ixp.length; i++){
            ixp[i] = (int)rotatedPoints[i].getX();
            iyp[i] = (int)rotatedPoints[i].getY();
        }
        return new Polygon(ixp, iyp, ixp.length);
    }

}
