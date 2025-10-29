package gamestates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.FontMetrics;

import core.GamePanel;

public class GameOverState extends State {
    //@ Attribute
    private int finalScore;

    //@ Constructor → รับคะแนนสุดท้ายมาแสดง
    public GameOverState(GamePanel gamePanel, int finalScore) {
        super(gamePanel); //* เรียก Constructor ของคลาสแม่
        this.finalScore = finalScore;
    }

    @Override
    public void update() {
        // ไม่ต้องทำอะไร
    }

    //@ draw() → วาดหน้าจอ Game Over
    @Override
    public void draw(Graphics g) {
        // ข้อความหลัก
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 72));
        FontMetrics fm1 = g.getFontMetrics();
        String overText = "GAME OVER";
        int overX = (GamePanel.SCREEN_WIDTH - fm1.stringWidth(overText)) / 2;
        int overY = GamePanel.SCREEN_HEIGHT / 2 - 100;
        g.drawString(overText, overX, overY);

        // คะแนนสุดท้าย
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 40));
        FontMetrics fm2 = g.getFontMetrics();
        String scoreText = "Final Score: " + finalScore;
        int scoreX = (GamePanel.SCREEN_WIDTH - fm2.stringWidth(scoreText)) / 2;
        int scoreY = overY + 100;
        g.drawString(scoreText, scoreX, scoreY);

        // ข้อความแนะนำ
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        FontMetrics fm3 = g.getFontMetrics();
        String restartText = "Press ENTER to Restart";
        int restartX = (GamePanel.SCREEN_WIDTH - fm3.stringWidth(restartText)) / 2;
        int restartY = scoreY + 100;
        g.drawString(restartText, restartX, restartY);
    }
    //@ keyPressed() → ตรวจสอบการกดปุ่ม
    @Override
    public void keyPressed(int keyCode) {
        if (keyCode == KeyEvent.VK_ENTER) {
            //* สั่ง GamePanel ให้เปลี่ยนกลับไปหน้า Menu
            gamePanel.setCurrentGameState(GameState.MENU, 0);
        }
    }
}