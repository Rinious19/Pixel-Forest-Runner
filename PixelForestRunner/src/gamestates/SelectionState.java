package gamestates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.FontMetrics;
// ⚠️ ไม่ต้องใช้ import ที่เกี่ยวข้องกับรูปภาพแล้ว (BufferedImage, AssetLoader)

import core.GamePanel;
import entities.CharacterInfo; 
import entities.BackgroundInfo; 
import gamestates.State;
import utils.AssetLoader;       

public class SelectionState extends State {
    
    // โหมดการเลือก: CHARACTER -> STAGE
    private enum SelectionMode { CHARACTER, STAGE }
    private SelectionMode currentMode = SelectionMode.CHARACTER;
    
    //@ Attributes สำหรับ Character
    private CharacterInfo[] characters;
    private int characterIndex = 0; 
    private final int NUM_CHARACTERS;
    
    //@ Attributes สำหรับ Stage
    private BackgroundInfo[] stages;
    private int stageIndex = 0; 
    private final int NUM_STAGES;
    
    public SelectionState(GamePanel gamePanel) {
        super(gamePanel);
        // โหลดข้อมูลตัวละคร
        this.characters = CharacterInfo.loadAllCharacters();
        this.NUM_CHARACTERS = characters.length;
        
        // โหลดข้อมูลด่าน
        this.stages = BackgroundInfo.loadAllBackgrounds();
        this.NUM_STAGES = stages.length;
    }

    @Override
    public void update() {}

    @Override
public void draw(Graphics g) {
    // พื้นหลัง
    g.setColor(Color.BLACK);
    g.fillRect(0, 0, GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT);

    g.setColor(Color.WHITE);

    if (currentMode == SelectionMode.CHARACTER) {
        // หัวข้อหลัก
        g.setFont(new Font("Arial", Font.BOLD, 40));
        drawCenteredText(g, "SELECT YOUR HERO", 150);

        // วาดตัวละคร
        drawCharacterSelection(g);

        // ข้อความล่าง
        g.setFont(new Font("Arial", Font.PLAIN, 22));
        drawCenteredText(g, "Press ENTER to select hero", GamePanel.SCREEN_HEIGHT - 50);

    } else if (currentMode == SelectionMode.STAGE) {
        // หัวข้อหลัก
        g.setFont(new Font("Arial", Font.BOLD, 40));
        drawCenteredText(g, "SELECT YOUR STAGE", 150);

        // วาดด่าน
        drawStageSelection(g);

        // ข้อความล่าง
        g.setFont(new Font("Arial", Font.PLAIN, 22));
        drawCenteredText(g, "Press ENTER to start game", GamePanel.SCREEN_HEIGHT - 50);
    }

    // คำแนะนำสลับโหมด
    g.setColor(Color.CYAN);
    g.setFont(new Font("Arial", Font.PLAIN, 20));
    drawCenteredText(g, "Press TAB to switch between Hero and Stage selection",
                     GamePanel.SCREEN_HEIGHT - 100);
}

// 💡 เมธอดช่วยจัดข้อความให้อยู่ตรงกลาง
    private void drawCenteredText(Graphics g, String text, int y) {
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int x = (GamePanel.SCREEN_WIDTH - textWidth) / 2;
        g.drawString(text, x, y);
    }
    
    // 💡 เมธอดสำหรับวาดการเลือกตัวละคร (ใช้ชื่อและกรอบสี่เหลี่ยมแทนรูป)
    private void drawCharacterSelection(Graphics g) {
        int itemWidth = 180; // ความกว้างของกรอบ
        int itemHeight = 150;
        int spacing = 20; // ระยะห่างระหว่างตัวเลือก
        // 1. คำนวณความกว้างรวมทั้งหมด
        int totalWidth = (NUM_CHARACTERS * itemWidth) + ((NUM_CHARACTERS - 1) * spacing);
        int startX = (GamePanel.SCREEN_WIDTH - totalWidth) / 2; // ตรงกลางจอแนวนอน
        int yPos = 250;
        for (int i = 0; i < NUM_CHARACTERS; i++) {
            CharacterInfo info = characters[i];

            int xPos = startX + (i * (itemWidth + spacing));

            // 2. วาดกรอบตัวเลือก
            g.setColor(Color.DARK_GRAY);
            g.fillRect(xPos, yPos, itemWidth, itemHeight);

            // 3. วาดกรอบไฮไลต์
            g.setColor(i == characterIndex ? Color.YELLOW : Color.GRAY);
            g.drawRect(xPos - 5, yPos - 5, itemWidth + 10, itemHeight + 10);

            // 4. วาดชื่อให้อยู่กึ่งกลางกรอบ (แนวนอน)
            g.setFont(new Font("Arial", Font.BOLD, 20));
            FontMetrics fm = g.getFontMetrics(g.getFont());
            String displayText = "[" + info.getId() + "] " + info.getName();
            int textWidth = fm.stringWidth(displayText);
            int textX = xPos + (itemWidth - textWidth) / 2;

            // 5. วาดชื่อให้อยู่ “ตรงกลางกรอบแนวดิ่ง” (หรือใต้กรอบเล็กน้อย)
            int textY = yPos + itemHeight + fm.getAscent() + 10;

            g.setColor(Color.WHITE);
            g.drawString(displayText, textX, textY);
        }
    }
    
    // 💡 เมธอดสำหรับวาดการเลือกด่าน (ใช้ชื่อและกรอบสี่เหลี่ยมแทนรูป)
    private void drawStageSelection(Graphics g) {
        for (int i = 0; i < NUM_STAGES; i++) {
            BackgroundInfo info = stages[i];
            
            int xPos = 70 + (i * 350); 
            int yPos = 200; 
            int rectWidth = 320;
            int rectHeight = 180;
            
            // วาดกรอบสี่เหลี่ยมแทนรูปภาพ
            g.setColor(Color.DARK_GRAY);
            g.fillRect(xPos, yPos, rectWidth, rectHeight);
            BufferedImage preview = AssetLoader.loadStaticSprite(info.getAssetFileName()); 
            if (preview != null) {
                // กำหนดขนาดและตำแหน่งของภาพพรีวิว
                int imgWidth = rectWidth - 20; 
                int imgHeight = rectHeight - 20;
                int imgX = xPos + 10; 
                int imgY = yPos + 10; 
                
                g.drawImage(preview, imgX, imgY, imgWidth, imgHeight, null); 
            }
            // วาดกรอบเน้นสีเหลือง
            g.setColor(i == stageIndex ? Color.YELLOW : Color.GRAY);
            g.drawRect(xPos - 5, yPos - 5, rectWidth + 10, rectHeight + 10);
            
            // วาดชื่อ
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString(info.getName(), xPos + 10, yPos + rectHeight + 30);
        }
    }

    @Override
    public void keyPressed(int keyCode) {
        // 💡 เพิ่มการสลับโหมดด้วย TAB
        if (keyCode == KeyEvent.VK_TAB) {
            currentMode = (currentMode == SelectionMode.CHARACTER) ? SelectionMode.STAGE : SelectionMode.CHARACTER;
            return;
        }
        
        if (currentMode == SelectionMode.CHARACTER) {
            // โหมดเลือกตัวละคร
            if (keyCode == KeyEvent.VK_LEFT) {
                characterIndex = (characterIndex - 1 + NUM_CHARACTERS) % NUM_CHARACTERS;
            } else if (keyCode == KeyEvent.VK_RIGHT) {
                characterIndex = (characterIndex + 1) % NUM_CHARACTERS;
            } else if (keyCode == KeyEvent.VK_ENTER) {
                // 1. เก็บ ID ของตัวละครที่เลือก
                int selectedId = characters[characterIndex].getId();
                gamePanel.setSelectedCharacterId(selectedId); 

                // 2. เปลี่ยนโหมดไปเลือกด่าน
                currentMode = SelectionMode.STAGE;
            }
        } else if (currentMode == SelectionMode.STAGE) {
            // โหมดเลือกด่าน
            if (keyCode == KeyEvent.VK_LEFT) {
                stageIndex = (stageIndex - 1 + NUM_STAGES) % NUM_STAGES;
            } else if (keyCode == KeyEvent.VK_RIGHT) {
                stageIndex = (stageIndex + 1) % NUM_STAGES;
            } else if (keyCode == KeyEvent.VK_ENTER) {
                // 1. เก็บ ID ของด่านที่เลือก
                int selectedId = stages[stageIndex].getId();
                gamePanel.setSelectedStageId(selectedId); 

                // 2. ไป State เล่นเกม
                gamePanel.setCurrentGameState(GameState.PLAYING, 0); 
            }
        }
    }
}