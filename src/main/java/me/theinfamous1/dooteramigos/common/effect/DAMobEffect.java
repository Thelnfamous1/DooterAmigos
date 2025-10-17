package me.theinfamous1.dooteramigos.common.effect;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class DAMobEffect extends MobEffect {
    public DAMobEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    public DAMobEffect(MobEffectCategory category, int color, ParticleOptions particleOptions) {
        super(category, color, particleOptions);
    }
}
