package earth.terrarium.ad_astra.entity.vehicle;

import earth.terrarium.ad_astra.registry.ModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class RocketTier2 extends Rocket {

    public RocketTier2(EntityType<?> type, Level level) {
        super(type, level, 2);
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
        return ModItems.TIER_2_ROCKET.get().getDefaultInstance();
    }
}