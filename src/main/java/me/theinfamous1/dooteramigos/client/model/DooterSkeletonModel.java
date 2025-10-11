package me.theinfamous1.dooteramigos.client.model;

import me.theinfamous1.dooteramigos.common.entity.DooterSkeleton;
import me.theinfamous1.dooteramigos.common.registry.DAEntityTypes;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class DooterSkeletonModel extends DefaultedEntityGeoModel<DooterSkeleton> {
    public DooterSkeletonModel() {
        super(DAEntityTypes.DOOTER_SKELETON.getId(), "Head");
    }
}
