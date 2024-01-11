/* MIT License
 *
 * Copyright (c) 2023 Terrarium Earth
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Methods below are taken from Mekanism and modified to fit Resourceful Bees -> https://github.com/mekanism/Mekanism
 * This class was taken from Resourceful Bees and modified to fit Ad Astra. The above is also true
 */

package earth.terrarium.adastra.common.utils;

import com.teamresourceful.resourcefullib.common.exceptions.UtilityClassException;
import com.teamresourceful.resourcefullib.common.lib.Constants;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public final class WorldUtils {

    private WorldUtils() throws UtilityClassException {
        throw new UtilityClassException();
    }

    @Nullable
    @Contract("_, null, _, _ -> null")
    public static <A extends BlockEntity> A getTileEntity(Class<A> clazz, @Nullable Level reader, BlockPos pos, boolean logWrongType) {
        BlockEntity tile = getTileEntity(reader, pos);
        if (tile == null) {
            return null;
        } else if (clazz.isInstance(tile)) {
            return clazz.cast(tile);
        } else {
            if (logWrongType) {
                Constants.LOGGER.warn("Unexpected TileEntity class at {}, expected {}, but found: {}", pos, clazz, tile.getClass());
            }

            return null;
        }
    }

    @Nullable
    @Contract("null, _ -> null")
    public static BlockEntity getTileEntity(@Nullable Level world, BlockPos pos) {
        return !isBlockLoaded(world, pos) ? null : world.getBlockEntity(pos);
    }

    @Contract("null, _ -> false")
    public static boolean isBlockLoaded(@Nullable Level world, BlockPos pos) {
        return world != null && world.isInWorldBounds(pos) && world.isLoaded(pos);
    }

    @Nullable
    @Contract("_, null, _ -> null")
    public static <T extends BlockEntity> T getTileEntity(Class<T> clazz, @Nullable Level world, BlockPos pos) {
        return getTileEntity(clazz, world, pos, false);
    }
}
