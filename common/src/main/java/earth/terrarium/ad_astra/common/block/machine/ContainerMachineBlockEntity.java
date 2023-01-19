package earth.terrarium.ad_astra.common.block.machine;

import earth.terrarium.botarium.common.menu.ExtraDataMenuProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class ContainerMachineBlockEntity extends MachineBlockEntity implements BasicContainer, ExtraDataMenuProvider {
    private final NonNullList<ItemStack> items;

    public ContainerMachineBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState, int containerSize) {
        super(blockEntityType, blockPos, blockState);
        items = NonNullList.withSize(containerSize, ItemStack.EMPTY);
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    public void writeExtraData(ServerPlayer player, FriendlyByteBuf buffer) {
        buffer.writeBlockPos(getBlockPos());
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public Component getDisplayName() {
        return getBlockState().getBlock().getName();
    }

    public void updateFluidSlots() {
    }
}
