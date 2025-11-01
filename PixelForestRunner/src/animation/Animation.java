package animation;

import java.awt.image.BufferedImage;

public class Animation {
    //@ Attribute
    private BufferedImage[] frames;
    private int currentFrameIndex;
    private long lastFrameTime;
    private long frameDelay; //* เวลา (ms) ที่จะแสดงแต่ละเฟรม

    //@ Constructor
    public Animation(BufferedImage[] frames, long frameDelay) {
        this.frames = frames;
        this.frameDelay = frameDelay;
        this.currentFrameIndex = 0;
        this.lastFrameTime = System.currentTimeMillis();
    }

    //@ อัปเดตเฟรมอนิเมชัน ทำให้เฟรมเปลี่ยนตามเวลา
    public void update() {
        if (System.currentTimeMillis() - lastFrameTime >= frameDelay) {
            currentFrameIndex++;
            if (currentFrameIndex >= frames.length) {
                currentFrameIndex = 0; //* วนกลับไปเฟรมแรก
            }
            lastFrameTime = System.currentTimeMillis();
        }
    }

    //@ ดึงเฟรมปัจจุบันไปวาด
    public BufferedImage getCurrentFrame() {
        return frames[currentFrameIndex];
    }
}