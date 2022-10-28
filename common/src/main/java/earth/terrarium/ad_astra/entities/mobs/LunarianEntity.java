package earth.terrarium.ad_astra.entities.mobs;

import earth.terrarium.ad_astra.entities.LunarianTradeOffers;
import earth.terrarium.ad_astra.registry.ModEntityTypes;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.village.TradeOfferList;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillagerData;
import net.minecraft.world.World;

public class LunarianEntity extends VillagerEntity {

    public LunarianEntity(EntityType<? extends VillagerEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createMobAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.5).add(EntityAttributes.GENERIC_MAX_HEALTH, 20).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(1, new FleeEntityGoal<>(this, CorruptedLunarianEntity.class, 15.0f, 0.5f, 0.5f));
    }

    @Override
    public VillagerEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        LunarianEntity entity = new LunarianEntity(ModEntityTypes.LUNARIAN.get(), serverWorld);
        entity.initialize(serverWorld, serverWorld.getLocalDifficulty(entity.getBlockPos()), SpawnReason.BREEDING, null, null);
        return entity;
    }

    // Custom trade offers
    @Override
    protected void fillRecipes() {
        VillagerData villagerData = this.getVillagerData();
        Int2ObjectMap<TradeOffers.Factory[]> int2ObjectMap = LunarianTradeOffers.PROFESSION_TO_LEVELED_TRADE.get(villagerData.getProfession());
        if (int2ObjectMap == null || int2ObjectMap.isEmpty()) {
            return;
        }
        TradeOffers.Factory[] factorys = int2ObjectMap.get(villagerData.getLevel());
        if (factorys == null) {
            return;
        }
        TradeOfferList tradeOfferList = this.getOffers();
        this.fillRecipesFromPool(tradeOfferList, factorys, 2);
    }
}
