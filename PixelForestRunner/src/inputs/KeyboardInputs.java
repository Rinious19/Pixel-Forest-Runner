package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import core.GamePanel;

public class KeyboardInputs implements KeyListener {
    private GamePanel gamePanel;
    //? Constructor
    //@ รับ GamePanel เข้ามาเพื่อใช้ส่งต่อ Event
    public KeyboardInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    //@ ถูกเรียกเมื่อ "กด" ปุ่มค้างไว้
    public void keyPressed(KeyEvent e) {
        // ส่งต่อรหัสปุ่ม (keyCode) ไปให้ State ปัจจุบันจัดการ
        gamePanel.getCurrentGameState().keyPressed(e.getKeyCode());
    }

    @Override
    //@ ถูกเรียกเมื่อ "ปล่อย" ปุ่ม
    public void keyReleased(KeyEvent e) {
        // ส่งต่อรหัสปุ่ม (keyCode) ไปให้ State ปัจจุบันจัดการ
        gamePanel.getCurrentGameState().keyReleased(e.getKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // เราไม่ได้ใช้เมธอดนี้
    }
}