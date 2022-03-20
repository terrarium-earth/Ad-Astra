package net.mrscauthd.beyond_earth.cable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.mrscauthd.beyond_earth.ModInit;

import java.util.ArrayList;
import java.util.List;

public class EnergyCableTileEntity extends BlockEntity {

    public EnergyCableTileEntity(BlockPos pos, BlockState state) {
        super(ModInit.ENERGY_CABLE.get(), pos, state);
    }

    public void tick() {
        /*for (BlockPos f1 : this.isMachine(level, this.getBlockPos())) {
            level.setBlock(f1, Blocks.BEDROCK.defaultBlockState(), 2);
            System.out.println(f1);
        }*/
    }

    public List<BlockPos> directionList(BlockPos pos) {
        List<BlockPos> dir = new ArrayList<>();

        dir.add(pos.above());
        dir.add(pos.below());
        dir.add(pos.north());
        dir.add(pos.south());
        dir.add(pos.east());
        dir.add(pos.west());

        return dir;
    }

    public List<BlockPos> isMachine(Level level, BlockPos pos) {
        /** Machine List */
        List<BlockPos> list = new ArrayList<>();

        for (BlockPos f1 : this.directionList(pos)) {
            if (level.getBlockState(f1).is(Blocks.DIAMOND_BLOCK)) {
                list.add(f1);
            }
        }

        return list;
    }

    public List<BlockPos> isCable(Level level, BlockPos pos) {

        /** Naigtbor List */
        List<BlockPos> network = new ArrayList<>();

        for (BlockPos f1 : this.directionList(pos)) {
            if (level.getBlockState(f1).is(ModInit.ENERGY_CABLE_BLOCK.get())) {
                network.add(f1);
            }
        }

        /** loop */
        for (BlockPos f2 : network) {
            for (BlockPos f3 : directionList(f2)) {

            }
        }

        return null;
    }
}
