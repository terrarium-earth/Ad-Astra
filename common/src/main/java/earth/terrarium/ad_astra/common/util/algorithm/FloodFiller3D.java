package earth.terrarium.ad_astra.common.util.algorithm;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class FloodFiller3D {

    public static Set<BlockPos> run(Level level, BlockPos start) {
        Set<BlockPos> positions = new HashSet<>();
        Set<BlockPos> queue = new LinkedHashSet<>();
        queue.add(start);

        while (!queue.isEmpty()) {
            if (positions.size() >= 2000) break;

            Iterator<BlockPos> iterator = queue.iterator();
            BlockPos pos = iterator.next();
            iterator.remove();

            BlockState state = level.getBlockState(pos);

            if (state.isCollisionShapeFullBlock(level, pos)) continue;

            positions.add(pos);

            for (Direction dir : Direction.values()) {
                BlockPos offsetPos = pos.relative(dir);
                if (!positions.contains(offsetPos)) {
                    queue.add(offsetPos);
                }
            }
        }

        return positions;
    }
}
