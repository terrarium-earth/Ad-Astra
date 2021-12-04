package net.mrscauthd.boss_tools.entity.alien;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.entity.ai.brain.schedule.Schedule;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.merchant.IMerchant;
import net.minecraft.entity.merchant.villager.VillagerData;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.villager.VillagerType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.MerchantOffers;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.server.ServerWorld;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.entity.AlienZombieEntity;
import net.mrscauthd.boss_tools.events.Config;


import net.minecraft.world.World;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

public class AlienEntity extends VillagerEntity implements IMerchant, INPC {
	public static ImmutableList<Pair<Integer, ? extends Task<? super VillagerEntity>>> core(VillagerProfession profession, float p_220638_1_) {
		return ImmutableList.of(Pair.of(0, new SwimTask(0.8F)), Pair.of(0, new InteractWithDoorTask()), Pair.of(0, new LookTask(45, 90)), Pair.of(0, new PanicTask()), Pair.of(0, new WakeUpTask()), Pair.of(0, new HideFromRaidOnBellRingTask()), Pair.of(0, new BeginRaidTask()), Pair.of(0, new ExpirePOITask(profession.getPointOfInterest(), MemoryModuleType.JOB_SITE)), Pair.of(0, new ExpirePOITask(profession.getPointOfInterest(), MemoryModuleType.POTENTIAL_JOB_SITE)), Pair.of(1, new WalkToTargetTask()), Pair.of(2, new SwitchVillagerJobTask(profession)), Pair.of(3, new TradeTask(p_220638_1_)), Pair.of(5, new PickupWantedItemTask(p_220638_1_, false, 4)), Pair.of(6, new GatherPOITask(profession.getPointOfInterest(), MemoryModuleType.JOB_SITE, MemoryModuleType.POTENTIAL_JOB_SITE, true, Optional.empty())), Pair.of(7, new FindPotentialJobTask(p_220638_1_)), Pair.of(8, new FindJobTask(p_220638_1_)), Pair.of(10, new GatherPOITask(PointOfInterestType.HOME, MemoryModuleType.HOME, false, Optional.of((byte)14))), Pair.of(10, new GatherPOITask(PointOfInterestType.MEETING, MemoryModuleType.MEETING_POINT, true, Optional.of((byte)14))));
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes(){
		return MobEntity.func_233666_p_()
				.createMutableAttribute(Attributes.MAX_HEALTH, 20)
				.createMutableAttribute(Attributes.MOVEMENT_SPEED,0.5D)
				.createMutableAttribute(Attributes.FOLLOW_RANGE, 48.0D);
	}

	public AlienEntity(EntityType<? extends VillagerEntity> type, World worldIn) {
		super(type, worldIn);
	}

	@Override
	public AlienEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {

		AlienEntity alienentity = new AlienEntity((EntityType<? extends VillagerEntity>) ModInnet.ALIEN.get(),this.world);
		alienentity.onInitialSpawn(p_241840_1_, p_241840_1_.getDifficultyForLocation(alienentity.getPosition()), SpawnReason.BREEDING, (ILivingEntityData)null, (CompoundNBT)null);
		return alienentity;
	}

	@Override
	public void func_241841_a(ServerWorld p_241841_1_, LightningBoltEntity p_241841_2_) {
		this.forceFireTicks(this.getFireTimer() + 1);
		if (this.getFireTimer() == 0) {
			this.setFire(8);
		}

		this.attackEntityFrom(DamageSource.LIGHTNING_BOLT, p_241841_2_.getDamage());
	}

	@Override
	public ActionResultType func_230254_b_(PlayerEntity p_230254_1_, Hand p_230254_2_) {
		ItemStack itemstack = p_230254_1_.getHeldItem(p_230254_2_);
		if (itemstack.getItem() != ModInnet.ALIEN_SPAWN_EGG.get() && this.isAlive() && !this.hasCustomer() && !this.isSleeping() && !p_230254_1_.isSecondaryUseActive()) {
			if (this.isChild()) {
				this.shakeHead();
				return ActionResultType.func_233537_a_(this.world.isRemote);
			} else {
				boolean flag = this.getOffers().isEmpty();
				if (p_230254_2_ == Hand.MAIN_HAND) {
					if (flag && !this.world.isRemote) {
						this.shakeHead();
					}

					p_230254_1_.addStat(Stats.TALKED_TO_VILLAGER);
				}

				if (flag) {
					return ActionResultType.func_233537_a_(this.world.isRemote);
				} else {
					if (!this.world.isRemote && !this.offers.isEmpty()) {
						this.displayMerchantGui(p_230254_1_);
					}

					return ActionResultType.func_233537_a_(this.world.isRemote);
				}
			}
		} else {
			return ActionResultType.PASS;
		}
	}

	private void displayMerchantGui(PlayerEntity player) {
		this.recalculateSpecialPricesFor(player);
		this.setCustomer(player);
		this.openMerchantContainer(player, this.getDisplayName(), this.getVillagerData().getLevel());
	}

	private void recalculateSpecialPricesFor(PlayerEntity playerIn) {
		int i = this.getPlayerReputation(playerIn);
		if (i != 0) {
			for(MerchantOffer merchantoffer : this.getOffers()) {
				merchantoffer.increaseSpecialPrice(-MathHelper.floor((float)i * merchantoffer.getPriceMultiplier()));
			}
		}

		if (playerIn.isPotionActive(Effects.HERO_OF_THE_VILLAGE)) {
			EffectInstance effectinstance = playerIn.getActivePotionEffect(Effects.HERO_OF_THE_VILLAGE);
			int k = effectinstance.getAmplifier();

			for(MerchantOffer merchantoffer1 : this.getOffers()) {
				double d0 = 0.3D + 0.0625D * (double)k;
				int j = (int)Math.floor(d0 * (double)merchantoffer1.getBuyingStackFirst().getCount());
				merchantoffer1.increaseSpecialPrice(-Math.max(j, 1));
			}
		}

	}

	private void shakeHead() {
		this.setShakeHeadTicks(40);
		if (!this.world.isRemote()) {
			this.playSound(SoundEvents.ENTITY_VILLAGER_NO, this.getSoundVolume(), this.getSoundPitch());
		}

	}

	@Override
	protected Brain<?> createBrain(Dynamic<?> dynamicIn) {
		Brain<VillagerEntity> brain = this.getBrainCodec().deserialize(dynamicIn);
		this.initBrain(brain);
		return brain;
	}

	@Override
	public void resetBrain(ServerWorld serverWorldIn) {
		Brain<VillagerEntity> brain = this.getBrain();
		brain.stopAllTasks(serverWorldIn, this);
		this.brain = brain.copy();
		this.initBrain(this.getBrain());
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, AlienZombieEntity.class, 15.0F, 0.5F, 0.5F));
	}

	private void initBrain(Brain<VillagerEntity> villagerBrain) {
		VillagerProfession villagerprofession = this.getVillagerData().getProfession();
		if (this.isChild()) {
			villagerBrain.setSchedule(Schedule.VILLAGER_BABY);
			villagerBrain.registerActivity(Activity.PLAY, VillagerTasks.play(0.5F));
		} else {
			villagerBrain.setSchedule(Schedule.VILLAGER_DEFAULT);
			villagerBrain.registerActivity(Activity.WORK, VillagerTasks.work(villagerprofession, 0.5F), ImmutableSet.of(Pair.of(MemoryModuleType.JOB_SITE, MemoryModuleStatus.VALUE_PRESENT)));
		}

		villagerBrain.registerActivity(Activity.CORE, AlienEntity.core(villagerprofession, 0.5F));
		villagerBrain.registerActivity(Activity.REST, VillagerTasks.rest(villagerprofession, 0.5F));
		villagerBrain.registerActivity(Activity.IDLE, VillagerTasks.idle(villagerprofession, 0.5F));
		villagerBrain.registerActivity(Activity.PANIC, VillagerTasks.panic(villagerprofession, 0.5F));
		villagerBrain.registerActivity(Activity.PRE_RAID, VillagerTasks.preRaid(villagerprofession, 0.5F));
		villagerBrain.registerActivity(Activity.RAID, VillagerTasks.raid(villagerprofession, 0.5F));
		villagerBrain.registerActivity(Activity.HIDE, VillagerTasks.hide(villagerprofession, 0.5F));
		villagerBrain.setDefaultActivities(ImmutableSet.of(Activity.CORE));
		villagerBrain.setFallbackActivity(Activity.IDLE);
		villagerBrain.switchTo(Activity.IDLE);
		villagerBrain.updateActivity(this.world.getDayTime(), this.world.getGameTime());
	}

	@Nullable
	@Override
	public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {

		if (reason == SpawnReason.COMMAND || reason == SpawnReason.SPAWN_EGG || reason == SpawnReason.SPAWNER || reason == SpawnReason.DISPENSER) {
			this.setVillagerData(this.getVillagerData().withType(VillagerType.func_242371_a(worldIn.func_242406_i(this.getPosition()))));
		}

		if (reason == SpawnReason.STRUCTURE) {
			this.assignProfessionWhenSpawned = true;
		}

		if (spawnDataIn == null) {
			spawnDataIn = new AgeableEntity.AgeableData(false);
		}

		int max = 13;
		int min = 1;

		for (int i = 0; i < new Random().nextInt((max+1)-min)+min; i++) {

			AlienJobs j = AlienJobs.values()[i];

			this.setVillagerData(this.getVillagerData().withProfession(j.getAlienJobs()));
		}

		return spawnDataIn;
	}

	@Override
	public void func_242367_a(ServerWorld p_242367_1_, long p_242367_2_, int p_242367_4_) {
	}

	@Override
	protected void populateTradeData() {
		VillagerData villagerdata = this.getVillagerData();
		Int2ObjectMap<AlienTrade.ITrade[]> int2objectmap = AlienTrade.VILLAGER_DEFAULT_TRADES.get(villagerdata.getProfession());
		if (int2objectmap != null && !int2objectmap.isEmpty()) {
			AlienTrade.ITrade[] avillagertrades$itrade = int2objectmap.get(villagerdata.getLevel());
			if (avillagertrades$itrade != null) {
				MerchantOffers merchantoffers = this.getOffers();
				this.addTrades(merchantoffers, avillagertrades$itrade, 6);
			}
		}
	}

	protected void addTrades(MerchantOffers givenMerchantOffers, AlienTrade.ITrade[] newTrades, int maxNumbers) {
		Set<Integer> set = Sets.newHashSet();
		if (newTrades.length > maxNumbers) {
			while(set.size() < maxNumbers) {
				set.add(this.rand.nextInt(newTrades.length));
			}
		} else {
			for(int i = 0; i < newTrades.length; ++i) {
				set.add(i);
			}
		}

		for(Integer integer : set) {
			AlienTrade.ITrade villagertrades$itrade = newTrades[integer];
			MerchantOffer merchantoffer = villagertrades$itrade.getOffer(this, this.rand);
			if (merchantoffer != null) {
				givenMerchantOffers.add(merchantoffer);
			}
		}

	}

	@Override
	public void baseTick() {
		super.baseTick();

		if (!Config.AlienSpawn) {
			this.remove();
		}
	}

}