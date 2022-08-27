package com.rebound.dataStructure;

import com.rebound.file.FileLoader;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class AssetPool
{

    //          String, Font Size
    private static final Map<String, Font> fonts = new HashMap<>();

    public static Font getFont(String resource, int size)
    {
        Font font;

        if (!fonts.containsKey(resource))
        {
            font = FileLoader.loadFont(resource, size);
            fonts.put(resource, font);
        }
        else
        {
            if (fonts.get(resource).getSize() != size)
            {
                font = FileLoader.loadFont(resource, size);
                fonts.put(resource, font);
            }

            for (Map.Entry<String, Font> fontSet : fonts.entrySet())
            {
                if (fontSet.getKey().equals(resource) && fontSet.getValue().getSize() == size)
                    return fontSet.getValue();
            }
        }

        return null;
    }

}
