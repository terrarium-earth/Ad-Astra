package earth.terrarium.ad_astra.common.block.machine;

import earth.terrarium.botarium.common.menu.ExtraDataMenuProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public abstract class CookingMachineBlockEntity extends MachineBlockEntity implements BasicContainer, ExtraDataMenuProvider {
    private final NonNullList<ItemStack> items;
    protected int cookTime;
    protected int cookTimeTotal;

    public CookingMachineBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState, int containerSize) {
        super(blockEntityType, blockPos, blockState);
        items = NonNullList.withSize(containerSize, ItemStack.EMPTY);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        ContainerHelper.loadAllItems(tag, this.items);
        this.cookTime = tag.getInt("CookTime");
        this.cookTimeTotal = tag.getInt("CookTimeTotal");
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, this.items);
        tag.putInt("CookTime", this.cookTime);
        tag.putInt("CookTimeTotal", this.cookTimeTotal);
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    public void writeExtraData(ServerPlayer player, FriendlyByteBuf buffer) {
        buffer.writeBlockPos(this.getBlockPos());
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return this.getBlockState().getBlock().getName();
    }
}
