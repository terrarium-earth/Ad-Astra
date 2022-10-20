package earth.terrarium.ad_astra.items.vehicles;

import java.util.List;

import earth.terrarium.ad_astra.entities.vehicles.RoverEntity;
import earth.terrarium.ad_astra.registry.ModEntityTypes;

import earth.terrarium.botarium.api.fluid.FluidHooks;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class RoverItem extends VehicleItem {

	public RoverItem(Settings settings) {
		super(settings);
	}

    @Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		World world = context.getWorld();
		if (!world.isClient) {
			BlockPos pos = context.getBlockPos();

			// Check if the block can be spawned in a 3x3x3 radius.
			for (int x = pos.getX() - 1; x < pos.getX() + 2; x++) {
				for (int y = pos.getY() + 1; y < pos.getY() + 4; y++) {
					for (int z = pos.getZ() - 1; z < pos.getZ() + 2; z++) {
						BlockPos testBlockPos = new BlockPos(x, y, z);
						BlockState testBlock = world.getBlockState(testBlockPos);
						if (testBlock.isFullCube(world, testBlockPos) && !(testBlock.getBlock() instanceof FluidBlock)) {
							return ActionResult.FAIL;
						}
					}
				}
			}

			ItemStack roverStack = context.getPlayer().getStackInHand(context.getHand());
			RoverEntity rover = new RoverEntity(ModEntityTypes.TIER_1_ROVER.get(), world);

			// Prevent placing rovers in rovers
			Box scanAbove = new Box(pos.getX() - 2, pos.getY() - 2, pos.getZ() - 2, pos.getX() + 2, pos.getY() + 2, pos.getZ() + 2);
			List<RoverEntity> entities = world.getEntitiesByClass(RoverEntity.class, scanAbove, entity -> true);
			if (!entities.isEmpty()) {
				return ActionResult.PASS;
			}

			NbtCompound nbt = roverStack.getOrCreateNbt();
			if (nbt.contains("fluid")) {
				if (!this.getFluid(roverStack).equals(Fluids.EMPTY)) {
					this.insertIntoTank(rover.tank, roverStack);
				}
			}
			if (nbt.contains("inventory")) {
				rover.getInventory().readNbtList(nbt.getList("inventory", NbtElement.COMPOUND_TYPE));
			}
			rover.setYaw(context.getPlayer().getYaw());
			rover.setPosition(context.getHitPos().add(0, 0, 1));
			world.spawnEntity(rover);
			roverStack.decrement(1);
			world.playSound(null, pos, SoundEvents.BLOCK_LODESTONE_PLACE, SoundCategory.BLOCKS, 1, 1);
		}
		return ActionResult.SUCCESS;
	}

	@Override
	public long getTankSize() {
		return 3 * FluidHooks.BUCKET;
	}
}