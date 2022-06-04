package com.github.alexnijjar.beyond_earth.items.vehicles;

import java.util.List;

import com.github.alexnijjar.beyond_earth.blocks.launch_pad.RocketLaunchPad;
import com.github.alexnijjar.beyond_earth.entities.vehicles.RocketEntity;
import com.github.alexnijjar.beyond_earth.entities.vehicles.RocketEntityTier1;
import com.github.alexnijjar.beyond_earth.entities.vehicles.RocketEntityTier2;
import com.github.alexnijjar.beyond_earth.entities.vehicles.RocketEntityTier3;
import com.github.alexnijjar.beyond_earth.entities.vehicles.RocketEntityTier4;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class RocketItem<T extends RocketEntity> extends VehicleItem {
    public static final int MAX_FUEL = 1000;

    private EntityType<T> rocketEntity;
    private int tier;

    public RocketItem(EntityType<T> rocketEntity, int tier, Settings settings) {
        super(settings);
        this.rocketEntity = rocketEntity;
        this.tier = tier;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (!world.isClient) {
            BlockPos pos = context.getBlockPos();
            BlockState state = world.getBlockState(pos);
            PlayerEntity player = context.getPlayer();

            if (state.getBlock() instanceof RocketLaunchPad pad) {
                if (state.get(RocketLaunchPad.STAGE).equals(true)) {
                    ItemStack currentStack = player.getStackInHand(context.getHand());
                    if (currentStack.getItem() instanceof RocketItem<?> rocket) {

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

                            // Check if a rocket is already placed on the pad.
                            Box scanAbove = new Box(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);
                            List<RocketEntity> entities = ((ServerWorld) world).getEntitiesByClass(RocketEntity.class, scanAbove, entity -> true);
                            if (!entities.isEmpty()) {
                                return ActionResult.PASS;
                            }

                            rocketEntity.assignLaunchPad(true);
                            currentStack.decrement(1);
                            world.playSound(null, pos, SoundEvents.BLOCK_NETHERITE_BLOCK_PLACE, SoundCategory.BLOCKS, 1, 1);

                            rocketEntity.setPosition(pos.getX() + 0.5f, pos.getY(), pos.getZ() + 0.5f);
                            rocketEntity.setYaw(Math.round((player.getYaw() + 180) / 90) * 90);
                            world.spawnEntity(rocketEntity);

                            return ActionResult.SUCCESS;
                        }
                    }
                }
            }
        }
        return ActionResult.SUCCESS;
    }

    public EntityType<T> getRocketEntity() {
        return this.rocketEntity;
    }

    public int getTier() {
        return tier;
    }

    @Override
    public int getMaxFuel() {
        return MAX_FUEL;
    }
}