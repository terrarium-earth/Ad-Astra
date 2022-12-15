package earth.terrarium.ad_astra.common.entity.vehicle;

import earth.terrarium.ad_astra.common.registry.ModEntityTypes;
import earth.terrarium.ad_astra.common.registry.ModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class RocketTier2 extends Rocket {

    public RocketTier2(Level level) {
        super(ModEntityTypes.TIER_2_ROCKET.get(), level, 2);
    }

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