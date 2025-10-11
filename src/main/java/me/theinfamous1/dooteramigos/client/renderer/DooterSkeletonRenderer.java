package me.theinfamous1.dooteramigos.client.renderer;

import me.theinfamous1.dooteramigos.client.model.DooterSkeletonModel;
import me.theinfamous1.dooteramigos.common.entity.DooterSkeleton;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class DooterSkeletonRenderer extends GeoEntityRenderer<DooterSkeleton> {
    public DooterSkeletonRenderer(EntityRendererProvider.Context context) {
        super(context, new DooterSkeletonModel());
    }
}
