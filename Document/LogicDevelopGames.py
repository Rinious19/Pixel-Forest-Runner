#LogicDevelopGames

"""
- เป้าหมาย: "เขียนเกมให้กลับมาแก้น้อยที่สุด" 
  ● สิ่งนี้จะเกิดขึ้นได้ก็ต่อเมื่อวาง โครงสร้างเชิงระบบ ตั้งแต่ต้น
  ● แนวคิดสำคัญคือ “แยกทุกสิ่งออกจากกัน (Separation of Concerns)”
  ● พูดง่าย ๆ คือ "สิ่งที่ทำหน้าที่ไม่เหมือนกัน ไม่ควรเขียนปนกันในไฟล์เดียว"
? แนวคิดหลักที่ใช้จริงในวงการ 
@ 1. Game Loop (หัวใจของทุกเกม)
- ทุกเกมจะมี “ลูปหลัก” ที่ทำงานซ้ำไปเรื่อย ๆ:
    while (running) {
        update();   #* อัปเดต logic เช่น การเคลื่อนไหว การชนกัน
        render();   #* วาดภาพทั้งหมดบนหน้าจอ
    }
✅ แยก “update” กับ “render” ออกจากกันเด็ดขาด อย่าให้โค้ดวาดภาพ (graphics) ไปยุ่งกับโค้ด
    การคำนวณ logic เช่น HP หรือการชน
@ 2. Entity-Based Architecture
- ทุกสิ่งในเกม = Entity ทุก Entity มีหน้าที่เหมือน ๆ กันในเชิงโครงสร้าง
💡ตัวอย่างเช่น:
    interface Entity {
        void update();
        void render(Graphics g);
    }
- แล้ว Player, Enemy, Bullet, Item ก็ implement interface นี้
✅ ข้อดี:
    ● เพิ่มตัวละครใหม่ → แค่สร้าง class ใหม่ ไม่ต้องแก้โค้ดหลักเลย
    ● เพราะลูปเกมเรียกใช้ผ่าน interface เดียวกันอยู่แล้ว
@ 3. Manager / Controller Pattern
- ใช้ “ตัวกลาง” จัดการสิ่งต่าง ๆ แทนที่จะยัดทุกอย่างไว้ใน main class
💡ตัวอย่างเช่น:
    ● EntityManager = เก็บรายการ Entity ทั้งหมดในฉาก (player, enemy, bullet ฯลฯ)
    ● InputManager = จัดการคีย์บอร์ด/เมาส์
    ● SceneManager = สลับด่าน, เปลี่ยนฉาก, โหลดทรัพยากร
    ● AudioManager = เล่นเสียง, หยุดเสียง
    ● CollisionManager = ตรวจการชน
✅ ข้อดี: เวลาแก้ฟีเจอร์ใด ๆ → แก้แค่ Manager เดียว ไม่ต้องแตะส่วนอื่นเลย
@ 4. Component-Based Design (แนวคิดสมัยใหม่)
- แยก “ข้อมูล” กับ “พฤติกรรม” ของ Entity ออกจากกัน
- เช่น Player มีส่วนประกอบ (component) แบบนี้:
    ● TransformComponent (ตำแหน่ง, ความเร็ว)
    ● SpriteComponent (รูปภาพ)
    ● PhysicsComponent (แรงโน้มถ่วง, การชน)
    ● HealthComponent (HP)
    ● InputComponent (การควบคุม)
- เวลาจะเพิ่มฟีเจอร์ใหม่ (เช่นศัตรูใหม่)
→ แค่ประกอบ component ที่ต้องใช้เข้าด้วยกัน ไม่ต้องเขียน class ยาว ๆ อีก
@ 5. Data-Driven Design
- เก็บค่าต่าง ๆ ไว้ในไฟล์ภายนอก (JSON, XML, หรือ properties) ไม่ฝังค่าคงที่ไว้ในโค้ด
- เช่น:
    {
      "player": {
        "speed": 5,
        "jumpPower": 10,
        "maxHealth": 100
      }
    }
- เวลาอยากปรับ balance เกม → แก้ไฟล์ data ไม่ต้องแก้โค้ดเลย เหมาะมากสำหรับเกมที่ต้อง test หลายรอบ
@ 6. State Machine Pattern
- แยก “สถานะของเกม” และ “สถานะของตัวละคร” ออกจากกันชัดเจน
💡ตัวอย่างสถานะเกม (Game State): Menu, Playing, Paused, GameOver
💡ตัวอย่างสถานะของตัวละคร (Character State): Idle, Run, Jump, Attack, Dead
- แต่ละ state จะมี logic ของตัวเอง เช่น:
    if (state == PlayerState.JUMP) {
        velocityY -= gravity;
    }
✅ ข้อดี: เพิ่ม animation หรือ behavior ใหม่ได้โดยไม่แตะ logic หลัก
@ 7. Event / Observer Pattern
- ถ้าเกมเริ่มมีระบบเยอะ เช่น ศัตรูตาย → ต้องให้ระบบเสียง, คะแนน, และ UI รู้
- ใช้ระบบ Event ช่วย
    EventManager.emit("enemyDead");
- จากนั้นส่วนอื่นจะ subscribe:
    EventManager.on("enemyDead", () -> {
        score += 100;
        sound.play("enemy_death");
    });
✅ ไม่ต้องให้ class หนึ่งไปรู้จักอีก class โดยตรง → ลดการเชื่อมโยง
@ 8. แยก Logic ออกจาก Rendering อย่างชัดเจน
- อย่าผสม “สิ่งที่เกมคิด” กับ “สิ่งที่เกมแสดงผล”
- เช่น:
    player.updatePhysics();  #* logic
    player.draw(g);          #* graphics
- นี่คือหลักพื้นฐานของ Model-View-Controller(MVC) และมันช่วยให้กลับมาแก้ง่ายสุด ๆ เลย

? สรุป Concept ใหญ่ที่ใช้ได้จริง
● Game Loop	= ควบคุมการอัปเดตและการวาด → ทำให้ เกมทำงานต่อเนื่อง
● Entity System	= ทุกสิ่งในเกมอยู่ในโครงเดียวกัน → ทำให้ เพิ่ม/ลบง่าย
● Manager Pattern = แยกหน้าที่แต่ละระบบ → ทำให้ แก้ง่าย ไม่ชนกัน
● State Machine	= แยกพฤติกรรมตามสถานะ → ทำให้ ปรับ logic ใหม่ไม่กระทบเดิม
● Data Driven = ค่ามาจากไฟล์นอก → ทำให้ ปรับสมดุลเร็ว
● Event System = ระบบสื่อสารแบบหลวม → ทำให้ ลดการผูก class
"""

