package net.mrscauthd.beyond_earth.cable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.energy.CapabilityEnergy;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class EnergyCable extends BaseEntityBlock implements SimpleWaterloggedBlock {

    public static HashMap<BlockPos, List<BlockPos>> CABLENETWORKS = new HashMap<>();
    public static HashMap<BlockPos,List<BlockPos>> CABLES = new HashMap<>();

    public EnergyCable(Properties p_49224_) {
        super(p_49224_);
    }

    public void networkUpdate(BlockPos pos, Level worldIn){
        if(!worldIn.isClientSide) {
            //if(!CABLENETWORKS.containsKey(pos)){
            //    CABLENETWORKS.put(pos,new ArrayList<BlockPos>());
            //}
            /*List<BlockPos> bl = new ArrayList<BlockPos>();
            bl.add(pos.up());
            bl.add(pos.down());
            bl.add(pos.south());
            bl.add(pos.north());
            bl.add(pos.west());
            bl.add(pos.east());*/

            HashMap<BlockPos, List<BlockPos>> map = new HashMap<>();
            HashMap<BlockPos, List<BlockPos>> map2 = new HashMap<>();

            List<BlockPos> blocked = new ArrayList<BlockPos>();
            blocked.add(pos);
            this.generateNetwork(pos, map,map2, blocked, pos,  worldIn);
            //for (BlockPos c : bl) {
            //    if (worldIn.getBlockState(c).getBlock() instanceof EnergyCable) {
            //        ((EnergyCable) worldIn.getBlockState(c).getBlock()).generateNetwork(pos, map, blocked, c, ((ServerWorld) worldIn));
            //    }
            //}

            if (map.get(pos) == null) {
                map.put(pos, new ArrayList<>());
            }

            boolean ALREADY_EXIST = true;

            if (CABLENETWORKS.entrySet().size() == 0) {
                System.out.println("System Added");
                CABLENETWORKS.put(pos, map.get(pos));
                CABLES.put(pos, map2.get(pos));
            } else {
                for (Map.Entry<BlockPos, List<BlockPos>> x : CABLENETWORKS.entrySet()) {
                    ALREADY_EXIST = true;
                    System.out.println("####TASK####");
                    for (BlockPos p : map.get(pos)) {
                        if (!(x.getValue().contains(p))) {
                            ALREADY_EXIST = false;
                            System.out.println("exist");
                            worldIn.setBlock(p, Blocks.DIAMOND_BLOCK.defaultBlockState(), 2);
                        } else {
                            System.out.println("don't exist");
                        }
                    }
                    if (ALREADY_EXIST) {
                        break;
                    }
                }

                if ((!ALREADY_EXIST) && (map.get(pos).size() > 0)) {
                    System.out.println("System Added");
                    CABLENETWORKS.put(pos, map.get(pos));
                    CABLES.put(pos, map2.get(pos));
                } else {
                    System.out.println("already exist");
                }
            }
        }
    }

    @Override
    public void onPlace(BlockState p_60566_, Level p_60567_, BlockPos p_60568_, BlockState p_60569_, boolean p_60570_) {
        super.onPlace(p_60566_, p_60567_, p_60568_, p_60569_, p_60570_);
        System.out.println("x");
        System.out.println("placed");
        this.networkUpdate(p_60568_, p_60567_);
    }

    public void generateNetwork(BlockPos engine, HashMap<BlockPos, List<BlockPos>> machines, HashMap<BlockPos,List<BlockPos>> cable, List<BlockPos> blocked, BlockPos checkedblock, Level world){
        blocked.add(checkedblock);
        /*if(!INPUTS.containsKey(checkedblock)){
            INPUTS.put(checkedblock,new ArrayList<BlockPos>());
        }
        List<BlockPos> bp = INPUTS.get(checkedblock);
        bp.add(engine);
        INPUTS.put(checkedblock,bp);*/

        if(machines.get(engine) == null){
            machines.put(engine,new ArrayList<>());
        }
        if(cable.get(engine) == null){
            cable.put(engine,new ArrayList<>());
        }

        if(world.getBlockState(checkedblock).getBlock() instanceof EnergyCable){
            List<BlockPos> ca = cable.get(engine);
            ca.add(checkedblock);
            cable.put(engine,ca);
        }

        List<BlockPos> bl = new ArrayList<BlockPos>();
        bl.add(checkedblock.above());
        bl.add(checkedblock.below());
        bl.add(checkedblock.south());
        bl.add(checkedblock.north());
        bl.add(checkedblock.west());
        bl.add(checkedblock.east());

        for (BlockPos c : bl) {
            if(!blocked.contains(c)) {
                if (world.getBlockState(c).getBlock() instanceof EnergyCable) {
                    ((EnergyCable) world.getBlockState(c).getBlock()).generateNetwork(engine, machines,cable, blocked, c, world);
                }else {
                    if(this.MachineCanRecieve(world, c)||this.MachineCanExtract(world,c)) {
                        System.out.println("machine"+engine.toString()+" find at: "+c.toString());
                        blocked.add(c);
                        List<BlockPos> pos = machines.get(engine);
                        pos.add(c);
                        machines.put(engine, pos);
                    }

                }
            }
        }

    }

    @Override
    public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);


        if(!worldIn.isClientSide){
            //if(!CABLENETWORKS.containsKey(pos)){
            //    CABLENETWORKS.put(pos,new ArrayList<BlockPos>());
            //}

            //List<BlockPos> BLOCKS = CABLENETWORKS.get(pos);
            //INPUTS.clear();
            //for (BlockPos bp : BLOCKS) {
            //System.out.println("update");
            //if (worldIn.getBlockState(bp).getBlock() instanceof MachineBlock) {
            //((MachineBlock)worldIn.getBlockState(bp).getBlock()).INPUTS.remove(bp);
            try {
                for (Map.Entry<BlockPos,List<BlockPos>>x :CABLENETWORKS.entrySet()) {
                    if(x.getValue().contains(pos)){
                        CABLENETWORKS.remove(x.getKey());
                        CABLES.remove(x.getKey());
                    }
                }
            } catch (ConcurrentModificationException cme){

            }

            this.networkUpdate(pos,worldIn);
            //((MachineBlock)worldIn.getBlockState(bp).getBlock()).networkUpdate(pos,((World)worldIn));
        }
    }


    public boolean MachineCanRecieve(Level world,BlockPos pos){
        return Optional.ofNullable(world.getBlockEntity(pos))
                .flatMap(te -> te.getCapability(CapabilityEnergy.ENERGY).resolve())
                .map(cap -> cap.canReceive())
                .orElse(false);
    }

    public boolean MachineCanExtract(Level world, BlockPos pos){
        return Optional.ofNullable(world.getBlockEntity(pos))
                .flatMap(te -> te.getCapability(CapabilityEnergy.ENERGY).resolve())
                .map(cap -> cap.canExtract())
                .orElse(false);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return new EnergyCableTileEntity(p_153215_, p_153216_);
    }
}
