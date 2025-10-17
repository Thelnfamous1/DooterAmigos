package me.theinfamous1.dooteramigos;

import me.theinfamous1.dooteramigos.common.entity.DooterSkeleton;
import me.theinfamous1.dooteramigos.common.entity.Pinata;
import me.theinfamous1.dooteramigos.common.item.SombreroItem;
import me.theinfamous1.dooteramigos.common.registry.DAEntityTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(DooterAmigos.MODID)
public class DooterAmigos {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "dooteramigos";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Items which will all be registered under the "dooteramigos" namespace
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "dooteramigos" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final DeferredItem<SombreroItem> SOMBRERO = ITEMS.registerItem("sombrero", p -> new SombreroItem(ArmorMaterials.LEATHER, ArmorItem.Type.HELMET, p), new Item.Properties().stacksTo(1));
    public static final DeferredItem<SpawnEggItem> DOOTER_SKELETON_SPAWN_EGG = ITEMS.registerItem("dooter_skeleton_spawn_egg", p -> new DeferredSpawnEggItem(DAEntityTypes.DOOTER_SKELETON, 12698049, 4802889, new Item.Properties()));
    public static final DeferredItem<SpawnEggItem> PINATA_SPAWN_EGG = ITEMS.registerItem("pinata_spawn_egg", p -> new DeferredSpawnEggItem(DAEntityTypes.PINATA, 12623485, 15656192, new Item.Properties()));

    // Creates a creative tab with the id "dooteramigos:example_tab" for the example item
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> DOOTER_AMIGOS = CREATIVE_MODE_TABS.register("dooteramigos", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.dooteramigos")) //The language key for the title of your CreativeModeTab
            //.withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> SOMBRERO.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(SOMBRERO.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
                output.accept(DOOTER_SKELETON_SPAWN_EGG.get());
                output.accept(PINATA_SPAWN_EGG.get());
            }).build());

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public DooterAmigos(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::onRegisterEntityAttributes);

        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);
        DAEntityTypes.ENTITY_TYPES.register(modEventBus);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (DooterAmigos) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
    }

    public static ResourceLocation location(String path){
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    private void onRegisterEntityAttributes(EntityAttributeCreationEvent event){
        event.put(DAEntityTypes.DOOTER_SKELETON.get(), DooterSkeleton.createAttributes().build());
        event.put(DAEntityTypes.PINATA.get(), Pinata.createAttributes().build());
    }

    public static boolean isPinataNamed(AbstractHorse horse){
        if (horse.hasCustomName()) {
            String name = ChatFormatting.stripFormatting(horse.getName().getString());
            return "Pinata".equals(name);
        }
        return false;
    }
}
