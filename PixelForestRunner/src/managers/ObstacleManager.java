package managers;

import entities.Bush;
import entities.Rock;

public class ObstacleManager {
    //@ Attribute
    private EntityManager entityManager;
    private long spawnTimer; //* ตัวจับเวลา
    private long spawnInterval = 2000; //* 2000ms = 2 วินาที

    //@ Constructor → รับ EntityManager เข้ามาเพื่อใช้ในการเพิ่ม Obstacle และเริ่มจับเวลา
    public ObstacleManager(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.spawnTimer = System.currentTimeMillis();
    }

    //@ update() → ตรวจสอบตลอดเวลาว่าถึงเวลาสร้าง Obstacle ใหม่หรือยัง
    public void update() {
        if (System.currentTimeMillis() - spawnTimer >= spawnInterval) {
            spawnObstacle(); //* ถ้าครบ 2 วินาที ให้สร้าง
            spawnTimer = System.currentTimeMillis(); //* Reset ตัวจับเวลา
        }
    }

    //@ spawnObstacle() → สุ่มและสร้าง Obstacle ชิ้นใหม่
    private void spawnObstacle() {
        //* สุ่มเลข 0-1 ถ้ามากกว่า 0.5 จะสร้าง Rock ถ้าน้อยกว่าจะสร้าง Bush  
        if (Math.random() > 0.5) {
            //* สร้าง Rock ที่ y=536, ขนาด 80x55
            entityManager.addObstacle(new Rock(800, 545, 80, 55, 
            10, 5, 60, 50));
        } else {
            //* สร้าง Bush ที่ y=568, ขนาด 
            entityManager.addObstacle(new Bush(800, 545, 108, 70,
            15,15,75,40));
        }
    }
}