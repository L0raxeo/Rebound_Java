package com.rebound.components;

import java.awt.*;

public class TestPlatform extends Component
{

    @Override
    public void render(Graphics g)
    {
        g.setColor(Color.WHITE);
        g.fillRect((int) gameObject.transform.position.x, (int) gameObject.transform.position.y, (int) gameObject.transform.scale.x, (int) gameObject.transform.scale.y);
    }
}
