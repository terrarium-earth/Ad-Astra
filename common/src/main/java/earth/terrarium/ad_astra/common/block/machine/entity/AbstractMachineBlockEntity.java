package earth.terrarium.ad_astra.common.block.machine.entity;

import earth.terrarium.ad_astra.common.block.machine.AbstractMachineBlock;
import earth.terrarium.ad_astra.common.util.ModInventory;
import earth.terrarium.botarium.api.menu.ExtraDataMenuProvider;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@MethodsReturnNonnullByDefault
public abstract class AbstractMachineBlockEntity extends BlockEntity implements ExtraDataMenuProvider, ModInventory, WorldlyContainer {

    private final NonNullList<ItemStack> inventory;

    public AbstractMachineBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
        inventory = NonNullList.withSize(getInventorySize(), ItemStack.EMPTY);
    }

    public abstract void tick();

    @Override
    public AbstractContainerMenu createMenu(int syncId, @NotNull Inventory inv, @NotNull Player player) {
        return null;
    }

    public int getInventorySize() {
        return 0;
    }

    public void setActive(boolean active) {
        if (this.getBlockState().hasProperty(AbstractMachineBlock.LIT)) {
            if (this.getLevel() != null) {
                this.getLevel().setBlockAndUpdate(this.getBlockPos(), this.getBlockState().setValue(AbstractMachineBlock.LIT, active));
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return getBlockState().getBlock().getName();
    }

    @Override
    public void writeExtraData(ServerPlayer player, FriendlyByteBuf buf) {
        buf.writeBlockPos(this.getBlockPos());
    }

    @Override
    public void load(CompoundTag nbt) {
        if (getInventorySize() > 0) {
            ContainerHelper.loadAllItems(nbt, this.inventory);
        }
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {
        if (getInventorySize() > 0) {
            ContainerHelper.saveAllItems(nbt, this.inventory);
        }
    }

    // Updates the chunk every time the energy is changed. Important for updating
    // the screen to show the latest energy value.
    @Override
    public void setChanged() {
        super.setChanged();

        if (this.level instanceof ServerLevel serverWorld) {
            serverWorld.getChunkSource().blockChanged(this.worldPosition);
        }
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        int[] result = new int[getItems().size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = i;
        }
        return result;
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack stack, Direction dir) {
        ItemStack slotStack = this.getItem(slot);
        return slotStack.isEmpty() || (slotStack.is(stack.getItem()) && slotStack.getCount() <= slotStack.getMaxStackSize());
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction dir) {
        return true;
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return inventory;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }
}