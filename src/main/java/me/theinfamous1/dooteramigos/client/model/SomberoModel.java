package me.theinfamous1.dooteramigos.client.model;

import me.theinfamous1.dooteramigos.DooterAmigos;
import me.theinfamous1.dooteramigos.common.item.SombreroItem;
import software.bernie.geckolib.model.DefaultedItemGeoModel;

public class SomberoModel extends DefaultedItemGeoModel<SombreroItem> {
    public SomberoModel() {
        super(DooterAmigos.SOMBRERO.getId().withPrefix("armor/")); // Using DefaultedItemGeoModel like this puts our 'location' as item/armor/example armor in the assets folders.
    }
}
