package com.cursan.anthony.game.sozo.tools;

/**
 * Created by cursan_a on 01/05/15.
 */
public class GameData {
    private static GameData instance = new GameData();
    private int level = 0;
    private GameData() {

    }

    public static GameData getInstance() {
        return GameData.instance;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
