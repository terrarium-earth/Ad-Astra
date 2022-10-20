package earth.terrarium.ad_astra.items;

import java.util.Optional;

import earth.terrarium.ad_astra.entities.SpacePaintingEntity;

import net.minecraft.entity.EntityType;
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

	public SpacePaintingItem(EntityType<? extends SpacePaintingEntity> type, Settings settings) {
		super(type, settings);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		SpacePaintingEntity spacePainting;
		BlockPos blockPos = context.getBlockPos();
		Direction direction = context.getSide();
		BlockPos blockPos2 = blockPos.offset(direction);
		PlayerEntity playerEntity = context.getPlayer();
		ItemStack itemStack = context.getStack();
		if (playerEntity != null && !this.canPlaceOn(playerEntity, direction, itemStack, blockPos2)) {
			return ActionResult.FAIL;
		}
		World world = context.getWorld();

		Optional<SpacePaintingEntity> optional = SpacePaintingEntity.placeSpacePainting(world, blockPos2, direction);
		if (optional.isEmpty()) {
			return ActionResult.CONSUME;
		}
		spacePainting = optional.get();

		NbtCompound nbtCompound = itemStack.getNbt();
		if (nbtCompound != null) {
			EntityType.loadFromEntityNbt(world, playerEntity, spacePainting, nbtCompound);
		}
		if (spacePainting.canStayAttached()) {
			if (!world.isClient) {
				spacePainting.onPlace();
				world.emitGameEvent(playerEntity, GameEvent.ENTITY_PLACE, spacePainting.getBlockPos());
				world.spawnEntity(spacePainting);
			}
			itemStack.decrement(1);
			return ActionResult.success(world.isClient);
		}
		return ActionResult.CONSUME;
	}
}
