package xyz.tuxinal.ffl_lock_indicator;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "ffl-lock-indicator")
public class ModConfig implements ConfigData {
    public boolean enabled = true;
    public boolean onlyWhenLocked = false;
    public int xOffset = 0;
    public int yOffset = 0;
}
