package me.theinfamous1.dooteramigos.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import me.theinfamous1.dooteramigos.DooterAmigos;
import me.theinfamous1.dooteramigos.client.DooterAmigosClient;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.renderer.entity.AbstractHorseRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HorseRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.horse.Horse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HorseRenderer.class)
public abstract class HorseRendererMixin extends AbstractHorseRenderer<Horse, HorseModel<Horse>> {
    public HorseRendererMixin(EntityRendererProvider.Context context, HorseModel<Horse> model, float scale) {
        super(context, model, scale);
    }

    @ModifyReturnValue(method = "getTextureLocation(Lnet/minecraft/world/entity/animal/horse/Horse;)Lnet/minecraft/resources/ResourceLocation;", at = @At("RETURN"))
    private ResourceLocation modify_getTextureLocation(ResourceLocation original, Horse horse){
        if(DooterAmigos.isPinataNamed(horse)){
            return DooterAmigosClient.PINATA_TEXTURE_LOCATION;
        }
        return original;
    }
}
