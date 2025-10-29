package entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Color; //* Import Color

//? GameObject (Abstract Class)
//@ เป็นพิมพ์เขียวสำหรับวัตถุทุกชิ้นในเกม
public abstract class GameObject {
    //@ Attributes
    //* เราใช้ protected เพื่อให้คลาสลูก (เช่น Player) สามารถเข้าถึงตัวแปรเหล่านี้ได้โดยตรง
    protected int x, y;
    protected int width, height;
    protected Rectangle hitbox; //* กล่องสำหรับชน
    protected int hitboxOffsetX, hitboxOffsetY;

    //@ Constructor
    public GameObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        //* สร้าง Bounding Box ขึ้นมาพร้อมกับ Object
        this.hitbox = new Rectangle(x, y, width, height);
        //! กำหนดค่าเริ่มต้นให้ Player (Player จะจัดการ hitbox เอง)
        this.hitboxOffsetX = 0;
        this.hitboxOffsetY = 0;
    }
    //@ รับค่า Offset และขนาดของ Hitbox มาโดยตรง
    public GameObject(int x, int y, int width, int height, 
                      int hitboxOffsetX, int hitboxOffsetY, 
                      int hitboxWidth, int hitboxHeight) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.hitboxOffsetX = hitboxOffsetX;
        this.hitboxOffsetY = hitboxOffsetY;
        // สร้าง Hitbox ตามที่กำหนดมา
        this.hitbox = new Rectangle(x + hitboxOffsetX, 
                                y + hitboxOffsetY, 
                                hitboxWidth, 
                                hitboxHeight);
    }
    //@ Abstract Methods
    //* เมธอดที่ไม่มีการ implement(ไม่มีปีกกา) บังคับให้คลาสลูกต้องไปเขียนโค้ดเอง เรียกว่า "Abstraction"
    public abstract void update();
    public abstract void draw(Graphics g);
    
    //? Bounding Box Methods
    //@ อัปเดตตำแหน่งของ Hitbox ให้ตามทันตำแหน่งของ Object
    protected void updateHitbox() {
        // อัปเดต x, y ของ Hitbox ให้ตรงกับ Object
        // (ส่วนขนาดและความกว้างจะคงที่)
        this.hitbox.x = this.x + this.hitboxOffsetX;
        this.hitbox.y = this.y + this.hitboxOffsetY;
    }
    
    //@ อัปเดต Hitbox (สำหรับ Player ที่ขนาดเปลี่ยน)
    protected void updateHitbox(int x, int y, int width, int height) {
        this.hitbox.x = x;
        this.hitbox.y = y;
        this.hitbox.width = width;
        this.hitbox.height = height;
    }

    //@ ส่งกล่องนี้ออกไปให้คนอื่นเช็ค
    public Rectangle getHitbox() {
        return this.hitbox;
    }
    //? DEBUG: เมธอดสำหรับวาด Hitbox ให้เราเห็น
    protected void drawHitbox(Graphics g) {
        g.setColor(Color.RED);
        g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
    }
}