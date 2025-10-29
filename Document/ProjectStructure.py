#ProjectStructure

"""
? ProjectStructure 
PixelForestRunner/
└──src/
    ├── entities/ #* โฟลเดอร์สำหรับเก็บคลาสที่เกี่ยวกับวัตถุในเกม
    │   ├── GameObject.java
    │   ├── Player.java
    │   ├── Obstacle.java
    │   ├── Rock.java
    │   ├── Bush.java
    │   ├── BackgroundInfo.java
    │   └── CharacterInfo.java
    │
    ├── core/ #* โฟลเดอร์สำหรับเก็บคลาสที่ควบคุมเกม
    │   ├── GamePanel.java
    │   └── GameFrame.java
    │
    ├── gamestates/ #* โฟลเดอร์สำหรับเก็บคลาสที่จัดการสถานะต่างๆ ของเกม     
    │   ├── GameState.java  // (Enum)
    │   ├── GameOverState.java  
    │   ├── State.java      // (Abstract Class)
    │   ├── PlayingState.java
    │   ├── SelectionState.java
    │   └── MenuState.java
    │
    ├── managers/ #* โฟลเดอร์สำหรับเก็บคลาสที่จัดการกับองค์ประกอบต่างๆ ของเกม
    │   ├── EntityManager.java
    │   └── ObstacleManager.java
    │
    ├── inputs/ #* โฟลเดอร์สำหรับเก็บคลาสที่จัดการกับการรับข้อมูลจากผู้เล่น
    │   └── KeyBoardInputs.java
    │
    ├── animation/ 
    │   ├── Animation.java
    │   └── AnimationManager.java
    │
    ├── utils/ #* โฟลเดอร์สำหรับเก็บคลาสที่เป็นประโยชน์ทั่วไป
    │   └── AssetLoader.java #* สำหรับโหลดภาพและเสียง
    │
    └── Main.java #* ไฟล์เริ่มต้นโปรแกรม

"""