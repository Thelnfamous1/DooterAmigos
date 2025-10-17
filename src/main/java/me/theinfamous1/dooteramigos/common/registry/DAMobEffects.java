package me.theinfamous1.dooteramigos.common.registry;

import me.theinfamous1.dooteramigos.DooterAmigos;
import me.theinfamous1.dooteramigos.common.effect.DAMobEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class DAMobEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, DooterAmigos.MODID);

    public static final double DAMAGE_BOOST_MULITPLIER_BASE = 0.2;
    public static final DeferredHolder<MobEffect, MobEffect> DAMAGE_BOOST = MOB_EFFECTS.register("damage_boost", () -> (new DAMobEffect(MobEffectCategory.BENEFICIAL, 16762624))
            .addAttributeModifier(Attributes.ATTACK_DAMAGE,
                    DooterAmigos.location("effect.damage_boost"),
                    DAMAGE_BOOST_MULITPLIER_BASE,
                    AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
}
