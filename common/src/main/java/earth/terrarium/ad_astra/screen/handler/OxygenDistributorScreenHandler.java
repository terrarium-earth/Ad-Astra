package earth.terrarium.ad_astra.screen.handler;

import earth.terrarium.ad_astra.blocks.machines.entity.FluidMachineBlockEntity;
import earth.terrarium.ad_astra.blocks.machines.entity.OxygenDistributorBlockEntity;
import earth.terrarium.ad_astra.networking.NetworkHandling;
import earth.terrarium.ad_astra.networking.packets.server.MachineInfoPacket;
import earth.terrarium.ad_astra.registry.ModScreenHandlers;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.slot.Slot;

public class OxygenDistributorScreenHandler extends AbstractMachineScreenHandler {

	public OxygenDistributorScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
		this(syncId, inventory, (OxygenDistributorBlockEntity) inventory.player.world.getBlockEntity(buf.readBlockPos()));
	}

	public OxygenDistributorScreenHandler(int syncId, PlayerInventory inventory, OxygenDistributorBlockEntity entity) {
		super(ModScreenHandlers.OXYGEN_DISTRIBUTOR_SCREEN_HANDLER.get(), syncId, inventory, entity, new Slot[] {

				// Left Insert.
				new Slot(entity, 0, 17, 82),
				// Left Extract.
				new Slot(entity, 1, 17, 112) {
					@Override
					public boolean canInsert(ItemStack stack) {
						return false;
					}
				} });
	}

	@Override
	public int getPlayerInventoryOffset() {
		return 78;
	}

	@Override
	public void syncClientScreen() {
		NetworkHandling.CHANNEL.sendToPlayer(new MachineInfoPacket(blockEntity.getEnergy(), ((FluidMachineBlockEntity)blockEntity).getFluidContainer().getFluids()), this.player);
	}
}