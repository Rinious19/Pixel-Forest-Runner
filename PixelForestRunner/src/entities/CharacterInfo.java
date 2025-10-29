package entities;

public class CharacterInfo {
    public static final int CH1_ID = 1;
    public static final int CH2_ID = 2;
    public static final int CH3_ID = 3;

    private final int id; 
    private final String name;
    private final String runAsset; //* เก็บ    
    private final String jumpAsset;

    public CharacterInfo(int id, String name, String run, String jump) {
        this.id = id;
        this.name = name;
        this.runAsset = run;
        this.jumpAsset = jump;
    }

    // ✅ Getter methods ที่ถูกเรียกใช้
    public int getId() { return id; }
    public String getName() { return name; }
    public String getRunAsset() { return runAsset; } 
    public String getJumpAsset() { return jumpAsset; }

    public static CharacterInfo[] loadAllCharacters() {
        // 💡 ส่งแค่ชื่อไฟล์เท่านั้น
        return new CharacterInfo[] {
            new CharacterInfo(CH1_ID, "Neo", 
                              "ch1_run.png", "ch1_jump.png"), 
            new CharacterInfo(CH2_ID, "Kenji", 
                              "ch2_run.png", "ch2_jump.png"), 
            new CharacterInfo(CH3_ID, "Boss", 
                              "ch3_run.png", "ch3_jump.png")  
        };
    }
}