package earth.terrarium.adastra.common.blockentities.base;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface TickableBlockEntity {

    /**
     * Called every tick on both the client and the server. Should be used for common logic
     * on both sides.
     *
     * @param level The level
     * @param time  The game time, offset by the block's position
     * @param state The block state
     * @param pos   The block position
     */
    default void tick(Level level, long time, BlockState state, BlockPos pos) {}

    /**
     * Called every tick on the server. Should be used for server-side logic.
     *
     * @param level The level
     * @param time  The game time, offset by the block's position
     * @param state The block state
     * @param pos   The block position
     */
    default void serverTick(ServerLevel level, long time, BlockState state, BlockPos pos) {}

    /**
     * Called every tick on the server. Should be used for internal server-side logic.
     *
     * @param level The level
     * @param time  The game time, offset by the block's position
     * @param state The block state
     * @param pos   The block position
     */
    default void internalServerTick(ServerLevel level, long time, BlockState state, BlockPos pos) {}

    /**
     * Called every tick on the client. Should be used for client-side logic.
     *
     * @param level The level
     * @param time  The game time, offset by the block's position
     * @param state The block state
     * @param pos   The block position
     */
    default void clientTick(ClientLevel level, long time, BlockState state, BlockPos pos) {}

    /**
     * Called once, on the block entity's first tick.
     *
     * @param level The level
     * @param pos   The block position
     * @param state The block state
     */
    default void firstTick(Level level, BlockPos pos, BlockState state) {}

    /**
     * Called when the block is destroyed, either through player interaction, explosion or by other means.
     */
    default void onRemoved() {}

    /**
     * Checks if the block entity has ticked at least once.
     *
     * @return True if the block entity has ticked at least once, false otherwise.
     */
    default boolean isInitialized() {
        return true;
    }

    /**
     * Syncs the block entity to the client. Used for updating energy and fluid bars.
     */
    default void sync() {}
}
