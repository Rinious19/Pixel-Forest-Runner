import core.GameFrame;
import core.GamePanel;

//? Main Class → ทำหน้าที่แค่ start โปรแกรม
public class Main {
    public static void main(String[] args) {
        GamePanel gamePanel = new GamePanel();
        new GameFrame(gamePanel);

        //* เริ่ม game loop จากที่นี่
        gamePanel.startGameThread();
    }
}