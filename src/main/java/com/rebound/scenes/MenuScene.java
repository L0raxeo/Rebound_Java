package com.rebound.scenes;

import com.rebound.dataStructure.AssetPool;
import com.rebound.ui.GuiButton;
import com.rebound.ui.GuiLayer;
import com.rebound.ui.GuiText;
import com.rebound.window.Window;
import org.joml.Vector2i;

import java.awt.*;

public class MenuScene extends Scene
{

    @Override
    public void loadResources()
    {
        AssetPool.getFont("assets/fonts/default_font.ttf", 24);
        AssetPool.getFont("assets/fonts/default_font.ttf", 48);
    }

    @Override
    public void init()
    {
        GuiLayer.getInstance().addGuiComponent(new GuiButton(
                "start_button",
                new Vector2i(240 - 115, 180 + 16),
                new Vector2i(230, 48),
                "Start Game",
                AssetPool.getFont("assets/fonts/default_font.ttf", 24),
                new Color[]{Color.LIGHT_GRAY, Color.DARK_GRAY},
                false,
                () -> {
                    Window.changeScene(GameScene.class);
                }
        ));
    }

    @Override
    public void gui(Graphics g)
    {
        GuiText.drawString(g,
                "RE:BOUND",
                new Vector2i(240, 116),
                true,
                Color.LIGHT_GRAY,
                AssetPool.getFont(
                        "assets/fonts/default_font.ttf", 48
                ));
    }

    @Override
    public void update(double dt)
    {
    }

    @Override
    public void render(Graphics g)
    {
        gui(g);
    }

}
