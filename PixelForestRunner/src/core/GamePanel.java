package core; //? ระบุว่าไฟล์นี้อยู่ใน package core

import javax.swing.JPanel;

import entities.BackgroundInfo;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;

import gamestates.GameOverState;
import gamestates.GameState;
import gamestates.MenuState;
import gamestates.PlayingState;
import gamestates.State;
import inputs.KeyboardInputs;
import utils.AssetLoader;

import java.util.HashMap;
import java.util.Map;
import gamestates.SelectionState;
import java.awt.image.BufferedImage;

//? GamePanel สืบทอดจาก JPanel เพื่อให้วาดรูปได้และ implements Runnable เพื่อให้ทำงานบน Thread แยกได้
public class GamePanel extends JPanel implements Runnable {
    //@ ค่าคงที่สำหรับหน้าจอ (การสร้างค่าคงที่ไว้แบบนี้ช่วยให้แก้ไขขนาดจอได้ง่ายในที่เดียว)
    public static final int SCREEN_WIDTH = 800; //* ความกว้างของหน้าจอ
    public static final int SCREEN_HEIGHT = 600; //* ความสูงของหน้าจอ
    private static final int FPS = 60; //* กำหนดให้เกมวิ่งที่ 60 เฟรมต่อวินาที

    //@ ระบบของเกม 
    private Thread gameThread;
    private Map<GameState, State> gameStates; //* 1. ประกาศ HashMap
    private State currentGameState;           //* 2. ประกาศ State ปัจจุบัน
    private int selectedCharacterId = 1;      //* 3. ตัวแปรเก็บ ID ตัวละครที่เลือก
    private int selectedStageId = BackgroundInfo.STAGE_FOREST_OF_DUSK;
    private BufferedImage currentBackground; // 💡 ตัวแปรสำหรับเก็บรูปพื้นหลังจริง

    //@ Constructor ของ GamePanel
    public GamePanel() {
        //* ตั้งค่าขนาดที่ต้องการสำหรับ Panel นี้
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setFocusable(true); //* ทำให้ Panel นี้สามารถรับ input จากคีย์บอร์ดได้
        this.currentGameState = new MenuState(this); //* เริ่มเกมที่ฉากเล่น (PlayingState)
        //! เพิ่มตัวดักฟังคีย์บอร์ด
        this.addKeyListener(new KeyboardInputs(this));
        initClasses(); //* เรียก initClasses
    }
    private void initClasses() {
        this.gameStates = new HashMap<>(); //* 4. สร้าง Instance ของ HashMap
        // 5. สร้างและเก็บทุก State ที่จะใช้ซ้ำ (ยกเว้น PLAYING/GAME_OVER)
        gameStates.put(GameState.MENU, new MenuState(this));
        gameStates.put(GameState.CHARACTER_SELECTION, new SelectionState(this));
        currentGameState = gameStates.get(GameState.MENU); //* เริ่มที่ฉาก Menu
        loadGameAssets(this.selectedStageId); //* โหลด Asset พื้นหลังเริ่มต้น
    }

    //@ startGameThread() → จะถูกเรียก "หลังจาก" ที่ Panel ถูกเพิ่มเข้าไปใน Frame แล้ว 
    public void startGameThread() { //* เราใช้เมธอดนี้เพื่อเริ่มการทำงานของเกม
        gameThread = new Thread(this); //* สร้าง Thread ใหม่โดยใช้ GamePanel นี้ (ที่เป็น Runnable)
        gameThread.start(); //* เริ่มการทำงานของ Thread (จะไปเรียกเมธอด run())
    }

    //@ run() → เมธอดหลักของ Thread ที่จะควบคุม Game Loop ที่ทำงานตลอดเวลา
    @Override
    public void run() {
        //! Game Loop: หัวใจหลักของเกม 
        double drawInterval = 1_000_000_000.0 / FPS; //* คำนวณเวลาสำหรับแต่ละเฟรม (ในหน่วยนาโนวินาที)
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        //* ลูปนี้จะทำงานไปเรื่อยๆ ตราบใดที่ gameThread ยังไม่เป็น null
        while (gameThread != null) { //! เป็น null เมื่อ หยุดการทำงานของเกม เช่น exit, pause
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            //* ถ้าเวลาผ่านไปครบ 1 เฟรม
            if (delta >= 1) {
                //* 1. UPDATE: อัปเดตข้อมูลต่างๆ ของเกม (เช่น ตำแหน่งตัวละคร, คะแนน)
                update();
                //* 2. DRAW: วาดทุกอย่างใหม่บนหน้าจอ
                repaint(); //* เมธอดนี้จะไปเรียก paintComponent() ด้านล่าง
                delta--;
            }
        }
    }

    //@ update() → สำหรับอัปเดตสถานะของเกม
    public void update() {
        if (currentGameState != null) { //* ส่งต่องานให้ State ปัจจุบัน
            currentGameState.update();
        }
    }

    //@ paintComponent() → ใช้วาดทุกอย่างลงบน Panel
    @Override
    public void paintComponent(Graphics g) { //* Graphics g คือ "พู่กัน" ที่ระบบส่งมาให้เราวาด
        super.paintComponent(g); //! เป็นคำสั่งสำคัญ! เพื่อให้ JPanel จัดการการวาดพื้นฐานของตัวเองก่อน
        // 1. วาดพื้นหลัง (สีเทา หรือ รูปภาพด่าน)
        if (currentBackground != null) {
            // ✅ วาดพื้นหลังด่านที่โหลดมา (เมื่ออยู่ใน PLAYING State)
            g.drawImage(currentBackground, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
        } else {
            // วาดพื้นหลังสีเทาเมื่อไม่มีรูปภาพด่าน (เช่น ใน Menu State)
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        }
        // 2. วาดสิ่งต่างๆ โดยส่งต่องานให้ State ปัจจุบัน
        if (currentGameState != null) {
            currentGameState.draw(g);
        }
        g.dispose();
    }
    //@ getcurrentGameState() → ให้ GamePanel อื่นๆ เข้าถึง State ปัจจุบันได้    
    public State getCurrentGameState() {
        return currentGameState;
    }
    public void setSelectedCharacterId(int id) {
        this.selectedCharacterId = id;
    }
    
    public int getSelectedCharacterId() {
        return this.selectedCharacterId;
    }
    public void setSelectedStageId(int id) { // 💡 เพิ่มเมธอดนี้
        this.selectedStageId = id;
    }
    public int getSelectedStageId() { // 💡 เพิ่มเมธอดนี้
        return this.selectedStageId;
    }

    //? State Manager Method
    //@ เมธอดสำหรับสลับ State ของเกม
    public void setCurrentGameState(GameState newState, int score) {
        switch (newState) {
            case MENU:
            case CHARACTER_SELECTION:
                this.currentGameState = gameStates.get(newState);
                break;
            case PLAYING:
                // สร้างใหม่ทุกครั้งที่เริ่มเล่น
                this.currentGameState = new PlayingState(this); 
                break;
            case GAME_OVER:
                // สร้างใหม่ทุกครั้งที่จบเกม
                this.currentGameState = new GameOverState(this, score); 
                break;
            // case LEVEL_SELECTION: ... (สำหรับขั้นตอนถัดไป)
        }
        if (newState == GameState.PLAYING) {
        // โหลดทรัพยากรของด่านที่เลือก
            loadGameAssets(this.selectedStageId); 
        }
    }
    // 💡 เมธอดที่ใช้เริ่มเกม (อาจเรียกจาก setCurrentGameState)
    public void loadGameAssets(int stageId) {
    // 1. ค้นหาข้อมูลด่าน
    BackgroundInfo bgInfo = BackgroundInfo.getBackgroundById(stageId);
    
    // 2. โหลด Asset พื้นหลัง
    // AssetLoader ต้องมีเมธอด loadStaticSprite(String fileName)
    this.currentBackground = AssetLoader.loadStaticSprite(bgInfo.getAssetFileName()); 
    
    // 3. (ถ้ามี) โหลดข้อมูลแผนที่/Level data อื่น ๆ
    }
}