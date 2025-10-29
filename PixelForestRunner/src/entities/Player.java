package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;

import animation.Animation;
import animation.AnimationManager;
import utils.AssetLoader; // 💡 ใช้ Package เดิมของคุณ
import core.GamePanel;      // 💡 ใช้ Package เดิมของคุณ
import entities.CharacterInfo; 

//? Player Class สืบทอดคุณสมบัติมาจาก GameObject
public class Player extends GameObject {
    //@ Attributes
    private GamePanel gamePanel; 
    private int hp = 100; 
    //* Physics 
    private double verticalSpeed = 0; 
    private double gravity = 0.5;
    private double jumpStrength = -12; 
    private int groundLevel = 480; 
    private boolean isAlive = true; 
    //* Player State Machine
    private enum PlayerState {
        RUNNING, JUMPING;
    }
    private PlayerState currentState;
    //* Animation 
    private AnimationManager animationManager;
    private Animation animRun, animJump;
    // ✅ เพิ่ม: เก็บข้อมูลตัวละครที่ถูกเลือก
    private CharacterInfo selectedInfo; 

    //@ Constructor
    public Player(int x, int y, int width, int height, GamePanel gamePanel) {
        super(x, y, width, height);
        this.gamePanel = gamePanel; 
        this.currentState = PlayerState.RUNNING;
        // 🚨 แก้ไข Hitbox Initialization ให้พอดีกับตัวละคร
        // 💡 OffsetX: ชดเชยจากซ้าย (35)
        int hitboxOffsetX = 44; 
        // 💡 HitboxWidth: ความกว้างของ Hitbox (40)
        int hitboxWidth = 40; 
        // 💡 OffsetY: ชดเชยจากบนลงมา (20)
        int hitboxOffsetY = 15; 
        // 💡 HitboxHeight: ความสูงของ Hitbox (80)
        int hitboxHeight = 105; 
        this.hitbox = new Rectangle(x + hitboxOffsetX, y + hitboxOffsetY, hitboxWidth, hitboxHeight);
        // ⚠️ เรียก loadAnimations และเก็บผลลัพธ์
        this.selectedInfo = loadAnimations(gamePanel.getSelectedCharacterId()); 
        
        this.animationManager = new AnimationManager(animRun);
    }

    //@ โหลดและตัดเฟรมอนิเมชัน
    private CharacterInfo loadAnimations(int characterId) { // ⚠️ เปลี่ยนให้ return CharacterInfo
        // 1. ค้นหาข้อมูลตัวละคร
        CharacterInfo info = null;
        for (CharacterInfo c : CharacterInfo.loadAllCharacters()) {
            if (c.getId() == characterId) {
                info = c;
                break;
            }
        }
        if (info == null) { 
            info = CharacterInfo.loadAllCharacters()[0]; 
        }
        
    // ✅ ใช้ขนาดจริงของ Sprite Sheet
    final int FRAME_WIDTH = 128;
    final int FRAME_HEIGHT = 128;
    final int FRAME_COUNT = 10;
    // โหลด Sprite Sheet
    BufferedImage runSheet = AssetLoader.loadSprite(info.getRunAsset());
    BufferedImage jumpSheet = AssetLoader.loadSprite(info.getJumpAsset());
    // ✅ ตัดทุกเฟรมในลูป
    BufferedImage[] runFrames = new BufferedImage[FRAME_COUNT];
    for (int i = 0; i < FRAME_COUNT; i++) {
        runFrames[i] = AssetLoader.getSubImage(runSheet, i, 0, FRAME_WIDTH, FRAME_HEIGHT);
    }
    // ✅ สร้างเฟรมของกระโดด (หลายเฟรม)
    BufferedImage[] jumpFrames = new BufferedImage[FRAME_COUNT];
    for (int i = 0; i < FRAME_COUNT; i++) {
        jumpFrames[i] = AssetLoader.getSubImage(jumpSheet, i, 0, FRAME_WIDTH, FRAME_HEIGHT);
    }
    // ✅ ใช้ทุกเฟรมสร้างแอนิเมชัน
    this.animRun = new Animation(runFrames, 80); // 80ms ต่อเฟรม
    this.animJump = new Animation(jumpFrames, 100);
        return info; // ⚠️ คืนค่า info กลับไป
    }

    @Override
    public void update() {
        if (y < groundLevel || verticalSpeed < 0) {
            verticalSpeed += gravity;
            y += verticalSpeed; 
            setState(PlayerState.JUMPING); 
        }
        if (y >= groundLevel && verticalSpeed > 0) {
            y = groundLevel;
            verticalSpeed = 0;
            setState(PlayerState.RUNNING); 
        }
        
        // 🚨 แก้ไข Hitbox Update 
        // -------------------------------------------------------------------
        // ต้องใช้ค่า Offset และขนาด Hitbox เดียวกับใน Constructor
        int hitboxOffsetX = 34;
        int hitboxWidth = 45;
        int hitboxOffsetY = 45;
        int hitboxHeight = 85;
        
        updateHitbox(this.x + hitboxOffsetX, this.y + hitboxOffsetY, hitboxWidth, hitboxHeight);
        // -------------------------------------------------------------------
    }

    private void setState(PlayerState newState) {
        if (currentState == newState) return; 
        currentState = newState;
        switch (newState) {
            case RUNNING:
                animationManager.setAnimation(animRun);
                break;
            case JUMPING:
                animationManager.setAnimation(animJump);
                break;
        }
    }

    @Override
    public void draw(Graphics g) {
        BufferedImage frame = animationManager.getFrameToDraw();
        if (frame != null) {
            g.drawImage(frame, x, y, width, height, null);
        } else {
            // Fallback สีม่วง
            g.setColor(Color.MAGENTA); 
            g.fillRect(x, y, width, height);
        }
        // วาด Hitbox
        drawHitbox(g);
    }
    
    public void handleKeyPress(int keyCode) {
        if (keyCode == KeyEvent.VK_SPACE) {
            jump();
        }
    }

    public void handleKeyRelease(int keyCode) {
        // ปล่อยปุ่มไม่มีผลกับผู้เล่นตอนนี้
    }

    public int getHp() { return hp; }
    public boolean isAlive() { return isAlive; }
    public AnimationManager getAnimationManager() { return animationManager; }
    
    // ✅ เพิ่ม: Getter Methods ที่หายไป (ใช้ข้อมูลจาก selectedInfo)
    public String getName() {
        return selectedInfo != null ? selectedInfo.getName() : "Unknown";
    }

    public int getId() {
        return selectedInfo != null ? selectedInfo.getId() : -1;
    }
    
    public void takeDamage(int damage) {
        if (!isAlive) return; 
        this.hp -= damage;
        if (this.hp <= 0) {
            this.hp = 0;
            die();
        }
    }
    public void die() {
        this.isAlive = false;
    }

    private void jump() {
        if (currentState != PlayerState.JUMPING) {
            verticalSpeed = jumpStrength;
            setState(PlayerState.JUMPING); 
        }
    }
}