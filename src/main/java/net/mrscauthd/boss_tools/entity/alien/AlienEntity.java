package net.mrscauthd.boss_tools.entity.alien;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.schedule.Schedule;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.entity.AlienZombieEntity;
import net.mrscauthd.boss_tools.events.Config;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

public class AlienEntity extends Villager implements Merchant, Npc {
	public static ImmutableList<Pair<Integer, ? extends Behavior>> core(VillagerProfession profession, float p_220638_1_) {
		return ImmutableList.of(Pair.of(0, new Swim(0.8F)), Pair.of(0, new InteractWithDoor()), Pair.of(0, new LookAtTargetSink(45, 90)), Pair.of(0, new VillagerPanicTrigger()), Pair.of(0, new WakeUp()), Pair.of(0, new ReactToBell()), Pair.of(0, new SetRaidStatus()), Pair.of(0, new ValidateNearbyPoi(profession.getJobPoiType(), MemoryModuleType.JOB_SITE)), Pair.of(0, new ValidateNearbyPoi(profession.getJobPoiType(), MemoryModuleType.POTENTIAL_JOB_SITE)), Pair.of(1, new MoveToTargetSink()), Pair.of(2, new PoiCompetitorScan(profession)), Pair.of(3, new LookAndFollowTradingPlayerSink(p_220638_1_)), Pair.of(5, new GoToWantedItem(p_220638_1_, false, 4)), Pair.of(6, new AcquirePoi(profession.getJobPoiType(), MemoryModuleType.JOB_SITE, MemoryModuleType.POTENTIAL_JOB_SITE, true, Optional.empty())), Pair.of(7, new GoToPotentialJobSite(p_220638_1_)), Pair.of(8, new YieldJobSite(p_220638_1_)), Pair.of(10, new AcquirePoi(PoiType.HOME, MemoryModuleType.HOME, false, Optional.of((byte)14))), Pair.of(10, new AcquirePoi(PoiType.MEETING, MemoryModuleType.MEETING_POINT, true, Optional.of((byte)14))));
	}

	private static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(MemoryModuleType.HOME, MemoryModuleType.JOB_SITE, MemoryModuleType.POTENTIAL_JOB_SITE, MemoryModuleType.MEETING_POINT, MemoryModuleType.NEAREST_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryModuleType.VISIBLE_VILLAGER_BABIES, MemoryModuleType.NEAREST_PLAYERS, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM, MemoryModuleType.WALK_TARGET, MemoryModuleType.LOOK_TARGET, MemoryModuleType.INTERACTION_TARGET, MemoryModuleType.BREED_TARGET, MemoryModuleType.PATH, MemoryModuleType.DOORS_TO_CLOSE, MemoryModuleType.NEAREST_BED, MemoryModuleType.HURT_BY, MemoryModuleType.HURT_BY_ENTITY, MemoryModuleType.NEAREST_HOSTILE, MemoryModuleType.SECONDARY_JOB_SITE, MemoryModuleType.HIDING_PLACE, MemoryModuleType.HEARD_BELL_TIME, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.LAST_SLEPT, MemoryModuleType.LAST_WOKEN, MemoryModuleType.LAST_WORKED_AT_POI, MemoryModuleType.GOLEM_DETECTED_RECENTLY);
	private static final ImmutableList<SensorType<? extends Sensor<? super Villager>>> SENSOR_TYPES = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_PLAYERS, SensorType.NEAREST_ITEMS, SensorType.NEAREST_BED, SensorType.HURT_BY, SensorType.VILLAGER_HOSTILES, SensorType.VILLAGER_BABIES, SensorType.SECONDARY_POIS, SensorType.GOLEM_DETECTED);


	public static AttributeSupplier.Builder setCustomAttributes() {
		return Mob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 20)
				.add(Attributes.MOVEMENT_SPEED,0.5D)
				.add(Attributes.FOLLOW_RANGE, 48.0D);
	}

	public AlienEntity(EntityType<? extends Villager> type, Level worldIn) {
		super(type, worldIn);
	}

	@Override
	public Villager getBreedOffspring(ServerLevel p_241840_1_, AgeableMob p_241840_2_) {
		AlienEntity alienentity = new AlienEntity((EntityType<? extends Villager>) ModInnet.ALIEN.get(),this.level);
		alienentity.finalizeSpawn(p_241840_1_, p_241840_1_.getCurrentDifficultyAt(new BlockPos(p_241840_2_.getX(), p_241840_2_.getY(), p_241840_2_.getZ())), MobSpawnType.BREEDING, (SpawnGroupData)null, (CompoundTag)null);
		return alienentity;
	}

	@Override
	public void thunderHit(ServerLevel p_35409_, LightningBolt p_35410_) {
		this.setRemainingFireTicks(this.getRemainingFireTicks() + 1);
		if (this.getRemainingFireTicks() == 0) {
			this.setSecondsOnFire(8);
		}
		super.thunderHit(p_35409_, p_35410_);
	}

	@Override
	public InteractionResult mobInteract(Player p_230254_1_, InteractionHand p_230254_2_) {
		ItemStack itemstack = p_230254_1_.getItemInHand(p_230254_2_);
		if (itemstack.getItem() != ModInnet.ALIEN_SPAWN_EGG.get() && this.isAlive() && !this.isTrading() && !this.isSleeping() && !p_230254_1_.isSecondaryUseActive()) {
			if (this.isBaby()) {
				this.shakeHead();
				return InteractionResult.sidedSuccess(this.level.isClientSide);
			} else {
				boolean flag = this.getOffers().isEmpty();
				if (p_230254_2_ == InteractionHand.MAIN_HAND) {
					if (flag && !this.level.isClientSide) {
						this.shakeHead();
					}

					p_230254_1_.awardStat(Stats.TALKED_TO_VILLAGER);
				}

				if (flag) {
					return InteractionResult.sidedSuccess(this.level.isClientSide);
				} else {
					if (!this.level.isClientSide && !this.offers.isEmpty()) {
						this.displayMerchantGui(p_230254_1_);
					}

					return InteractionResult.sidedSuccess(this.level.isClientSide);
				}
			}
		} else {
			return InteractionResult.PASS;
		}
	}

	private void displayMerchantGui(Player player) {
		this.recalculateSpecialPricesFor(player);
		this.setTradingPlayer(player);
		this.openTradingScreen(player, this.getDisplayName(), this.getVillagerData().getLevel());
	}

	private void recalculateSpecialPricesFor(Player playerIn) {
		int i = this.getPlayerReputation(playerIn);
		if (i != 0) {
			for(MerchantOffer merchantoffer : this.getOffers()) {
				merchantoffer.addToSpecialPriceDiff((int) -Math.floor((float)i * merchantoffer.getPriceMultiplier()));
			}
		}

		if (playerIn.hasEffect(MobEffects.HERO_OF_THE_VILLAGE)) {
			MobEffectInstance effectinstance = playerIn.getEffect(MobEffects.HERO_OF_THE_VILLAGE);
			int k = effectinstance.getAmplifier();

			for(MerchantOffer merchantoffer1 : this.getOffers()) {
				double d0 = 0.3D + 0.0625D * (double)k;
				int j = (int)Math.floor(d0 * (double)merchantoffer1.getBaseCostA().getCount());
				merchantoffer1.addToSpecialPriceDiff(-Math.max(j, 1));
			}
		}

	}

	private void shakeHead() {
		this.setUnhappyCounter(40);
		if (!this.level.isClientSide) {
			this.playSound(SoundEvents.VILLAGER_NO, this.getSoundVolume(), this.getVoicePitch());
		}
	}

	@Override
	protected Brain<?> makeBrain(Dynamic<?> p_35445_) {
		Brain<Villager> brain = Brain.provider(MEMORY_TYPES, SENSOR_TYPES).makeBrain(p_35445_);
		this.initBrain(brain);
		return brain;
	}

	@Override
	public void refreshBrain(ServerLevel p_35484_) {
		Brain<Villager> brain = this.getBrain();
		brain.stopAll(p_35484_, this);
		this.brain = brain.copyWithoutBehaviors();
		this.initBrain(this.getBrain());
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, AlienZombieEntity.class, 15.0F, 0.5F, 0.5F));
	}

	private void initBrain(Brain<Villager> p_35425_) {
		VillagerProfession villagerprofession = this.getVillagerData().getProfession();
		if (this.isBaby()) {
			p_35425_.setSchedule(Schedule.VILLAGER_BABY);
			p_35425_.addActivity(Activity.PLAY, VillagerGoalPackages.getPlayPackage(0.5F));
		} else {
			p_35425_.setSchedule(Schedule.VILLAGER_DEFAULT);
			p_35425_.addActivityWithConditions(Activity.WORK, VillagerGoalPackages.getWorkPackage(villagerprofession, 0.5F), ImmutableSet.of(Pair.of(MemoryModuleType.JOB_SITE, MemoryStatus.VALUE_PRESENT)));
		}

		p_35425_.addActivity(Activity.CORE, VillagerGoalPackages.getCorePackage(villagerprofession, 0.5F));
		p_35425_.addActivity(Activity.REST, VillagerGoalPackages.getRestPackage(villagerprofession, 0.5F));
		p_35425_.addActivity(Activity.IDLE, VillagerGoalPackages.getIdlePackage(villagerprofession, 0.5F));
		p_35425_.addActivity(Activity.PANIC, VillagerGoalPackages.getPanicPackage(villagerprofession, 0.5F));
		p_35425_.addActivity(Activity.PRE_RAID, VillagerGoalPackages.getPreRaidPackage(villagerprofession, 0.5F));
		p_35425_.addActivity(Activity.RAID, VillagerGoalPackages.getRaidPackage(villagerprofession, 0.5F));
		p_35425_.addActivity(Activity.HIDE, VillagerGoalPackages.getHidePackage(villagerprofession, 0.5F));
		p_35425_.setCoreActivities(ImmutableSet.of(Activity.CORE));
		p_35425_.setDefaultActivity(Activity.IDLE);
		p_35425_.setActiveActivityIfPossible(Activity.IDLE);
		p_35425_.updateActivityFromSchedule(this.level.getDayTime(), this.level.getGameTime());
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @org.jetbrains.annotations.Nullable SpawnGroupData spawnDataIn, @org.jetbrains.annotations.Nullable CompoundTag dataTag) {

		if (reason == MobSpawnType.COMMAND || reason == MobSpawnType.SPAWN_EGG || reason == MobSpawnType.SPAWNER || reason == MobSpawnType.DISPENSER) {
			this.setVillagerData(this.getVillagerData().setType(VillagerType.byBiome(worldIn.getBiomeName(new BlockPos(this.getX(),this.getY(),this.getZ())))));
		}

		if (reason == MobSpawnType.STRUCTURE) {
			this.assignProfessionWhenSpawned = true;
		}

		if (spawnDataIn == null) {
			spawnDataIn = new AgeableMob.AgeableMobGroupData(false);
		}

		int max = 13;
		int min = 1;

		for (int i = 0; i < new Random().nextInt((max+1)-min)+min; i++) {

			AlienJobs j = AlienJobs.values()[i];

			this.setVillagerData(this.getVillagerData().setProfession(j.getAlienJobs()));
		}

		return spawnDataIn;
	}

	@Override
	public void spawnGolemIfNeeded(ServerLevel p_35398_, long p_35399_, int p_35400_) {

	}

	@Override
	protected void updateTrades() {
		VillagerData villagerdata = this.getVillagerData();
		Int2ObjectMap<AlienTrade.ITrade[]> int2objectmap = AlienTrade.VILLAGER_DEFAULT_TRADES.get(villagerdata.getProfession());
		if (int2objectmap != null && !int2objectmap.isEmpty()) {
			AlienTrade.ITrade[] avillagertrades$itrade = int2objectmap.get(villagerdata.getLevel());
			if (avillagertrades$itrade != null) {
				MerchantOffers merchantoffers = this.getOffers();
				this.addOffersFromItemListings(merchantoffers, (VillagerTrades.ItemListing[]) avillagertrades$itrade, 6);
			}
		}
	}

	protected void addOffersFromItemListings(MerchantOffers p_35278_, VillagerTrades.ItemListing[] p_35279_, int p_35280_) {
		Set<Integer> set = Sets.newHashSet();
		if (p_35279_.length > p_35280_) {
			while(set.size() < p_35280_) {
				set.add(this.random.nextInt(p_35279_.length));
			}
		} else {
			for(int i = 0; i < p_35279_.length; ++i) {
				set.add(i);
			}
		}

		for(Integer integer : set) {
			VillagerTrades.ItemListing villagertrades$itemlisting = p_35279_[integer];
			MerchantOffer merchantoffer = villagertrades$itemlisting.getOffer(this, this.random);
			if (merchantoffer != null) {
				p_35278_.add(merchantoffer);
			}
		}

	}

	@Override
	public void baseTick() {
		super.baseTick();

		if (!Config.AlienSpawn) {
			this.remove(RemovalReason.DISCARDED);
		}
	}
}