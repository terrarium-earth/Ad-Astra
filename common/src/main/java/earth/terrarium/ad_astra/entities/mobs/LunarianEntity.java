package earth.terrarium.ad_astra.entities.mobs;

import earth.terrarium.ad_astra.entities.LunarianTradeOffers;
import earth.terrarium.ad_astra.registry.ModEntityTypes;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
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
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;

public class LunarianEntity extends Villager {

    public LunarianEntity(EntityType<? extends Villager> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.5).add(Attributes.MAX_HEALTH, 20).add(Attributes.FOLLOW_RANGE, 48);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, CorruptedLunarianEntity.class, 15.0f, 0.5f, 0.5f));
    }

    @Override
    public Villager getBreedOffspring(ServerLevel serverWorld, AgeableMob passiveEntity) {
        LunarianEntity entity = new LunarianEntity(ModEntityTypes.LUNARIAN.get(), serverWorld);
        entity.finalizeSpawn(serverWorld, serverWorld.getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.BREEDING, null, null);
        return entity;
    }

    // Custom trade offers
    @Override
    protected void updateTrades() {
        VillagerData villagerData = this.getVillagerData();
        Int2ObjectMap<VillagerTrades.ItemListing[]> int2ObjectMap = LunarianTradeOffers.PROFESSION_TO_LEVELED_TRADE.get(villagerData.getProfession());
        if (int2ObjectMap == null || int2ObjectMap.isEmpty()) {
            return;
        }
        VillagerTrades.ItemListing[] factorys = int2ObjectMap.get(villagerData.getLevel());
        if (factorys == null) {
            return;
        }
        MerchantOffers tradeOfferList = this.getOffers();
        this.addOffersFromItemListings(tradeOfferList, factorys, 2);
    }
}
