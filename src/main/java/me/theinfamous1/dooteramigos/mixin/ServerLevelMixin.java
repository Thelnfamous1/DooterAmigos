package me.theinfamous1.dooteramigos.mixin;

import me.theinfamous1.dooteramigos.DooterAmigos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerLevel.class)
public class ServerLevelMixin {

    @Inject(method = "tickChunk", at = @At("TAIL"))
    private void spawnPinata_tickChunk(LevelChunk chunk, int randomTickSpeed, CallbackInfo ci){
        DooterAmigos.spawnPinata((ServerLevel) (Object)this, chunk);
    }
}
