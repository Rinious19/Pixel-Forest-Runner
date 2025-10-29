#Development Plan

"""
- แผนการพัฒนาโปรเจกต์: Pixel Forest Runner
? 1. ตั้งค่าโปรเจกต์และสร้างโครงสร้างสถาปัตยกรรม (Project Setup & Architecture)
@ 1.1 สร้างโปรเจกต์และโฟลเดอร์:
- เริ่มต้นโปรเจกต์ Java ใน IDE และสร้างโครงสร้างโฟลเดอร์เพื่อ แยกหน้าที่ (Separation of Concerns)
  ● entities/: สำหรับคลาสวัตถุในเกม (Player, Obstacles)
  ● managers/: สำหรับคลาสผู้จัดการ (EntityManager, ObstacleManager)
  ● gamestates/: สำหรับคลาสสถานะของเกม (MenuState, PlayingState)
  ● game/: สำหรับคลาสควบคุมหลักของเกม (GamePanel, GameFrame)
@ 1.2 สร้างคลาสโครงสร้างหลัก:
  ● Main.java: ไฟล์เริ่มต้นโปรแกรม
  ● GameFrame.java: คลาส JFrame สำหรับสร้างหน้าต่างเกม
  ● GamePanel.java: คลาส JPanel ที่ทำหน้าที่เป็น Runnable สำหรับ Game Loop และเป็น "ผืนผ้าใบ" 
    ที่จะมอบหมายหน้าที่การวาดให้กับ State ปัจจุบัน
? 2. ออกแบบและสร้างคลาสหลักตามหลัก OOP (Core OOP Classes Design)
- วางโครงสร้างคลาสตามนี้
@ 2.1 GameObject (Abstract Class):
  ● หน้าที่: เป็นคลาสแม่แบบสำหรับทุกสิ่งในเกมที่ขยับหรือวาดได้
  ● คุณสมบัติ: protected int x, y, width, height;
  ● เมธอด: public abstract void update();, public abstract void draw(Graphics g);
  ● หลักการ OOP ที่ใช้: Abstraction (กำหนดโครงสร้างว่าทุก Object ต้องมี update และ draw 
    แต่ยังไม่บอกว่าทำอย่างไร), Entity-Based Architecture
@ 2.2 Player (extends GameObject):
  ● หน้าที่: จัดการข้อมูลและสถานะของผู้เล่น
  ● คุณสมบัติ: private int hp;, private PlayerState currentState; (ใช้ Enum แทน boolean)
  ● เมธอด: jump(), duck(), takeDamage(), และ update() ที่จะทำงานตาม currentState 
    (State Machine Pattern), draw() #* duck = หลบ, ก้มหลบ
  ● หลักการ OOP ที่ใช้: Inheritance, Encapsulation (ซ่อน hp ไว้เป็น private และให้เข้าถึงผ่านเมธอด)      
    State Machine Pattern
@ 2.3 Obstacle (extends GameObject):
  ● หน้าที่: เป็นคลาสแม่แบบของสิ่งกีดขวาง
  ● เมธอด: Override update() เพื่อให้สิ่งกีดขวางเลื่อนจากขวามาซ้าย และ draw() เพื่อวาดตัวเอง
  ● หลักการ OOP ที่ใช้: Inheritance
  * คลาสย่อยของสิ่งกีดขวาง:
    ● Rock.java (extends Obstacle)
    ● Tree.java (extends Obstacle)
    ● หลักการ OOP ที่ใช้: Polymorphism (สามารถเก็บทั้ง Rock และ Tree ไว้ใน ArrayList<Obstacle>
      เดียวกัน และเมื่อเรียกเมธอด draw() มันจะเรียกใช้เมธอดของคลาสลูกที่ถูกต้องโดยอัตโนมัติ)
@ 2.4 Manager Classes (Controller Pattern):
  ● EntityManager.java: ทำหน้าที่ เก็บและจัดการ Entity ทั้งหมด (มี Player และ List<Obstacle>) 
    มีเมธอด update() และ draw() ของตัวเอง
  ● ObstacleManager.java: ทำหน้าที่รับผิดชอบ Logic การสร้าง สิ่งกีดขวางตามเงื่อนไข (เช่น เวลา, คะแนน)
@ 2.5 State Classes (State Machine Pattern):
  ● GameState.java (Enum): กำหนดสถานะ MENU, PLAYING, GAME_OVER
  ● State.java (Abstract Class): แม่แบบของ State มีเมธอด update() และ draw()
  ● PlayingState.java, MenuState.java (extends State): คลาสที่จัดการ Logic และการวาดของแต่ละ
    สถานะเกมโดยเฉพาะ
? 3. การวาดภาพและอนิเมชัน (Graphics & Animation)
  ● โหลด Sprites: สร้างคลาส AssetManager (อาจจะเพิ่มในภายหลัง) เพื่อจัดการโหลดและจัดเก็บรูปภาพ
    ทั้งหมดในที่เดียว
  ● วาดพื้นหลัง: Logic การวาดพื้นหลังแบบเลื่อนจะอยู่ในเมธอด draw() ของ PlayingState
  ● ทำอนิเมชัน: ในเมธอด Player.draw() จะเลือกแสดง Sprite ที่ถูกต้องโดยอ้างอิงจาก currentState ของ 
    Player (เช่น PlayerState.JUMPING)
? 4. Game Loop และการใช้ Thread (The Game Loop)
- ส่วนนี้ตรงตาม Requirement ที่ต้องใช้ 3 Thread พอดี
@ Thread ที่ 1: Game Loop หลัก (Runnable)
  ● หน้าที่: เป็นหัวใจของเกม ทำงานวนลูปตลอดเวลาเพื่ออัปเดตสถานะและวาดภาพใหม่
  ● Logic: ภายใน run() จะเรียกใช้เมธอดของ State ปัจจุบัน เท่านั้น เช่น currentState.update() และ 
    currentState.draw() ซึ่งเป็นการแยก Logic ออกจาก GamePanel อย่างสมบูรณ์
@ Thread ที่ 2: ตัวควบคุมเวลาและคะแนน (Runnable)
  ● หน้าที่: นับเวลาและเพิ่มคะแนนตามระยะทาง
  ● Logic: ให้ Thread.sleep(1000) เพื่อให้ทำงานทุกๆ 1 วินาที แล้วเพิ่มค่าคะแนน (Score) ขึ้น
           ถูกจัดการและสั่งเริ่ม/หยุดโดย PlayingState
@ Thread ที่ 3: การอัปเดตการเคลื่อนไหวของตัวละคร/ศัตรู (Runnable)
  ● หน้าที่: จัดการ Logic การเคลื่อนไหวที่ซับซ้อน เช่น การเปลี่ยนเฟรมอนิเมชัน หรือการเคลื่อนไหวของศัตรูบางชนิด
  ● Logic: Logic การกระโดด (แรงโน้มถ่วง) จะถูกจัดการภายใน Player.update() เมื่อสถานะเป็น 
           PlayerState.JUMPING
? 5. การควบคุมผู้เล่น (Event Handling)
* 1. Implement KeyListener: ใน GamePanel ให้ implements KeyListener
* 2. รับ Input และส่งต่อ: GamePanel จะทำหน้าที่แค่ รับ Event จากคีย์บอร์ด แล้ว ส่งต่อไปให้ State 
     ปัจจุบัน จัดการ (currentState.handleInput(keyCode)) จากนั้น State จะส่งต่อไปยัง Player อีกทีหนึ่ง
* 3. เชื่อมต่อกับ Player:
     ● เมื่อกด Space Bar: เรียกเมธอด player.jump()
     ● เมื่อกด Arrow Down: ตั้งค่าสถานะ player.setIsDucking(true)
     ● เมื่อปล่อย Arrow Down: ตั้งค่า player.setIsDucking(false)
     ● หลักการ OOP ที่ใช้: Event-Driven Programming
? 6. สร้างระบบ UI (Menus, HUD)
- แยกการวาดตาม State:
  ● MenuState.java: รับผิดชอบการวาดหน้าจอหลัก, ชื่อเกม, และปุ่ม "Start" ทั้งหมด
  ● PlayingState.java: รับผิดชอบการวาด HUD (แถบ HP, คะแนน) ทับลงบนฉากเกม
  ● GameOverState.java: รับผิดชอบการวาดหน้าจอ Game Over
- สร้างปุ่ม: Logic การตรวจจับการคลิกปุ่มจะอยู่ใน State ที่เกี่ยวข้อง (เช่น MenuState จัดการปุ่ม Start)
? 7. การจัดการด่านและตัวละคร
● ระบบด่าน: Logic การเพิ่มความยาก (เพิ่มความเร็ว/จำนวนสิ่งกีดขวาง) จะถูกจัดการทั้งหมดภายใน 
  ObstacleManager
● ระบบตัวละคร: Logic การเลือกตัวละครจะอยู่ใน MenuState และเมื่อเกมเริ่ม MenuState 
  จะส่งข้อมูลตัวละครที่เลือกไปให้ PlayingState เพื่อสร้าง Player ที่ถูกต้อง

? Concept OOP in Game
1. Encapsulation: ข้อมูลสำคัญของคลาส Player เช่น hp จะเป็น private และเข้าถึงผ่านเมธอด 
   takeDamage() หรือ getHP() เท่านั้น
2. Inheritance: คลาส Player, Obstacle, Rock, Tree ล้วนสืบทอดคุณสมบัติมาจาก GameObject
3. Polymorphism: สามารถมี ArrayList<GameObject> ที่เก็บทั้ง Player และ Obstacle ได้ 
   และเมื่อคุณเรียก object.draw() ในลูป Java จะรู้เองว่าต้องเรียกใช้เมธอด draw() ของคลาสไหน
4. Abstraction: คลาส GameObject เป็นคลาสนามธรรมที่บังคับให้คลาสลูกต้องมีเมธอด update() และ draw()
   เป็นของตัวเอง
5. Manager/Controller Pattern: ใช้ EntityManager และ ObstacleManager เพื่อแยก Logic การ
   จัดการออกจาก GamePanel ทำให้โค้ดสะอาดและเป็นสัดส่วน
6. State Machine Pattern: ใช้จัดการ "สถานะ" ของเกม (PlayingState) และของตัวละคร (PlayerState)
   เพื่อลดเงื่อนไข if-else ที่ซับซ้อน ทำให้โค้ดเข้าใจง่ายและยืดหยุ่น
"""