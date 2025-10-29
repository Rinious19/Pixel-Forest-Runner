package entities; //! อย่าลืมใส่ package declaration

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage; //* Import

import utils.AssetLoader;

//? Rock Class → คลาสสำหรับสิ่งกีดขวางประเภท "ก้อนหิน"
public class Rock extends Obstacle {
    private BufferedImage sprite; //* เก็บ Sprite
    //@ Constructor
    public Rock(int x, int y, int width, int height,
                int hitboxOffsetX, int hitboxOffsetY, 
                int hitboxWidth, int hitboxHeight) {
        super(x, y, width, height, hitboxOffsetX, hitboxOffsetY, hitboxWidth, hitboxHeight);
        this.sprite = AssetLoader.loadStaticSprite(AssetLoader.ROCK_SPRITE);
    }

    //@ draw() → วาดก้อนหินบนหน้าจอ
    @Override
    public void draw(Graphics g) {
        if (sprite != null) {
            g.drawImage(sprite, x, y, width, height, null);
        } else {
            // Fallback
            g.setColor(Color.GRAY);
            g.fillRect(x, y, width, height);
        }
        //! DEBUG: วาด Hitbox
        drawHitbox(g);
    }
}