package com.rebound.components;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;

public class TestComponent extends Component
{



    @Override
    public void render(Graphics g)
    {
        int x = (int) gameObject.transform.position.x;
        int y = (int) gameObject.transform.position.y ;
        Polygon p = new Polygon();

        p.addPoint(x, y);
        p.addPoint(x + 64, y);
        p.addPoint(x + 64, y + 16);
        p.addPoint(x, y + 16);

        g.setColor(Color.RED);
        g.fillPolygon(buildPolygon(x, y, p.xpoints, p.ypoints, 0));
    }

    // TODO abstract this somewhere idk where tho
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
    private Polygon buildPolygon(int centerX, int centerY, int[] xp, int[] yp, double rotationAngle) throws IllegalArgumentException {
        // copy the arrays so that we dont manipulate the originals, that way we can
        // reuse them if necessary
        int[] xpoints = Arrays.copyOf(xp,xp.length);
        int[] ypoints = Arrays.copyOf(yp,yp.length);
        if(xpoints.length != ypoints.length){
            throw new IllegalArgumentException("The provided x points are not the same length as the provided y points.");
        }

        // create a list of Point2D pairs
        ArrayList<Point2D> list = new ArrayList();
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
