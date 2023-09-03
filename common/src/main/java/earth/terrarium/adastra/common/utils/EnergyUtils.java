package earth.terrarium.adastra.common.utils;

import com.mojang.datafixers.util.Pair;
import earth.terrarium.botarium.common.energy.EnergyApi;
import earth.terrarium.botarium.common.energy.base.EnergyContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class EnergyUtils {

    public static long distributeEnergyNearby(BlockEntity energyBlock, long amount, Predicate<Pair<Direction, BlockEntity>> filter) {
        BlockPos blockPos = energyBlock.getBlockPos();
        Level level = energyBlock.getLevel();
        if (level == null) return 0;
        EnergyContainer internalEnergy = EnergyApi.getBlockEnergyContainer(energyBlock, null);
        long amountToDistribute = internalEnergy.extractEnergy(amount, true);
        if (amountToDistribute == 0) return 0;
        List<EnergyContainer> list = Direction.stream()
            .map(direction -> Pair.of(direction, level.getBlockEntity(blockPos.relative(direction))))
            .filter(pair -> pair.getSecond() != null)
            .filter(pair -> EnergyApi.isEnergyBlock(pair.getSecond(), pair.getFirst().getOpposite()))
            .filter(filter)
            .map(pair -> EnergyApi.getBlockEnergyContainer(pair.getSecond(), pair.getFirst()))
            .sorted(Comparator.comparingLong(energy -> energy.insertEnergy(amount, true)))
            .toList();
        int receiverCount = list.size();
        for (EnergyContainer energy : list) {
            if (energy == null) continue;
            long inserted = EnergyApi.moveEnergy(internalEnergy, energy, amountToDistribute / receiverCount, false);
            amountToDistribute -= inserted;
            receiverCount--;
        }
        return amount - amountToDistribute;
    }
}
