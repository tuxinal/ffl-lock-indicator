package xyz.tuxinal.ffl_lock_indicator;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ClientModInitializer;

public class Init implements ClientModInitializer{
  @Override
  public void onInitializeClient() {
    AutoConfig.register(ModConfig.class, Toml4jConfigSerializer::new);
  }
}
