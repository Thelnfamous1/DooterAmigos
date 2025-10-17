package me.theinfamous1.dooteramigos.client.renderer;

import me.theinfamous1.dooteramigos.client.DooterAmigosClient;
import me.theinfamous1.dooteramigos.common.entity.Pinata;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.renderer.entity.AbstractHorseRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class PinataRenderer extends AbstractHorseRenderer<Pinata, HorseModel<Pinata>> {
    public PinataRenderer(EntityRendererProvider.Context context) {
        super(context, new HorseModel<>(context.bakeLayer(DooterAmigosClient.PINATA_LAYER_LOCATION)), 1.0F);
    }

    @Override
    public ResourceLocation getTextureLocation(Pinata entity) {
        return DooterAmigosClient.PINATA_TEXTURE_LOCATION;
    }
}