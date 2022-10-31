package earth.terrarium.ad_astra.items.vehicles;

import earth.terrarium.ad_astra.entities.vehicles.RoverEntity;
import earth.terrarium.ad_astra.registry.ModEntityTypes;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;

public class RoverItem extends VehicleItem {

    public RoverItem(Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (!level.isClientSide) {
            BlockPos pos = context.getClickedPos();

            // Check if the block can be spawned in a 3x3x3 radius.
            for (int x = pos.getX() - 1; x < pos.getX() + 2; x++) {
                for (int y = pos.getY() + 1; y < pos.getY() + 4; y++) {
                    for (int z = pos.getZ() - 1; z < pos.getZ() + 2; z++) {
                        BlockPos testBlockPos = new BlockPos(x, y, z);
                        BlockState testBlock = level.getBlockState(testBlockPos);
                        if (testBlock.isCollisionShapeFullBlock(level, testBlockPos) && !(testBlock.getBlock() instanceof LiquidBlock)) {
                            return InteractionResult.FAIL;
                        }
                    }
                }
            }

            ItemStack roverStack = context.getPlayer().getItemInHand(context.getHand());
            RoverEntity rover = new RoverEntity(ModEntityTypes.TIER_1_ROVER.get(), level);

            // Prevent placing rovers in rovers
            AABB scanAbove = new AABB(pos.getX() - 2, pos.getY() - 2, pos.getZ() - 2, pos.getX() + 2, pos.getY() + 2, pos.getZ() + 2);
            List<RoverEntity> entities = level.getEntitiesOfClass(RoverEntity.class, scanAbove, entity -> true);
            if (!entities.isEmpty()) {
                return InteractionResult.PASS;
            }

            CompoundTag nbt = roverStack.getOrCreateTag();
            if (nbt.contains("fluid")) {
                if (!this.getFluid(roverStack).equals(Fluids.EMPTY)) {
                    this.insert(roverStack, rover.getTankHolder());
                }
            }
            if (nbt.contains("inventory")) {
                rover.getInventory().fromTag(nbt.getList("inventory", Tag.TAG_COMPOUND));
            }
            rover.setYRot(context.getPlayer().getYRot());
            rover.setPos(context.getClickLocation().add(0, 0, 1));
            level.addFreshEntity(rover);
            roverStack.shrink(1);
            level.playSound(null, pos, SoundEvents.LODESTONE_PLACE, SoundSource.BLOCKS, 1, 1);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public long getTankSize() {
        return FluidHooks.buckets(3);
    }
}