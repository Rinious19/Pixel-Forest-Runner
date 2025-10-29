package animation;

import java.awt.image.BufferedImage;

public class AnimationManager implements Runnable {
    //@ Attribute
    private Animation currentAnimation;
    private boolean isRunning = true;
    private volatile boolean isPaused = false;

    //@ Constructor
    public AnimationManager(Animation startAnimation) {
        this.currentAnimation = startAnimation;
    }

    @Override
    //@ การทำงานของ Thread 3
    public void run() {
        while (isRunning) {
            try {
                //* เพิ่มเงื่อนไขเช็ค Pause
                if (!isPaused) {
                    if (currentAnimation != null) {
                        currentAnimation.update();
                    }
                }
                Thread.sleep(17);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //@ ดึงเฟรมปัจจุบันไปวาด
    public BufferedImage getFrameToDraw() {
        if (currentAnimation != null) {
            return currentAnimation.getCurrentFrame();
        }
        return null; // หรือ return ภาพ default
    }
    
    //@ สลับอนิเมชัน (เช่น จากวิ่งเป็นกระโดด)
    public void setAnimation(Animation newAnimation) {
        this.currentAnimation = newAnimation;
    }
    
    //@ ตั้งสถานะ Pause/Resume
    public void setPaused(boolean isPaused) {
        this.isPaused = isPaused;
    }
    
    //@ หยุดการทำงานของ AnimationManager
    public void stop() {
        this.isRunning = false;
    }
}