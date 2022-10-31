package earth.terrarium.ad_astra.items.vehicles;

import earth.terrarium.ad_astra.blocks.door.LocationState;
import earth.terrarium.ad_astra.blocks.launchpad.LaunchPad;
import earth.terrarium.ad_astra.entities.vehicles.*;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;

public class RocketItem<T extends RocketEntity> extends VehicleItem {

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
                    ItemStack rocketStack = player.getItemInHand(context.getHand());
                    if (rocketStack.getItem() instanceof RocketItem<?> rocket) {

                        RocketEntity rocketEntity = null;

                        int tier = rocket.getTier();
                        switch (tier) {
                            case 1 -> rocketEntity = new RocketEntityTier1(rocket.getRocketEntity(), level);
                            case 2 -> rocketEntity = new RocketEntityTier2(rocket.getRocketEntity(), level);
                            case 3 -> rocketEntity = new RocketEntityTier3(rocket.getRocketEntity(), level);
                            case 4 -> rocketEntity = new RocketEntityTier4(rocket.getRocketEntity(), level);
                        }

                        if (rocketEntity != null) {

                            // Check if a rocket is already placed on the pad
                            AABB scanAbove = new AABB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);
                            List<RocketEntity> entities = level.getEntitiesOfClass(RocketEntity.class, scanAbove, entity -> true);
                            if (!entities.isEmpty()) {
                                return InteractionResult.PASS;
                            }

                            CompoundTag nbt = rocketStack.getOrCreateTag();
                            if (nbt.contains("fluid")) {
                                if (!this.getFluid(rocketStack).equals(Fluids.EMPTY)) {
                                    this.insert(rocketStack, rocketEntity.getTankHolder());
                                }
                            }
                            if (nbt.contains("inventory")) {
                                rocketEntity.getInventory().fromTag(nbt.getList("inventory", Tag.TAG_COMPOUND));
                            }

                            rocketEntity.assignLaunchPad(true);
                            rocketStack.shrink(1);
                            level.playSound(null, pos, SoundEvents.NETHERITE_BLOCK_PLACE, SoundSource.BLOCKS, 1, 1);

                            rocketEntity.setPos(pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5);
                            rocketEntity.setYRot(Math.round((player.getYRot() + 180) / 90) * 90);
                            level.addFreshEntity(rocketEntity);

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