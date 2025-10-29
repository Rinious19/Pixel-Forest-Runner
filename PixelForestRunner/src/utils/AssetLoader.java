package utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class AssetLoader {
    // Attributes: ชื่อไฟล์
    public static final String ROCK_SPRITE = "rock_grass.png";
    public static final String BUSH_SPRITE = "bush.png";

    // CORE LOADING METHOD: ใช้ /res/ นำหน้า path 
    public static BufferedImage loadSprite(String path) {
        BufferedImage image = null;
        // 💡 ใช้ /res/ นำหน้าตามโครงสร้างใหม่
        try (InputStream is = AssetLoader.class.getResourceAsStream("/res/" + path)) { 
            if (is == null) {
                System.err.println("❌ ERROR: Asset not found. Path checked: /res/" + path);
                return createFallbackImage(path, 80, 80); 
            }
            image = ImageIO.read(is);
        } catch (IOException e) {
            System.err.println("❌ ERROR: Failed to load image " + path + ": " + e.getMessage());
            e.printStackTrace();
            return createFallbackImage(path, 80, 80);
        }
        return image;
    }
    
    // 💡 loadStaticSprite สำหรับเรียกใช้ Asset ทั่วไป (Rock, Bush)
    public static BufferedImage loadStaticSprite(String fileName) {
        return loadSprite(fileName);
    }
    
    // UTILITY & FALLBACK METHODS

    private static BufferedImage createFallbackImage(String path, int width, int height) {
        // (โค้ด Fallback Image สีแดง)
        BufferedImage fallback = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = fallback.getGraphics();
        g.setColor(Color.RED.darker()); 
        g.fillRect(0, 0, width, height);
        g.setColor(Color.WHITE);
        g.drawString("FILE ERR", 5, height / 2); 
        g.dispose();
        return fallback;
    }

    // เมธอดตัดเฟรม
    public static BufferedImage getSubImage(BufferedImage sheet, int xIndex, int yIndex, int width, int height) {
        if (sheet == null) {
            return createFallbackImage("Null Sheet", width, height); 
        }
        try {
            return sheet.getSubimage(xIndex * width, yIndex * height, width, height);
        } catch (Exception e) {
            System.err.println("❌ ERROR: Sub-image index/size out of bounds. Check dimensions!");
            return createFallbackImage("OOB", width, height);
        }
    }
}