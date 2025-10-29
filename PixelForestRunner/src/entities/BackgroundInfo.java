package entities; // หรือ package ที่คุณใช้เก็บ CharacterInfo

public class BackgroundInfo {
    
    // ID สำหรับด่าน
    public static final int STAGE_FOREST_OF_DAWN = 1;
    public static final int STAGE_FOREST_OF_DUSK = 2;

    private final int id;
    private final String name;         // ชื่อด่าน (สำหรับแสดงผล)
    private final String assetFileName; // ชื่อไฟล์ Asset พื้นหลังจริง

    public BackgroundInfo(int id, String name, String assetFileName) {
        this.id = id;
        this.name = name;
        this.assetFileName = assetFileName;
    }

    // Getter Methods
    public int getId() { return id; }
    public String getName() { return name; }
    public String getAssetFileName() { return assetFileName; }

    // เมธอดสำหรับโหลดข้อมูลด่านทั้งหมด
    public static BackgroundInfo[] loadAllBackgrounds() {
        return new BackgroundInfo[] {
            // ด่าน 1: The Forest of Dawn
            new BackgroundInfo(STAGE_FOREST_OF_DAWN, "The Forest of Dawn", "bg1.png"), 
            // ด่าน 2: The Forest of Dusk
            new BackgroundInfo(STAGE_FOREST_OF_DUSK, "The Forest of Dusk", "bg2.png")
        };
    }
    
    // เมธอดค้นหาตาม ID (เผื่อใช้ใน GamePanel)
    public static BackgroundInfo getBackgroundById(int id) {
        for (BackgroundInfo info : loadAllBackgrounds()) {
            if (info.getId() == id) {
                return info;
            }
        }
        return loadAllBackgrounds()[0]; // คืนค่าด่านแรกเป็นค่าเริ่มต้น
    }
}