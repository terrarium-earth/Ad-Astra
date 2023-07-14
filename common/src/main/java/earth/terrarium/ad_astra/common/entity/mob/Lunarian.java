package earth.terrarium.ad_astra.common.entity.mob;

import earth.terrarium.ad_astra.common.entity.LunarianMerchantOffer;
import earth.terrarium.ad_astra.common.registry.ModEntityTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;

public class Lunarian extends Villager {

    public Lunarian(EntityType<? extends Villager> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.5).add(Attributes.MAX_HEALTH, 20).add(Attributes.FOLLOW_RANGE, 48);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, CorruptedLunarian.class, 15.0f, 0.5f, 0.5f));
    }

    @Override
    public Villager getBreedOffspring(ServerLevel serverWorld, AgeableMob passiveEntity) {
        Lunarian entity = new Lunarian(ModEntityTypes.LUNARIAN.get(), serverWorld);
        entity.finalizeSpawn(serverWorld, serverWorld.getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.BREEDING, null, null);
        return entity;
    }

    // Custom trade offers
    @Override
    protected void updateTrades() {
        VillagerData villagerData = this.getVillagerData();
        ItemListing[] factorys = LunarianMerchantOffer.getProfessionItemListings(this.getLevel().getRecipeManager(), villagerData.getProfession(), villagerData.getLevel());
        MerchantOffers tradeOfferList = this.getOffers();
        this.addOffersFromItemListings(tradeOfferList, factorys, 2);
    }
}
