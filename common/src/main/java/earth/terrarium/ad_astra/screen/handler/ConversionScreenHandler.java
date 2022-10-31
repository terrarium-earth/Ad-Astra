package earth.terrarium.ad_astra.screen.handler;

import earth.terrarium.ad_astra.blocks.machines.entity.FluidMachineBlockEntity;
import earth.terrarium.ad_astra.networking.NetworkHandling;
import earth.terrarium.ad_astra.networking.packets.server.MachineInfoPacket;
import earth.terrarium.ad_astra.registry.ModScreenHandlers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ConversionScreenHandler extends AbstractMachineScreenHandler<FluidMachineBlockEntity> {

    public ConversionScreenHandler(int syncId, Inventory inventory, FriendlyByteBuf buf) {
        this(syncId, inventory, (FluidMachineBlockEntity) inventory.player.level.getBlockEntity(buf.readBlockPos()));
    }

    public ConversionScreenHandler(int syncId, Inventory inventory, FluidMachineBlockEntity entity) {
        super(ModScreenHandlers.CONVERSION_SCREEN_HANDLER.get(), syncId, inventory, entity, new Slot[]{

                // Left Insert.
                new Slot(entity, 0, 12, 22),
                // Left Extract.
                new Slot(entity, 1, 12, 52) {
                    @Override
                    public boolean mayPlace(ItemStack stack) {
                        return false;
                    }
                },
                // Right Insert.
                new Slot(entity, 2, 127, 22),
                // Right Extract.
                new Slot(entity, 3, 127, 52) {
                    @Override
                    public boolean mayPlace(ItemStack stack) {
                        return false;
                    }
                }});
    }

    @Override
    public int getPlayerInventoryOffset() {
        return 18;
    }

    @Override
    public void syncClientScreen() {
        NetworkHandling.CHANNEL.sendToPlayer(new MachineInfoPacket(machine.getEnergyStorage().getStoredEnergy(), machine.getFluidContainer().getFluids()), this.player);
    }
}