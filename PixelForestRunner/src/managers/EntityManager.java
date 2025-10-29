package managers;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import core.GamePanel;
import entities.Obstacle;
import entities.Player;

import java.util.Iterator; //* Import Iterator

public class EntityManager {
    //@ Attribute
    private Player player;
    private List<Obstacle> obstacles;

    //@ Constructor → สร้าง Player และเตรียม List ว่างๆ สำหรับเก็บสิ่งกีดขวาง
    //? แก้ไข: เหลือเพียง Constructor เดียวที่รับ GamePanel
    public EntityManager(GamePanel gamePanel) { 
        // สร้าง Player โดยส่ง GamePanel เข้าไป
        // ใช้ค่าเริ่มต้นที่เหมาะสมสำหรับ Player (x=50, y=480, w=80, h=120)
        this.player = new Player(50, 400, 128, 128, gamePanel); 
        this.obstacles = new ArrayList<>();
    }
    //? ลบ Constructor เดิม: public EntityManager() { this.player = new Player(100, 472, 128, 128); this.obstacles = new ArrayList<>(); }
    
    //@ update() → อัปเดตสถานะของ Entity ทุกชิ้นที่ดูแลอยู่
    public void update() {
        player.update();
        //* ใช้ Iterator เพื่อให้ลบ Obstacle ระหว่างวนลูปได้อย่างปลอดภัย
        Iterator<Obstacle> iterator = obstacles.iterator();
        while (iterator.hasNext()) {
            Obstacle obs = iterator.next();
            obs.update();
            //* (เราอาจจะเพิ่ม Logic ลบ Obstacle ที่ตกขอบซ้ายไปแล้ว)
        }
        //* ตรวจสอบการชน
        checkCollisions();
    }
    //@ ตรวจสอบการชน
    private void checkCollisions() {
        //* เช็คเฉพาะตอนที่ Player ยังไม่ตาย
        if (!player.isAlive()) return;

        Iterator<Obstacle> iterator = obstacles.iterator();
        while (iterator.hasNext()) {
            Obstacle obs = iterator.next();

            //? หัวใจของการชน: .intersects()
            if (player.getHitbox().intersects(obs.getHitbox())) {
                player.takeDamage(10); //* ลด HP
                iterator.remove();     //* ลบสิ่งกีดขวางทิ้ง
            }
        }
    }
    //@ draw() → วาด Entity ทุกชิ้นที่ดูแลอยู่ลงบนจอ
    public void draw(Graphics g) {
        player.draw(g);
        for (Obstacle obs : obstacles) {
            obs.draw(g);
        }
    }

    //@ addObstacle() → เมธอดสำหรับให้ภายนอก (ObstacleManager) เพิ่มสิ่งกีดขวางเข้ามาใน List
    public void addObstacle(Obstacle obs) {
        this.obstacles.add(obs);
    }
    //? Getter
    //@ สำหรับให้ภายนอก (PlayingState) เรียกใช้ Player ได้
    public Player getPlayer() {
        return this.player;
    }
}