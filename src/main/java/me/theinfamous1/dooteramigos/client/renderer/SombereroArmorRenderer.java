package me.theinfamous1.dooteramigos.client.renderer;

import me.theinfamous1.dooteramigos.client.model.SomberoModel;
import me.theinfamous1.dooteramigos.common.item.SombreroItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public final class SombereroArmorRenderer extends GeoArmorRenderer<SombreroItem> {
    public SombereroArmorRenderer() {
        super(new SomberoModel());
    }
}