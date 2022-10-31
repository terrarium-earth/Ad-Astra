package earth.terrarium.ad_astra.entities.vehicles;

import earth.terrarium.ad_astra.registry.ModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class RocketEntityTier1 extends RocketEntity {

    public RocketEntityTier1(EntityType<?> type, Level level) {
        super(type, level, 1);
    }

    @Override
    public double getPassengersRidingOffset() {
        return super.getPassengersRidingOffset() + 1.0f;
    }

    @Override
    public boolean shouldSit() {
        return false;
    }

    @Override
    public ItemStack getDropStack() {
        return ModItems.TIER_1_ROCKET.get().getDefaultInstance();
    }
}