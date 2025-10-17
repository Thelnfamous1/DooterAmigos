package me.theinfamous1.dooteramigos.common.entity;

import com.google.common.base.Suppliers;
import me.theinfamous1.dooteramigos.common.entity.ai.PinataTrapGoal;
import me.theinfamous1.dooteramigos.common.registry.DAEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Supplier;

public class Pinata extends AbstractHorse {
    private final PinataTrapGoal pinataTrapGoal = new PinataTrapGoal(this);
    private static final int TRAP_MAX_LIFE = 18000;
    private static final Supplier<EntityDimensions> BABY_DIMENSIONS = Suppliers.memoize(() -> DAEntityTypes.PINATA.get().getDimensions()
            .withAttachments(EntityAttachments.builder()
                    .attach(EntityAttachment.PASSENGER, 0.0F, DAEntityTypes.PINATA.get().getHeight() + 0.125F, 0.0F))
            .scale(0.5F));
    private boolean isTrap;
    private int trapTime;

    public Pinata(EntityType<? extends Pinata> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return AbstractHorse.createBaseHorseAttributes().add(Attributes.MAX_HEALTH, 15.0).add(Attributes.MOVEMENT_SPEED, 0.20000000298023224);
    }

    public static boolean checkPinataSpawnRules(EntityType<Pinata> animal, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return !MobSpawnType.isSpawner(spawnType) ? Animal.checkAnimalSpawnRules(animal, level, spawnType, pos, random) : MobSpawnType.ignoresLightRequirements(spawnType) || isBrightEnoughToSpawn(level, pos);
    }

    @Override
    protected void randomizeAttributes(RandomSource random) {
        AttributeInstance jumpStrength = this.getAttribute(Attributes.JUMP_STRENGTH);
        Objects.requireNonNull(random);
        jumpStrength.setBaseValue(generateJumpStrength(random::nextDouble));
    }

    @Override
    protected void addBehaviourGoals() {
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.HORSE_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.HORSE_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.HORSE_HURT;
    }

    @Override
    public EntityDimensions getDefaultDimensions(Pose pose) {
        return this.isBaby() ? BABY_DIMENSIONS.get() : super.getDefaultDimensions(pose);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.isTrap() && this.trapTime++ >= TRAP_MAX_LIFE) {
            this.discard();
        }

    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("PinataTrap", this.isTrap());
        compound.putInt("PinataTrapTime", this.trapTime);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setTrap(compound.getBoolean("PinataTrap"));
        this.trapTime = compound.getInt("PinataTrapTime");
    }

    public boolean isTrap() {
        return this.isTrap;
    }

    public void setTrap(boolean isTrap) {
        if (isTrap != this.isTrap) {
            this.isTrap = isTrap;
            if (isTrap) {
                this.goalSelector.addGoal(1, this.pinataTrapGoal);
            } else {
                this.goalSelector.removeGoal(this.pinataTrapGoal);
            }
        }

    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return DAEntityTypes.PINATA.get().create(level);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        return !this.isTamed() ? InteractionResult.PASS : super.mobInteract(player, hand);
    }
}