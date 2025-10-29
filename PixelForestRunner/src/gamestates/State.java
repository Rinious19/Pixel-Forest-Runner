package gamestates;

import java.awt.Graphics;

import core.GamePanel;

public abstract class State {
    //@ Attribute
    protected GamePanel gamePanel; //* เพิ่มตัวแปร

    //? Constructor
    //@ รับ GamePanel มาเก็บไว้
    public State(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    //@ Abstract Methods
    public abstract void update();
    public abstract void draw(Graphics g);

    //? Input Methods → เพิ่มเมธอดว่างๆ สำหรับรับ Input
    //@ คลาสลูกสามารถเลือก override เฉพาะที่ต้องใช้ได้
    public void keyPressed(int keyCode) {}
    public void keyReleased(int keyCode) {}
}