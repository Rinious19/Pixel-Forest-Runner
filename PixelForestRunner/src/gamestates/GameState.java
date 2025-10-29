package gamestates;

public enum GameState {
    MENU, 
    CHARACTER_SELECTION, //* เพิ่ม State เลือกตัวละคร
    LEVEL_SELECTION,     //* เพิ่ม State เลือกด่าน (เตรียมไว้)
    PLAYING, 
    PAUSED, 
    GAME_OVER;

    // ... (เมธอด getGameState เดิม) ...
    
}