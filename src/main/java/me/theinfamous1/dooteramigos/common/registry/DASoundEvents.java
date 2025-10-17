package me.theinfamous1.dooteramigos.common.registry;

import me.theinfamous1.dooteramigos.DooterAmigos;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class DASoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, DooterAmigos.MODID);

    public static final DeferredHolder<SoundEvent, SoundEvent> DOOTER_SKELETON_TRUMPET = register("entity.dooter_skeleton.trumpet");

    private static DeferredHolder<SoundEvent, SoundEvent> register(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(DooterAmigos.location(name)));
    }
}
