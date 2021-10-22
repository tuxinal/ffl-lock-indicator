package xyz.tuxinal.ffl_lock_indicator;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "ffl-lock-indicator")
public class ModConfig implements ConfigData {
    public boolean enabled = true;
    public boolean onlyWhenLocked = false;
    @Comment("Changing this will make it so it does not react to your mainhand/offhand status")
    public int xOffset = 0;
    @Comment("Changing this will make it so it does not react to your mainhand/offhand status")
    public int yOffset = 0;
}
