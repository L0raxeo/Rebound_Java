package com.rebound.window;

import com.rebound.input.keyboard.KeyManager;
import com.rebound.input.mouse.MouseManager;
import com.rebound.scenes.GameScene;
import com.rebound.scenes.MenuScene;
import com.rebound.scenes.Scene;
import com.rebound.ui.GuiLayer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Window implements Runnable
{

    private static Window instance;

    private JFrame frame;
    private Canvas canvas;
    private final String title;
    private final int width, height;
    private final Dimension size;
    private Thread thread;
    private boolean running = false;

    private static final List<Scene> scenes = new ArrayList<>();
    private static Scene currentScene = null;
    private Color backdrop;
    private KeyManager keyListener;
    private MouseManager mouseListener;
    private GuiLayer guiLayer;

    private Window()
    {
        this.title = "Re:Bound";
        this.width = 480;
        this.height = 480;
        this.size = new Dimension(width, height);
    }

    /**
     * Changes scenes according to scene class in params.
     * The class was used in order to allow the user to
     * attempt to change scenes without having to create
     * the scene within the parameters of this method.
     */
    public static void changeScene(Class<?> sceneClass)
    {
        if (sceneClass.isInstance(Scene.class))
        {
            assert false : "Class '" + sceneClass + "' is not a subclass of Scene";
            return;
        }

        Scene targetScene = null;

        if (currentScene != null && currentScene.getClass().equals(sceneClass))
        {
            assert false : "Cannot change to current scene '" + currentScene + "'";
        }
        else
        {
            boolean sceneExists = false;

            for (Scene s : scenes)
                if (s.getClass().equals(sceneClass))
                {
                    sceneExists = true;
                    targetScene = s;
                    break;
                }

            if (!sceneExists)
            {
                try {
                    targetScene = (Scene) sceneClass.getDeclaredConstructor().newInstance();
                    scenes.add(targetScene);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                         NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }

            GuiLayer.getInstance().clear();

            if (currentScene != null)
                currentScene.onDestroy();
            currentScene = targetScene;
            currentScene.loadResources();
            currentScene.init();
            currentScene.start();
        }
    }

    public static Scene getScene()
    {
        return currentScene;
    }

    @Override
    public void run()
    {
        init();
        loop();
        stop();
    }

    private void init()
    {
        // Create JFrame
        frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        // Create Canvas
        canvas = new Canvas();
        canvas.setPreferredSize(size);
        canvas.setMaximumSize(size);
        canvas.setMinimumSize(size);
        canvas.setFocusable(false);

        // Combine JFrame and Canvas
        frame.add(canvas);
        frame.pack();

        // Create Input Listeners
        keyListener = new KeyManager();
        mouseListener = new MouseManager();

        // Add Input Listeners
        frame.addKeyListener(keyListener);
        canvas.addMouseListener(mouseListener);
        canvas.addMouseMotionListener(mouseListener);

        // GuiLayer
        guiLayer = GuiLayer.getInstance();

        setVisible(true);

        changeScene(MenuScene.class);
    }

    private void loop()
    {
        long lastTime = System.nanoTime();
        double timePerTick = 1000000000D / 60D;
        int ticks = 0;
        int frames = 0;
        long lastTimer = System.currentTimeMillis();
        double delta = 0;

        while (running)
        {
            long now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            lastTime = now;

            while (delta >= 1)
            {
                ticks++;
                update(delta);
                delta -= 1;
            }

            try
            {
                Thread.sleep(5);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            frames++;
            render();

            if (System.currentTimeMillis() - lastTimer >= 1000)
            {
                lastTimer += 1000;
                getFrame().setTitle(title + " | TPS: " + ticks + " FPS: " + frames);
                frames = 0;
                ticks = 0;
            }
        }
    }

    private void update(double dt)
    {
        mouseListener.update();
        keyListener.update();
        currentScene.update(dt);
    }

    private void render()
    {
        BufferStrategy bs = getCanvas().getBufferStrategy();

        if (bs == null)
        {
            getCanvas().createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        // clear screen
        g.clearRect(0, 0, getFrame().getWidth(), getFrame().getHeight());
        // render scene here

        g.setColor(backdrop);
        g.fillRect(0, 0, getWidth(), getHeight());

        currentScene.render(g);
        guiLayer.render(g);

        // end drawing
        bs.show();
        g.dispose();
    }

    public static Window getInstance()
    {
        if (instance == null)
            instance = new Window();

        return instance;
    }

    public static int getWidth()
    {
        return getInstance().width;
    }

    public static int getHeight()
    {
        return getInstance().height;
    }

    public void setVisible(boolean isVisible)
    {
        frame.setVisible(isVisible);
    }

    public Canvas getCanvas()
    {
        return canvas;
    }

    public JFrame getFrame()
    {
        return frame;
    }

    public static void setBackdrop(Color color)
    {
        getInstance().backdrop = color;
    }

    public synchronized void start()
    {
        running = true;

        thread = new Thread(this, "Tankit" + "_main");
        thread.start();
    }

    public synchronized void stop()
    {
        if (!running) return;
        running = false;

        try
        {
            thread.join();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

}
