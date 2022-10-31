package earth.terrarium.ad_astra.screen.handler;

import earth.terrarium.ad_astra.blocks.machines.entity.CoalGeneratorBlockEntity;
import earth.terrarium.ad_astra.networking.NetworkHandling;
import earth.terrarium.ad_astra.networking.packets.server.MachineInfoPacket;
import earth.terrarium.ad_astra.registry.ModScreenHandlers;
import java.util.List;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

public class CoalGeneratorScreenHandler extends AbstractMachineScreenHandler<CoalGeneratorBlockEntity> {

    public CoalGeneratorScreenHandler(int syncId, Inventory inventory, FriendlyByteBuf buf) {
        this(syncId, inventory, (CoalGeneratorBlockEntity) inventory.player.level.getBlockEntity(buf.readBlockPos()));

    }

    public CoalGeneratorScreenHandler(int syncId, Inventory inventory, CoalGeneratorBlockEntity entity) {
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