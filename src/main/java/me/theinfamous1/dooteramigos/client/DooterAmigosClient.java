package me.theinfamous1.dooteramigos.client;

import me.theinfamous1.dooteramigos.DooterAmigos;
import me.theinfamous1.dooteramigos.client.renderer.DooterSkeletonRenderer;
import me.theinfamous1.dooteramigos.common.registry.DAEntityTypes;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = DooterAmigos.MODID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = DooterAmigos.MODID, value = Dist.CLIENT)
public class DooterAmigosClient {
    public static final ResourceLocation PINATA_TEXTURE_LOCATION = DooterAmigos.location("textures/entity/pinata.png");

    public DooterAmigosClient(ModContainer container) {
        // Allows NeoForge to create a config screen for this mod's configs.
        // The config screen is accessed by going to the Mods screen > clicking on your mod > clicking on config.
        // Do not forget to add translations for your config options to the en_us.json file.
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
    }

    @SubscribeEvent
    static void onEntityRenderers(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(DAEntityTypes.DOOTER_SKELETON.get(), DooterSkeletonRenderer::new);
    }
}
