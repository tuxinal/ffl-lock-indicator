package xyz.tuxinal.ffl_lock_indicator.mixins;

import com.mojang.blaze3d.systems.RenderSystem;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.shedaniel.autoconfig.AutoConfig;
import net.adriantodt.fallflyinglib.impl.FallFlyingPlayerEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import xyz.tuxinal.ffl_lock_indicator.ModConfig;

@Mixin(InGameHud.class)
@Environment(EnvType.CLIENT)
public class InGameHudMixin extends DrawableHelper {
    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerInteractionManager;getCurrentGameMode()Lnet/minecraft/world/GameMode;", ordinal = 0))
    private void renderOnHud(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
        if (!config.enabled) {
            return;
        }
        if (!(client.player instanceof FallFlyingPlayerEntity)) {
            return;
        }
        FallFlyingPlayerEntity fflPlayer = (FallFlyingPlayerEntity) client.player;
        if (!fflPlayer.ffl_isFallFlyingAbilityEnabled()) {
            return;
        }
        boolean locked = fflPlayer.ffl_getFallFlyingLock();
        if (!locked && config.onlyWhenLocked) {
            return;
        }
        Identifier texture = locked ? new Identifier("ffl-lock-indicator", "textures/gui/locked.png")
                : new Identifier("ffl-lock-indicator", "textures/gui/unlocked.png");
        PlayerEntity player = getCameryEntity();
        int x;
        int y;
        if (player.getMainArm() == Arm.LEFT) {
            // really strange people
            x = client.getWindow().getScaledWidth() / 2 + 105 - 8 + config.xOffset;
            y = client.getWindow().getScaledHeight() - 13 + config.yOffset;
        } else {
            x = client.getWindow().getScaledWidth() / 2 - 105 + config.xOffset;
            y = client.getWindow().getScaledHeight() - 13 + config.yOffset;
        }
        ItemStack offhand = player.getOffHandStack();
        if (!offhand.isEmpty()) {
            if (player.getMainArm() == Arm.LEFT) {
                x += 29;
            } else {
                x -= 29;
            }
        }
        RenderSystem.setShaderTexture(0, texture);
        drawTexture(matrices, x, y, 0, 0, 8, 8, 8, 8);
    }

    private PlayerEntity getCameryEntity() {
        return !(client.getCameraEntity() instanceof PlayerEntity) ? null : (PlayerEntity) client.getCameraEntity();
    }
}
