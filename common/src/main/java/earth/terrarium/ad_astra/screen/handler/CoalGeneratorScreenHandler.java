package earth.terrarium.ad_astra.screen.handler;

import earth.terrarium.ad_astra.blocks.machines.entity.CoalGeneratorBlockEntity;
import earth.terrarium.ad_astra.networking.NetworkHandling;
import earth.terrarium.ad_astra.networking.packets.server.MachineInfoPacket;
import earth.terrarium.ad_astra.registry.ModScreenHandlers;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.slot.Slot;

import java.util.List;

public class CoalGeneratorScreenHandler extends AbstractMachineScreenHandler<CoalGeneratorBlockEntity> {

    public CoalGeneratorScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
        this(syncId, inventory, (CoalGeneratorBlockEntity) inventory.player.world.getBlockEntity(buf.readBlockPos()));

    }

    public CoalGeneratorScreenHandler(int syncId, PlayerInventory inventory, CoalGeneratorBlockEntity entity) {
        super(ModScreenHandlers.COAL_GENERATOR_SCREEN_HANDLER.get(), syncId, inventory, entity, new Slot[]{new Slot(entity, 0, 80, 40)});
    }

    @Override
    public int getPlayerInventoryOffset() {
        return 11;
    }

    @Override
    public void syncClientScreen() {
        NetworkHandling.CHANNEL.sendToPlayer(new MachineInfoPacket(machine.getEnergyStorage().getStoredEnergy(), List.of()), this.player);
    }
}