package me.theinfamous1.dooteramigos;

import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.IntValue PINATA_SPAWN_CHANCE = BUILDER
            .comment("Chance to spawn a pinata, in terms of once every X ticks")
            .defineInRange("magicNumber", 100000, 0, Integer.MAX_VALUE);
    static final ModConfigSpec SPEC = BUILDER.build();
}
