package com.github.alexnijjar.ad_astra.items.vehicles;

import com.github.alexnijjar.ad_astra.blocks.pads.RocketLaunchPad;
import com.github.alexnijjar.ad_astra.entities.vehicles.*;
import com.github.alexnijjar.ad_astra.registry.ModFluids;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class RocketItem<T extends RocketEntity> extends VehicleItem {

	private final EntityType<T> rocketEntity;
	private final int tier;

	public RocketItem(EntityType<T> rocketEntity, int tier, Settings settings) {
		super(settings);
		this.rocketEntity = rocketEntity;
		this.tier = tier;
	}

	@Override
	public List<Fluid> getInputFluids() {
		return List.of(ModFluids.FUEL_STILL, ModFluids.CRYO_FUEL_STILL);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		World world = context.getWorld();
		if (!world.isClient) {
			BlockPos pos = context.getBlockPos();
			BlockState state = world.getBlockState(pos);
			PlayerEntity player = context.getPlayer();

			// Check if the block can be spawned in a 3x8x3 radius
			for (int x = pos.getX() - 1; x < pos.getX() + 2; x++) {
				for (int y = pos.getY() + 1; y < pos.getY() + 9; y++) {
					for (int z = pos.getZ() - 1; z < pos.getZ() + 2; z++) {
						BlockPos testBlockPos = new BlockPos(x, y, z);
						BlockState testBlock = world.getBlockState(testBlockPos);
						if (!testBlock.isAir() && !(testBlock.getBlock() instanceof FluidBlock)) {
							return ActionResult.FAIL;
						}
					}
				}
			}

			if (state.getBlock() instanceof RocketLaunchPad pad) {
				if (state.get(RocketLaunchPad.STAGE).equals(true)) {
					ItemStack rocketStack = player.getStackInHand(context.getHand());
					if (rocketStack.getItem() instanceof RocketItem<?> rocket) {

						RocketEntity rocketEntity = null;

						int tier = rocket.getTier();
						switch (tier) {
							case 1 -> {
								rocketEntity = new RocketEntityTier1(rocket.getRocketEntity(), world);
							}
							case 2 -> {
								rocketEntity = new RocketEntityTier2(rocket.getRocketEntity(), world);
							}
							case 3 -> {
								rocketEntity = new RocketEntityTier3(rocket.getRocketEntity(), world);
							}
							case 4 -> {
								rocketEntity = new RocketEntityTier4(rocket.getRocketEntity(), world);
							}
						}

						if (rocketEntity != null) {

							// Check if a rocket is already placed on the pad
							Box scanAbove = new Box(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);
							List<RocketEntity> entities = world.getEntitiesByClass(RocketEntity.class, scanAbove, entity -> true);
							if (!entities.isEmpty()) {
								return ActionResult.PASS;
							}

							NbtCompound nbt = rocketStack.getOrCreateNbt();
							if (nbt.contains("fluid")) {
								if (!this.getFluid(rocketStack).isBlank()) {
									this.insertIntoTank(rocketEntity.inputTank, rocketStack);
								}
							}
							if (nbt.contains("inventory")) {
								rocketEntity.getInventory().readNbtList(nbt.getList("inventory", NbtElement.COMPOUND_TYPE));
							}

							rocketEntity.assignLaunchPad(true);
							rocketStack.decrement(1);
							world.playSound(null, pos, SoundEvents.BLOCK_NETHERITE_BLOCK_PLACE, SoundCategory.BLOCKS, 1, 1);

							rocketEntity.setPosition(pos.getX() + 0.5, pos.getY() + 0.26, pos.getZ() + 0.5);
							rocketEntity.setYaw(Math.round((player.getYaw() + 180) / 90) * 90);
							world.spawnEntity(rocketEntity);

							return ActionResult.SUCCESS;
						}
					}
				}
			}
		}
		return ActionResult.PASS;
	}

	public EntityType<T> getRocketEntity() {
		return this.rocketEntity;
	}

	public int getTier() {
		return tier;
	}

	@Override
	public long getTankSize() {
		return 3 * FluidConstants.BUCKET;
	}
}