package me.theinfamous1.dooteramigos.common.entity.ai;

import javax.annotation.Nullable;

import me.theinfamous1.dooteramigos.DooterAmigos;
import me.theinfamous1.dooteramigos.common.entity.DooterSkeleton;
import me.theinfamous1.dooteramigos.common.entity.Pinata;
import me.theinfamous1.dooteramigos.common.registry.DAEntityTypes;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.item.enchantment.providers.VanillaEnchantmentProviders;

public class PinataTrapGoal extends Goal {
    private final Pinata horse;

    public PinataTrapGoal(Pinata horse) {
        this.horse = horse;
    }

    @Override
    public boolean canUse() {
        return this.horse.level().hasNearbyAlivePlayer(this.horse.getX(), this.horse.getY(), this.horse.getZ(), 10.0);
    }

    @Override
    public void tick() {
        ServerLevel serverlevel = (ServerLevel)this.horse.level();
        serverlevel.getServer().tell(new TickTask(serverlevel.getServer().getTickCount(), () -> {
            if (this.horse.isAlive()) {
                DifficultyInstance localDifficulty = serverlevel.getCurrentDifficultyAt(this.horse.blockPosition());
                this.horse.setTrap(false);
                //this.horse.setTamed(true);
                //this.horse.setAge(0);
                this.horse.discard();
                //Explosion explode = serverlevel.explode(this.horse, Explosion.getDefaultDamageSource(serverlevel, this.horse), null, this.horse.getX(), this.horse.getY(), this.horse.getZ(), 3, false, Level.ExplosionInteraction.NONE);
                serverlevel.playSound(null, this.horse.getX(), this.horse.getY(), this.horse.getZ(), SoundEvents.GENERIC_EXPLODE, this.horse.getSoundSource(), 1.0F, 1.0F);
                serverlevel.sendParticles(ParticleTypes.EXPLOSION_EMITTER, this.horse.getX(), this.horse.getY(), this.horse.getZ(), 1, 0, 0, 0, 0);
                serverlevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, this.horse.getX(), this.horse.getY(), this.horse.getZ(), 60, 1.0, 1.0, 1.0, 0.1);
                serverlevel.sendParticles(ParticleTypes.CLOUD, this.horse.getX(), this.horse.getY(), this.horse.getZ(), 10, 0.5, 0.5, 0.5, 0.05);
                for (int i = 0; i < 3; ++i) {
                    DooterSkeleton dooterSkeleton = this.createDooterSkeleton(localDifficulty);
                    dooterSkeleton.push(this.horse.getRandom().triangle(0.0, 1.1485), 0.0, this.horse.getRandom().triangle(0.0, 1.1485));
                    serverlevel.addFreshEntityWithPassengers(dooterSkeleton);
                    Skeleton sombereroSkeleton = this.createSombreroSkeleton(localDifficulty, this.horse);
                    sombereroSkeleton.push(this.horse.getRandom().triangle(0.0, 1.1485), 0.0, this.horse.getRandom().triangle(0.0, 1.1485));
                    serverlevel.addFreshEntityWithPassengers(sombereroSkeleton);
                }

            }
        }));
    }

    @Nullable
    private DooterSkeleton createDooterSkeleton(DifficultyInstance difficulty) {
        DooterSkeleton dooterSkeleton = DAEntityTypes.DOOTER_SKELETON.get().create(this.horse.level());
        if (dooterSkeleton != null) {
            dooterSkeleton.finalizeSpawn((ServerLevel)this.horse.level(), difficulty, MobSpawnType.TRIGGERED, null);
            dooterSkeleton.setPos(this.horse.getX(), this.horse.getY(), this.horse.getZ());
            dooterSkeleton.invulnerableTime = 60;
            dooterSkeleton.setPersistenceRequired();
            //dooterSkeleton.setTamed(true);
            //dooterSkeleton.setAge(0);
        }

        return dooterSkeleton;
    }

    @Nullable
    private Skeleton createSombreroSkeleton(DifficultyInstance difficulty, AbstractHorse horse) {
        Skeleton skeleton = EntityType.SKELETON.create(horse.level());
        if (skeleton != null) {
            skeleton.finalizeSpawn((ServerLevel)horse.level(), difficulty, MobSpawnType.TRIGGERED, null);
            skeleton.setPos(horse.getX(), horse.getY(), horse.getZ());
            skeleton.invulnerableTime = 60;
            skeleton.setPersistenceRequired();
            if (skeleton.getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
                skeleton.setItemSlot(EquipmentSlot.HEAD, new ItemStack(DooterAmigos.SOMBRERO.get()));
            }

            this.enchant(skeleton, EquipmentSlot.MAINHAND, difficulty);
            //this.enchant(skeleton, EquipmentSlot.HEAD, difficulty);
        }

        return skeleton;
    }

    private void enchant(Skeleton skeleton, EquipmentSlot slot, DifficultyInstance difficulty) {
        ItemStack itemstack = skeleton.getItemBySlot(slot);
        itemstack.set(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
        EnchantmentHelper.enchantItemFromProvider(itemstack, skeleton.level().registryAccess(), VanillaEnchantmentProviders.MOB_SPAWN_EQUIPMENT, difficulty, skeleton.getRandom());
        skeleton.setItemSlot(slot, itemstack);
    }
}