package com.github.alexnijjar.beyond_earth.items;

import com.github.alexnijjar.beyond_earth.entities.SpacePaintingEntity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.AbstractDecorationEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DecorationItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class SpacePaintingItem extends DecorationItem {

	public SpacePaintingItem(EntityType<? extends AbstractDecorationEntity> type, Settings settings) {
		super(type, settings);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		BlockPos blockPos = context.getBlockPos();
		Direction direction = context.getSide();
		BlockPos blockPos2 = blockPos.offset(direction);
		PlayerEntity playerEntity = context.getPlayer();
		ItemStack itemStack = context.getStack();
		if (playerEntity != null && !this.canPlaceOn(playerEntity, direction, itemStack, blockPos2)) {
			return ActionResult.FAIL;
		}
		World world = context.getWorld();
		AbstractDecorationEntity abstractDecorationEntity = new SpacePaintingEntity(world, blockPos2, direction);

		NbtCompound nbtCompound = itemStack.getNbt();
		if (nbtCompound != null) {
			EntityType.loadFromEntityNbt(world, playerEntity, abstractDecorationEntity, nbtCompound);
		}
		if (abstractDecorationEntity.canStayAttached()) {
			if (!world.isClient) {
				abstractDecorationEntity.onPlace();
				world.emitGameEvent((Entity) playerEntity, GameEvent.ENTITY_PLACE, blockPos);
				world.spawnEntity(abstractDecorationEntity);
			}
			itemStack.decrement(1);
			return ActionResult.success(world.isClient);
		}
		return ActionResult.CONSUME;
	}
}
