package entities; //! อย่าลืมใส่ package declaration

import java.awt.Graphics;

import core.GamePanel;

//? Obstacle (Abstract Class) → เป็นพิมพ์เขียวสำหรับสิ่งกีดขวางทุกชนิด และสืบทอดมาจาก GameObject
public abstract class Obstacle extends GameObject {
    //@ Attributes
    private int speed = 5; //* ความเร็วในการเคลื่อนที่ของสิ่งกีดขวาง

    //@ Constructor
    public Obstacle(int x, int y, int width, int height,
                    int hitboxOffsetX, int hitboxOffsetY, 
                    int hitboxWidth, int hitboxHeight) {
        super(x, y, width, height, hitboxOffsetX, hitboxOffsetY, hitboxWidth, hitboxHeight);
    }

    //@ update() → Logic การเคลื่อนที่และการรีเซ็ตตำแหน่ง
    @Override
    public void update() {
        //* Logic การเคลื่อนที่: ให้ x ลดลงตามค่า speed เพื่อให้เลื่อนจากขวามาซ้าย
        x -= speed;
        //* Logic การ Reset ตำแหน่ง: ถ้าสิ่งกีดขวางตกขอบจอด้านซ้าย ให้วาร์ปกลับไปด้านขวาเพื่อวนลูป
        if (x < -width) {
            x = GamePanel.SCREEN_WIDTH;
        }
        updateHitbox(); //* อัปเดตกล่องชนให้ตามทันตำแหน่งใหม่
    }

    //! draw() → เรายังคงบังคับให้คลาสลูกต้อง implement เมธอด draw() ของตัวเอง
    @Override
    public abstract void draw(Graphics g);

}