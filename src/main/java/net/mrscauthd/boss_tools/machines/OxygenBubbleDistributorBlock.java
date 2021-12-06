package net.mrscauthd.boss_tools.machines;

import java.util.List;
import java.util.function.Supplier;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.network.NetworkEvent;
import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.capability.IOxygenStorage;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeType;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeTypes;
import net.mrscauthd.boss_tools.crafting.OxygenMakingRecipeAbstract;
import net.mrscauthd.boss_tools.gui.screens.oxygenbubbledistributor.OxygenBubbleDistributorGui;
import net.mrscauthd.boss_tools.machines.tile.NamedComponentRegistry;
import net.mrscauthd.boss_tools.machines.tile.OxygenMakingBlockEntity;
import net.mrscauthd.boss_tools.machines.tile.PowerSystemEnergyCommon;
import net.mrscauthd.boss_tools.machines.tile.PowerSystemRegistry;

public class OxygenBubbleDistributorBlock {

	public static final int ENERGY_PER_TICK = 1;
	public static final String KEY_TIMER = "timer";
	public static final String KEY_RANGE = "range";
	public static final String KEY_WORKINGAREA_VISIBLE = "workingAreaVisible";

	public static final int RANGE_MAX = 15;
	public static final int RANGE_MIN = 1;

	/**
	 * Interval Ticks, 4 = every 4 ticks
	 */
	public static final int MAX_TIMER = 4;

	public static class CustomBlock extends AbstractMachineBlock<OxygenBubbleDistributorBlockEntity> {

		public CustomBlock() {
			super(Block.Properties.of(Material.METAL).sound(SoundType.METAL).strength(5f, 1f).lightLevel(s -> 0).requiresCorrectToolForDrops());
		}

		@Override
		public void appendHoverText(ItemStack itemstack, BlockGetter level, List<Component> list, TooltipFlag flag) {
			super.appendHoverText(itemstack, level, list, flag);
			
			int min = RANGE_MIN * 2 + 1;
			int max = RANGE_MAX * 2 + 1;
			list.add(new TranslatableComponent("tooltip." + BossToolsMod.ModId + ".oxygen_bubble_distributor", min, max).setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
		}

		@Override
		public OxygenBubbleDistributorBlockEntity newBlockEntity(BlockPos pos, BlockState state) {
			return new OxygenBubbleDistributorBlockEntity(pos, state);
		}

	}

	public static class OxygenBubbleDistributorBlockEntity extends OxygenMakingBlockEntity {

		public OxygenBubbleDistributorBlockEntity(BlockPos pos, BlockState state) {
			super(ModInnet.OXYGEN_BUBBLE_DISTRIBUTOR.get(), pos, state);
			this.setWorkingAreaVisible(false);
		}

		@Override
		protected boolean canActivated() {
			if (this.getOutputTank().getOxygenStored() >= this.getOxygenUsing(this.getRange())) {
				return true;
			}

			return super.canActivated();
		}
		
//		@OnlyIn(Dist.CLIENT)
//		@Override
//		public double getMaxRenderDistanceSquared() {
//			return 256.0D;
//		}

		@Override
		public AABB getRenderBoundingBox() {
			return new AABB(this.getBlockPos()).inflate(32, 32, 32);
		}

		@Override
		public AbstractContainerMenu createMenu(int id, Inventory inventory) {
			return new OxygenBubbleDistributorGui.GuiContainer(id, inventory, this);
		}

		@Override
		protected void createEnergyStorages(NamedComponentRegistry<IEnergyStorage> registry) {
			super.createEnergyStorages(registry);
			registry.put(this.createEnergyStorageCommon());
		}

		protected void tickProcessing() {
			super.tickProcessing();

			this.tickDistributeTimer();
		}

		/**
		 * timer will cycle 0, 1, 2, 3
		 */
		private void tickDistributeTimer() {
			if (this.getTimer() >= this.getMaxTimer()) {
				this.setTimer(0);
				this.distribute();
			}
			this.setTimer(this.getTimer() + 1);
		}

		private void distribute() {
			IOxygenStorage oxygenStorage = this.getOutputTank();
			double range = this.getRange();
			int oxygenUsing = this.getOxygenUsing(range);

			if (oxygenStorage.extractOxygen(oxygenUsing, true) == oxygenUsing) {
				if (this.isProcessedInThisTick() || this.consumePowerForOperation() != null) {
					oxygenStorage.extractOxygen(oxygenUsing, false);
					this.spawnOxygenBubble(range);
				}
			}
		}

		private void spawnOxygenBubble(double range) {
			Level level = this.getLevel();
			List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, this.getWorkingArea(range), null);

			for (LivingEntity entity : entities) {
				entity.addEffect(new MobEffectInstance(ModInnet.OXYGEN_EFFECT.get(),2 * 24,0, false ,false));
			}

			if (level instanceof ServerLevel) {
				ServerLevel serverLevel = (ServerLevel) level;
				Vec3 center = new AABB(this.getBlockPos()).getCenter();
				serverLevel.sendParticles(ParticleTypes.CLOUD, center.x, center.y + 0.5D, center.z, 1, 0.1D, 0.1D, 0.1D, 0.001D);
			}

			this.setProcessedInThisTick();
		}

		public int getMaxTimer() {
			return MAX_TIMER;
		}

		public int getTimer() {
			return this.getTileData().getInt(KEY_TIMER);
		}

		public void setTimer(int timer) {
			timer = Math.max(timer, 0);

			if (this.getTimer() != timer) {
				this.getTileData().putInt(KEY_TIMER, timer);
				this.markDirty();
			}
		}

		public int getOxygenUsing(double range) {
			return (int) range + 1;
		}

		public int getRange() {
			return Math.max(this.getTileData().getInt(KEY_RANGE), RANGE_MIN);
		}

		public void setRange(int range) {
			range = Math.min(Math.max(range, RANGE_MIN), RANGE_MAX);

			if (this.getRange() != range) {
				this.getTileData().putInt(KEY_RANGE, range);
				this.markDirty();
			}
		}

		public boolean isWorkingAreaVisible() {
			return this.getTileData().getBoolean(KEY_WORKINGAREA_VISIBLE);
		}

		public void setWorkingAreaVisible(boolean visible) {
			if (this.isWorkingAreaVisible() != visible) {
				this.getTileData().putBoolean(KEY_WORKINGAREA_VISIBLE, visible);
				this.markDirty();
			}
		}

		public AABB getWorkingArea(double range) {
			return this.getWorkingArea(this.getBlockPos(), range);
		}

		public AABB getWorkingArea(BlockPos pos, double range) {
			return new AABB(pos).inflate(range).move(0.0D, range, 0.0D);
		}

		@Override
		protected BooleanProperty getBlockActivatedProperty() {
			return CustomBlock.LIT;
		}

		@Override
		protected void createPowerSystems(PowerSystemRegistry map) {
			super.createPowerSystems(map);

			map.put(new PowerSystemEnergyCommon(this) {
				@Override
				public int getBasePowerForOperation() {
					return OxygenBubbleDistributorBlockEntity.this.getBasePowerForOperation();
				}
			});
		}

		public int getBasePowerForOperation() {
			return ENERGY_PER_TICK;
		}

		@Override
		public BossToolsRecipeType<? extends OxygenMakingRecipeAbstract> getRecipeType() {
			return BossToolsRecipeTypes.OXYGENBUBBLEDISTRIBUTOR;
		}
	}

	public static class ChangeRangeMessage {
		private BlockPos blockPos = BlockPos.ZERO;
		private boolean direction = false;

		public ChangeRangeMessage() {

		}

		public ChangeRangeMessage(BlockPos pos, boolean direction) {
			this.setBlockPos(pos);
			this.setDirection(direction);
		}

		public ChangeRangeMessage(FriendlyByteBuf buffer) {
			this.setBlockPos(buffer.readBlockPos());
			this.setDirection(buffer.readBoolean());
		}

		public BlockPos getBlockPos() {
			return this.blockPos;
		}

		public void setBlockPos(BlockPos blockPos) {
			this.blockPos = blockPos;
		}

		public boolean getDirection() {
			return this.direction;
		}

		public void setDirection(boolean direction) {
			this.direction = direction;
		}

		public static ChangeRangeMessage decode(FriendlyByteBuf buffer) {
			return new ChangeRangeMessage(buffer);
		}

		public static void encode(ChangeRangeMessage message, FriendlyByteBuf buffer) {
			buffer.writeBlockPos(message.getBlockPos());
			buffer.writeBoolean(message.getDirection());
		}

		public static void handle(ChangeRangeMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			OxygenBubbleDistributorBlockEntity blockEntity = (OxygenBubbleDistributorBlockEntity) context.getSender().level.getBlockEntity(message.getBlockPos());
			int prev = blockEntity.getRange();
			int next = prev + (message.getDirection() ? +1 : -1);
			blockEntity.setRange(next);
			context.setPacketHandled(true);
		}
	}

	public static class ChangeWorkingAreaVisibleMessage {
		private BlockPos blockPos = BlockPos.ZERO;
		private boolean visible = false;

		public ChangeWorkingAreaVisibleMessage() {

		}

		public ChangeWorkingAreaVisibleMessage(BlockPos pos, boolean visible) {
			this.setBlockPos(pos);
			this.setVisible(visible);
		}

		public ChangeWorkingAreaVisibleMessage(FriendlyByteBuf buffer) {
			this.setBlockPos(buffer.readBlockPos());
			this.setVisible(buffer.readBoolean());
		}

		public BlockPos getBlockPos() {
			return this.blockPos;
		}

		public void setBlockPos(BlockPos blockPos) {
			this.blockPos = blockPos;
		}

		public boolean isVisible() {
			return this.visible;
		}

		public void setVisible(boolean visible) {
			this.visible = visible;
		}

		public static ChangeWorkingAreaVisibleMessage decode(FriendlyByteBuf buffer) {
			return new ChangeWorkingAreaVisibleMessage(buffer);
		}

		public static void encode(ChangeWorkingAreaVisibleMessage message, FriendlyByteBuf buffer) {
			buffer.writeBlockPos(message.getBlockPos());
			buffer.writeBoolean(message.isVisible());
		}

		public static void handle(ChangeWorkingAreaVisibleMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			OxygenBubbleDistributorBlockEntity blockEntity = (OxygenBubbleDistributorBlockEntity) context.getSender().level.getBlockEntity(message.getBlockPos());
			blockEntity.setWorkingAreaVisible(message.isVisible());
			context.setPacketHandled(true);
		}
	}
}
