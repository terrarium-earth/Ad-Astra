package earth.terrarium.ad_astra.common.item.vehicle;

import earth.terrarium.ad_astra.common.entity.vehicle.Rover;
import earth.terrarium.ad_astra.common.registry.ModEntityTypes;
import earth.terrarium.botarium.common.fluid.base.PlatformFluidItemHandler;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import earth.terrarium.botarium.common.item.ItemStackHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class RoverItem extends VehicleItem {

    public RoverItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (!level.isClientSide) {
            BlockPos pos = context.getClickedPos();
            Player player = context.getPlayer();
            if (player == null) return InteractionResult.FAIL;

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

            ItemStackHolder roverStack = new ItemStackHolder(player.getItemInHand(context.getHand()));
            Rover rover = new Rover(ModEntityTypes.TIER_1_ROVER.get(), level);

            // Prevent placing rovers in rovers
            AABB scanAbove = new AABB(pos.getX() - 2, pos.getY() - 2, pos.getZ() - 2, pos.getX() + 2, pos.getY() + 2, pos.getZ() + 2);
            List<Rover> entities = level.getEntitiesOfClass(Rover.class, scanAbove, entity -> true);
            if (!entities.isEmpty()) {
                return InteractionResult.PASS;
            }

            PlatformFluidItemHandler fluidItemHandler = FluidHooks.getItemFluidManager(roverStack.getStack());
            if (fluidItemHandler.extractFluid(roverStack, fluidItemHandler.getFluidInTank(0), true).getFluidAmount() > 0) {
                rover.getTank().insertFluid(fluidItemHandler.extractFluid(roverStack, fluidItemHandler.getFluidInTank(0), false), false);
            }
            CompoundTag nbt = roverStack.getStack().getOrCreateTag();
            if (nbt.contains("Inventory")) {
                rover.getInventory().fromTag(nbt.getList("Inventory", Tag.TAG_COMPOUND));
            }
            rover.setYRot(player.getYRot());
            rover.setPos(context.getClickLocation().add(0, 0, 1));
            level.addFreshEntity(rover);
            level.playSound(null, pos, SoundEvents.LODESTONE_PLACE, SoundSource.BLOCKS, 1, 1);
            if (roverStack.isDirty()) {
                player.setItemInHand(context.getHand(), roverStack.getStack());
            }
            player.getItemInHand(context.getHand()).shrink(1);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public long getTankSize() {
        return FluidHooks.buckets(3);
    }
}