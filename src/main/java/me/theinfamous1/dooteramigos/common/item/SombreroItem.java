package me.theinfamous1.dooteramigos.common.item;

import me.theinfamous1.dooteramigos.DooterAmigos;
import me.theinfamous1.dooteramigos.client.renderer.SombereroArmorRenderer;
import me.theinfamous1.dooteramigos.common.registry.DAMobEffects;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

public final class SombreroItem extends ArmorItem implements GeoItem {
    public static final List<MobEffectInstance> DEFAULT_BUFFS = List.of(
            new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20, 4),
            new MobEffectInstance(DAMobEffects.DAMAGE_BOOST, 20, 4));
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public SombreroItem(Holder<ArmorMaterial> armorMaterial, Type armorType, Properties properties) {
        super(armorMaterial, armorType, properties);
    }

    public static void applyBuffsToAllies(LivingEntity self, double radius, List<MobEffectInstance> buffs, BiPredicate<LivingEntity, LivingEntity> filter) {
        if (self.tickCount % 20 == 0) {
            List<LivingEntity> nearbyEntities = self.level().getEntitiesOfClass(LivingEntity.class, AABB.ofSize(self.position(), radius * 2, radius * 2, radius * 2), le -> le != self && DooterAmigos.isAlliedTo(self, le, filter));
            for(LivingEntity nearbyEntity : nearbyEntities){
                for (MobEffectInstance effectToApply : buffs) {
                    if (effectToApply.getEffect().value().isInstantenous()) {
                        effectToApply.getEffect().value().applyInstantenousEffect(self, null, nearbyEntity, effectToApply.getAmplifier(), 0.5);
                    } else {
                        nearbyEntity.addEffect(new MobEffectInstance(effectToApply), self);
                    }
                }
            }
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
        if(entity instanceof Player player && player.getItemBySlot(this.getEquipmentSlot()) == stack){
            applyBuffsToAllies(player, 16, DEFAULT_BUFFS, (self, other) -> false);
        }
    }

    // Create our armor model/renderer for Fabric and return it
    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private GeoArmorRenderer<?> renderer;

            @Override
            public <T extends LivingEntity> HumanoidModel<?> getGeoArmorRenderer(@Nullable T livingEntity, ItemStack itemStack, @Nullable EquipmentSlot equipmentSlot, @Nullable HumanoidModel<T> original) {
                if(this.renderer == null)
                    this.renderer = new SombereroArmorRenderer();

                return this.renderer;
            }
        });
    }

    // Let's add our animation controller
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}