package gamestates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import core.GamePanel;

public class MenuState extends State {
    //@ Attributes
    private String[] options = {"Start Game", "Exit"};
    private int currentSelection = 0; //* Index 0 = Start, 1 = Exit
    
    //@ Constructor
    public MenuState(GamePanel gamePanel) {
        super(gamePanel);
    }
    
    //@ update() → อัปเดต Logic ของหน้าเมนู
    @Override
    public void update() {
        // หน้าเมนูไม่ต้องอัปเดตอะไร
    }

    //@ draw() → วาดหน้าเมนู
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 60));
        g.drawString("Pixel Forest Runner", 100, 200);

        g.setFont(new Font("Arial", Font.PLAIN, 30));
        
        // วาดตัวเลือก
        for (int i = 0; i < options.length; i++) {
            if (i == currentSelection) {
                g.setColor(Color.YELLOW);
            } else {
                g.setColor(Color.WHITE);
            }
            g.drawString(options[i], 300, 350 + (i * 50));
        }
    }

    @Override
    //@ ตรวจสอบการกดปุ่ม
    public void keyPressed(int keyCode) {
        if (keyCode == KeyEvent.VK_UP) {
            currentSelection = (currentSelection - 1 + options.length) % options.length;
        } else if (keyCode == KeyEvent.VK_DOWN) {
            currentSelection = (currentSelection + 1) % options.length;
        } else if (keyCode == KeyEvent.VK_ENTER) {
            //! แก้ไข: ลบโค้ดซ้ำซ้อน และใช้ logic ที่ถูกต้อง
            if (currentSelection == 0) { // Start Game
                gamePanel.setCurrentGameState(GameState.CHARACTER_SELECTION, 0); // ไปหน้าเลือกตัวละคร
            } else if (currentSelection == 1) { // Exit
                System.exit(0);
            }
        }
    }
}