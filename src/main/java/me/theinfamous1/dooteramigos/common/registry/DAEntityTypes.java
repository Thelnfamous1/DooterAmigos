package me.theinfamous1.dooteramigos.common.registry;

import me.theinfamous1.dooteramigos.DooterAmigos;
import me.theinfamous1.dooteramigos.common.entity.DooterSkeleton;
import me.theinfamous1.dooteramigos.common.entity.Pinata;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class DAEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, DooterAmigos.MODID);
    
    public static final DeferredHolder<EntityType<?>, EntityType<DooterSkeleton>> DOOTER_SKELETON = register(EntityType.Builder.of(DooterSkeleton::new, MobCategory.MONSTER)
            .sized(0.6F, 1.99F)
            .eyeHeight(1.74F)
            .ridingOffset(-0.7F)
            .clientTrackingRange(8),
            "dooter_skeleton");

    public static final DeferredHolder<EntityType<?>, EntityType<Pinata>> PINATA = register(EntityType.Builder.of(Pinata::new, MobCategory.CREATURE)
            .sized(1.3964844F, 1.6F)
            .eyeHeight(1.52F)
            .passengerAttachments(1.44375F)
            .clientTrackingRange(10),
            "pinata");

    private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> register(EntityType.Builder<T> entityTypeBuilder, String name) {
        return ENTITY_TYPES.register(name,
                () -> entityTypeBuilder
                        .build(DooterAmigos.location(name).toString()));
    }
}
