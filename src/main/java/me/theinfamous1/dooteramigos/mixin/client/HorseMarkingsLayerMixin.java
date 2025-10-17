package me.theinfamous1.dooteramigos.mixin.client;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import me.theinfamous1.dooteramigos.DooterAmigos;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HorseMarkingLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.animal.horse.Horse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HorseMarkingLayer.class)
public abstract class HorseMarkingsLayerMixin extends RenderLayer<Horse, HorseModel<Horse>> {
    public HorseMarkingsLayerMixin(RenderLayerParent<Horse, HorseModel<Horse>> renderer) {
        super(renderer);
    }

    @WrapWithCondition(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/animal/horse/Horse;FFFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/HorseModel;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;II)V"))
    private boolean wrap_renderToBuffer_render(HorseModel instance, PoseStack poseStack, VertexConsumer vertexConsumer, int i, int j, PoseStack poseStackO, MultiBufferSource buffer, int packedLight, Horse horse, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch){
        return !DooterAmigos.isPinataNamed(horse);
    }
}
