package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage; //* Import

import utils.AssetLoader;

public class Bush extends Obstacle {
    private BufferedImage sprite; //* เก็บ Sprite
    //@ Constructor → รับค่าต่างๆ แล้วส่งต่อไปให้คลาสแม่ (Obstacle)
    public Bush(int x, int y, int width, int height,
                int hitboxOffsetX, int hitboxOffsetY, 
                int hitboxWidth, int hitboxHeight) {
        super(x, y, width, height, hitboxOffsetX, hitboxOffsetY, hitboxWidth, hitboxHeight);
        this.sprite = AssetLoader.loadStaticSprite(AssetLoader.BUSH_SPRITE);
    }

    //@ draw() → กำหนดวิธีการวาด "พุ่มไม้"
    @Override
    public void draw(Graphics g) {
         if (sprite != null) {
            g.drawImage(sprite, x, y, width, height, null);
        } else {
            // Fallback
            g.setColor(new Color(0, 100, 0));
            g.fillRect(x, y, width, height);
        }
        //! DEBUG: วาด Hitbox
        drawHitbox(g);
    }
}