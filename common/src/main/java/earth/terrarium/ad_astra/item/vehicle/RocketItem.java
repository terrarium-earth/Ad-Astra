package earth.terrarium.ad_astra.item.vehicle;

import earth.terrarium.ad_astra.block.door.LocationState;
import earth.terrarium.ad_astra.block.launchpad.LaunchPad;
import earth.terrarium.ad_astra.entity.vehicle.*;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import earth.terrarium.botarium.api.fluid.PlatformFluidItemHandler;
import earth.terrarium.botarium.api.item.ItemStackHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class RocketItem<T extends Rocket> extends VehicleItem {

    private final EntityType<T> rocketEntity;
    private final int tier;

    public RocketItem(EntityType<T> rocketEntity, int tier, Properties settings) {
        super(settings);
        this.rocketEntity = rocketEntity;
        this.tier = tier;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (!level.isClientSide) {
            BlockPos pos = context.getClickedPos();
            BlockState state = level.getBlockState(pos);
            Player player = context.getPlayer();
            if (player == null) return InteractionResult.FAIL;

            // Check if the block can be spawned in a 3x8x3 radius
            for (int x = pos.getX() - 1; x < pos.getX() + 2; x++) {
                for (int y = pos.getY() + 1; y < pos.getY() + 9; y++) {
                    for (int z = pos.getZ() - 1; z < pos.getZ() + 2; z++) {
                        BlockPos testBlockPos = new BlockPos(x, y, z);
                        BlockState testBlock = level.getBlockState(testBlockPos);
                        if (!testBlock.isAir() && !(testBlock.getBlock() instanceof LiquidBlock)) {
                            return InteractionResult.FAIL;
                        }
                    }
                }
            }

            if (state.getBlock() instanceof LaunchPad) {
                if (state.getValue(LaunchPad.LOCATION).equals(LocationState.CENTER)) {
                    ItemStackHolder rocketStack = new ItemStackHolder(player.getItemInHand(context.getHand()));
                    if (rocketStack.getStack().getItem() instanceof RocketItem<?> rocket) {

                        Rocket rocketEntity = null;

                        int tier = rocket.getTier();
                        switch (tier) {
                            case 1 -> rocketEntity = new RocketTier1(rocket.getRocketEntity(), level);
                            case 2 -> rocketEntity = new RocketTier2(rocket.getRocketEntity(), level);
                            case 3 -> rocketEntity = new RocketTier3(rocket.getRocketEntity(), level);
                            case 4 -> rocketEntity = new RocketTier4(rocket.getRocketEntity(), level);
                        }

                        if (rocketEntity != null) {

                            // Check if a rocket is already placed on the pad
                            AABB scanAbove = new AABB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);
                            List<Rocket> entities = level.getEntitiesOfClass(Rocket.class, scanAbove, entity -> true);
                            if (!entities.isEmpty()) {
                                return InteractionResult.PASS;
                            }

                            PlatformFluidItemHandler fluidItemHandler = FluidHooks.getItemFluidManager(rocketStack.getStack());
                            if (fluidItemHandler.extractFluid(rocketStack, fluidItemHandler.getFluidInTank(0), true).getFluidAmount() > 0) {
                                rocketEntity.getTank().insertFluid(fluidItemHandler.extractFluid(rocketStack, fluidItemHandler.getFluidInTank(0), false), false);
                            }
                            CompoundTag nbt = rocketStack.getStack().getOrCreateTag();
                            if (nbt.contains("Inventory")) {
                                rocketEntity.getInventory().fromTag(nbt.getList("Inventory", Tag.TAG_COMPOUND));
                            }

                            rocketEntity.assignLaunchPad(true);
                            level.playSound(null, pos, SoundEvents.NETHERITE_BLOCK_PLACE, SoundSource.BLOCKS, 1, 1);

                            rocketEntity.setPos(pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5);
                            rocketEntity.setYRot(Math.round((player.getYRot() + 180) / 90) * 90);
                            level.addFreshEntity(rocketEntity);
                            if (rocketStack.isDirty()) {
                                player.setItemInHand(context.getHand(), rocketStack.getStack());
                            }
                            player.getItemInHand(context.getHand()).shrink(1);

                            return InteractionResult.SUCCESS;
                        }
                    }
                }
            }
        }
        return InteractionResult.PASS;
    }

    public EntityType<T> getRocketEntity() {
        return this.rocketEntity;
    }

    public int getTier() {
        return tier;
    }

    @Override
    public long getTankSize() {
        return FluidHooks.buckets(3);
    }
}