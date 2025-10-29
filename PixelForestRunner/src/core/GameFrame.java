package core;

import javax.swing.JFrame;

public class GameFrame extends JFrame {
    //? Constructor ของ GameFrame
    public GameFrame(GamePanel gamePanel) {
        //@ ตั้งค่าพื้นฐานของหน้าต่างโปรแกรม (JFrame)
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //* ตั้งค่าให้โปรแกรมปิดเมื่อกดปุ่ม X
        this.setResizable(false); //* ไม่ให้ผู้เล่นปรับขนาดหน้าต่างเอง
        this.setTitle("Pixel Forest Runner"); //* ตั้งชื่อบน Title Bar

        //@ เพิ่ม GamePanel เข้าไปใน Frame
        this.add(gamePanel);

        //! คำสั่งสำคัญ! ให้ Frame ปรับขนาดตัวเองให้พอดีกับ Component ข้างใน (ก็คือ GamePanel)
        this.pack();

        //* ทำให้หน้าต่างแสดงผลอยู่กลางจอ
        this.setLocationRelativeTo(null);
        //* แสดงหน้าต่าง
        this.setVisible(true);
    }
}