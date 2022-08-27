package com.rebound.file;

import java.awt.*;
import java.io.*;

public class FileLoader
{

    public static Font loadFont(String path, float size)
    {
        try
        {
            return Font.createFont(Font.TRUETYPE_FONT, new File(path)).deriveFont(Font.PLAIN, size);
        }
        catch (FontFormatException | IOException e)
        {
            e.printStackTrace();
            System.exit(1);
        }

        return null;
    }

    public static String readFile(String path) throws IOException
    {
        BufferedReader br = new BufferedReader(new FileReader(path));
        return br.readLine();
    }

    public static void writeFile(String path, String data) throws IOException
    {
        BufferedWriter bw = new BufferedWriter(new FileWriter(path));
        bw.write(String.valueOf(data));
        bw.flush();
        bw.close();
    }

}
