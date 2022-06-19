package com.github.alexnijjar.beyond_earth.items.vehicles;

import com.github.alexnijjar.beyond_earth.entities.vehicles.RoverEntity;
import com.github.alexnijjar.beyond_earth.registry.ModEntities;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RoverItem extends VehicleItem {
    public static final long TANK_SIZE = 3 * FluidConstants.BUCKET;

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
                        if (!testBlock.isAir() && !(testBlock.getBlock() instanceof FluidBlock)) {
                            return ActionResult.FAIL;
                        }
                    }
                }
            }

            ItemStack roverStack = context.getPlayer().getStackInHand(context.getHand());
            RoverEntity rover = new RoverEntity(ModEntities.ROVER_TIER_1, world);
            NbtCompound nbt = roverStack.getOrCreateNbt();
            if (nbt.contains("Fluid")) {
                this.insertIntoTank(rover.inputTank, roverStack);
            }
            if (nbt.contains("Inventory")) {
                rover.getInventory().readNbtList(nbt.getList("Inventory", NbtElement.COMPOUND_TYPE));
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
        return TANK_SIZE;
    }
}