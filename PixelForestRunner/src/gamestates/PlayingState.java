package gamestates;

import java.awt.Color; //* Import
import java.awt.Font;  //* Import
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.FontMetrics;

import animation.AnimationManager;
import core.GamePanel;
import managers.EntityManager;
import managers.ObstacleManager;

public class PlayingState extends State implements Runnable {
    //@ Attribute
    private EntityManager entityManager;
    private ObstacleManager obstacleManager;

    private int score = 0;
    private Thread scoreThread; //* Thread ที่ 2
    private Thread animationThread; //* Thread 3 สำหรับจัดการแอนิเมชัน
    private boolean isPaused = false; //* เพิ่มสถานะ Pause

    //@ Constructor → รับ GamePanel เข้ามา
    public PlayingState(GamePanel gamePanel) { //* รับ GamePanel เข้ามา
        super(gamePanel);
        this.entityManager = new EntityManager(gamePanel);
        this.obstacleManager = new ObstacleManager(this.entityManager);
        startScoreThread(); //* เริ่ม Thread 2 นับคะแนน
        startAnimationThread(); //* เริ่ม Thread 3
    }

    //@ เริ่ม Thread ที่ 2 สำหรับนับคะแนน
    private void startScoreThread() {
        scoreThread = new Thread(this); // 'this' คือ PlayingState (Runnable)
        scoreThread.start();
    }

    //@ เริ่ม Thread ที่ 3 สำหรับอัปเดตอนิเมชัน
    private void startAnimationThread() {
        AnimationManager playerAnimManager = entityManager.getPlayer().getAnimationManager();
        if (playerAnimManager != null) {
            animationThread = new Thread(playerAnimManager);
            animationThread.start();
        }
    }

    @Override
    //@ นี่คือการทำงานของ Thread ที่ 2
    public void run() {
        while (entityManager.getPlayer().isAlive()) {
            try {
                //* 1. เพิ่มเงื่อนไขเช็ค Pause
                if (!isPaused) {
                    Thread.sleep(100); 
                    score += 1;
                } else {
                    // ถ้า Pause อยู่ ก็ให้ Thread "หลับ" ไปเฉยๆ
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //@ update() → อัปเดต Logic ของทุกอย่างที่อยู่ในฉากเล่นเกม
    @Override
    public void update() {
        //* สั่งให้ Thread 3 หยุดทำงานเมื่อผู้เล่นตาย
        if (!entityManager.getPlayer().isAlive()) {
            if (animationThread != null && animationThread.isAlive()) {
                entityManager.getPlayer().getAnimationManager().stop();
            }
            gamePanel.setCurrentGameState(GameState.GAME_OVER, score);
            return;
        }
        //! ถ้า Pause อยู่ ให้ข้ามการอัปเดตทั้งหมด
        if (isPaused) {
            return;
        }
        //* จะอัปเดตเกมก็ต่อเมื่อผู้เล่นยังมีชีวิตอยู่ และ ไม่ Pause ก็ให้อัปเดตเกมตามปกติ
        entityManager.update();
        obstacleManager.update();
    }

    //@ วาดทุกอย่างที่อยู่ในฉากเล่นเกม
    @Override
    public void draw(Graphics g) {
        //* สั่งให้ผู้จัดการแผนกนักแสดงวาดนักแสดงทั้งหมดลงบนจอ
        entityManager.draw(g);
        drawHUD(g); //* วาด HUD
        //! ถ้า Pause อยู่ ให้วาดหน้าจอ Pause ทับ
        if (isPaused) {
            drawPauseScreen(g);
        }
    }
    //@ drawPauseScreen() → วาดหน้าจอ Pause
private void drawPauseScreen(Graphics g) {
    // ฉากดำโปร่งแสง
    g.setColor(new Color(0, 0, 0, 150));
    g.fillRect(0, 0, GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT);

    // คำว่า "PAUSED"
    g.setColor(Color.WHITE);
    g.setFont(new Font("Arial", Font.BOLD, 80));
    FontMetrics fm = g.getFontMetrics();
    String text = "PAUSED";

    int textWidth = fm.stringWidth(text);
    int x = (GamePanel.SCREEN_WIDTH - textWidth) / 2;
    int y = (GamePanel.SCREEN_HEIGHT / 2) + (fm.getAscent() / 2);

    g.drawString(text, x, y);
}

    //@ วาดหน้าจอแสดงผล (HUD)
    private void drawHUD(Graphics g) {
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.setColor(Color.WHITE);
        //* วาด Score
        g.drawString("Score: " + score, 20, 30);
        //* วาด HP Bar
        int hp = entityManager.getPlayer().getHp();
        g.drawString("HP:", 20, 60);
        g.setColor(Color.GRAY);
        g.drawRect(70, 40, 100, 20); //* กรอบ HP
        g.setColor(Color.RED);
        g.fillRect(70, 40, hp, 20); //* แถบ HP (hp พอดี 100)
    }

    //? Input Handling
    @Override
    //@ keyPressed() → เมื่อมีการกดปุ่ม
    public void keyPressed(int keyCode) {
        //! เพิ่มการเช็คปุ่ม 'P'
        if (keyCode == KeyEvent.VK_P) {
            isPaused = !isPaused; //* สลับค่า true/false
        }
        //* สั่งให้ AnimationManager (Thread 3) หยุดด้วย
        entityManager.getPlayer().getAnimationManager().setPaused(isPaused);
        //* ส่งต่อ Input ให้ Player (ถ้าไม่ Pause)
        if (!isPaused) {
            entityManager.getPlayer().handleKeyPress(keyCode);
        }
    }

    @Override
    //@ keyReleased() → เมื่อมีการปล่อยปุ่ม
    public void keyReleased(int keyCode) {
        //* ส่งรหัสปุ่มไปให้ Player จัดการ (ถ้าไม่ Pause)
        if (!isPaused) {
            entityManager.getPlayer().handleKeyRelease(keyCode);
        }
    }
}