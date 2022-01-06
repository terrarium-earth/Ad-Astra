package net.mrscauthd.beyond_earth.gui.screens.planetselection;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Items;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.network.NetworkEvent;
import net.mrscauthd.beyond_earth.BeyondEarthMod;
import net.mrscauthd.beyond_earth.ModInit;
import net.mrscauthd.beyond_earth.events.Methodes;

import java.util.function.Supplier;

public class PlanetSelectionGui {

	public static class GuiContainerFactory implements IContainerFactory<GuiContainer> {
		public GuiContainer create(int id, Inventory inv, FriendlyByteBuf extraData) {
			return new GuiContainer(id, inv, extraData);
		}
	}

	public static class GuiContainer extends AbstractContainerMenu {
		String rocket;
		Player player;

		public GuiContainer(int id, Inventory inv, FriendlyByteBuf extraData) {
			super(ModInit.PLANET_SELECTION_GUI.get(), id);

			this.rocket = extraData.readUtf();
			this.player = inv.player;
		}

		@Override
		public boolean stillValid(Player player) {
			return !player.isRemoved();
		}
	}

	public static class NetworkMessage {
		private int integer = 0;

		public NetworkMessage() {

		}

		public NetworkMessage(int integer) {
			this.setInteger(integer);
		}

		public NetworkMessage(FriendlyByteBuf buffer) {
			this.setInteger(buffer.readInt());
		}

		public int getInteger() {
			return this.integer;
		}

		public void setInteger(int integer) {
			this.integer = integer;
		}

		public static NetworkMessage decode(FriendlyByteBuf buffer) {
			return new NetworkMessage(buffer);
		}

		public static void encode(NetworkMessage message, FriendlyByteBuf buffer) {
			buffer.writeInt(message.getInteger());
		}

		public static void handle(NetworkMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			//TODO IF THE PLANET DONE CHANGE VENUS TO GLACIO (ID)

			//Teleport Planet Buttons
			if (message.getInteger() == 0) {
				defaultOptions(context.getSender());
				Methodes.teleportButton(context.getSender(), Methodes.overworld, false);
			}
			if (message.getInteger() == 1) {
				defaultOptions(context.getSender());
				Methodes.teleportButton(context.getSender(), Methodes.moon, false);
			}
			if (message.getInteger() == 2) {
				defaultOptions(context.getSender());
				Methodes.teleportButton(context.getSender(), Methodes.mars, false);
			}
			if (message.getInteger() == 3) {
				defaultOptions(context.getSender());
				Methodes.teleportButton(context.getSender(), Methodes.mercury, false);
			}
			if (message.getInteger() == 4) {
				defaultOptions(context.getSender());
				Methodes.teleportButton(context.getSender(), Methodes.venus, false);
			}
			/**Proxima Centauri:*/
			if (message.getInteger() == 5) {
				defaultOptions(context.getSender());
				Methodes.teleportButton(context.getSender(), Methodes.glacio, false);
			}

			//Teleport Orbit Buttons
			if (message.getInteger() == 6) {
				defaultOptions(context.getSender());
				Methodes.teleportButton(context.getSender(), Methodes.overworld_orbit, false);
			}
			if (message.getInteger() == 7) {
				defaultOptions(context.getSender());
				Methodes.teleportButton(context.getSender(), Methodes.moon_orbit, false);
			}
			if (message.getInteger() == 8) {
				defaultOptions(context.getSender());
				Methodes.teleportButton(context.getSender(), Methodes.mars_orbit, false);
			}
			if (message.getInteger() == 9) {
				defaultOptions(context.getSender());
				Methodes.teleportButton(context.getSender(), Methodes.mercury_orbit, false);
			}
			if (message.getInteger() == 10) {
				defaultOptions(context.getSender());
				Methodes.teleportButton(context.getSender(), Methodes.venus_orbit, false);
			}
			/**Proxima Centauri:*/
			if (message.getInteger() == 11) {
				defaultOptions(context.getSender());
				Methodes.teleportButton(context.getSender(), Methodes.glacio_orbit, false);
			}

			//Create Space Station Buttons
			if (message.getInteger() == 12) {
				deleteItems(context.getSender());
				defaultOptions(context.getSender());
				Methodes.teleportButton(context.getSender(), Methodes.overworld_orbit, true);
			}
			if (message.getInteger() == 13) {
				deleteItems(context.getSender());
				defaultOptions(context.getSender());
				Methodes.teleportButton(context.getSender(), Methodes.moon_orbit, true);
			}
			if (message.getInteger() == 14) {
				deleteItems(context.getSender());
				defaultOptions(context.getSender());
				Methodes.teleportButton(context.getSender(), Methodes.mars_orbit, true);
			}
			if (message.getInteger() == 15) {
				deleteItems(context.getSender());
				defaultOptions(context.getSender());
				Methodes.teleportButton(context.getSender(), Methodes.mercury_orbit, true);
			}
			if (message.getInteger() == 16) {
				deleteItems(context.getSender());
				defaultOptions(context.getSender());
				Methodes.teleportButton(context.getSender(), Methodes.venus_orbit, true);
			}
			/**Proxima Centauri:*/
			if (message.getInteger() == 17) {
				deleteItems(context.getSender());
				defaultOptions(context.getSender());
				Methodes.teleportButton(context.getSender(), Methodes.glacio_orbit, true);
			}

			context.setPacketHandled(true);
		}
	}

	public static void defaultOptions(ServerPlayer player) {
		Methodes.holdSpaceMessage(player);
		player.setNoGravity(false);
		player.closeContainer();
	}

	public static void deleteItems(Player player) {
		player.getInventory().clearOrCountMatchingItems(p -> Items.DIAMOND == p.getItem(), 6, player.getInventory());
		player.getInventory().clearOrCountMatchingItems(p -> ModInit.IRON_PLATE.get() == p.getItem(), 12, player.getInventory());
		player.getInventory().clearOrCountMatchingItems(p -> ModInit.STEEL_INGOT.get() == p.getItem(), 16, player.getInventory());
		player.getInventory().clearOrCountMatchingItems(p -> ModInit.DESH_PLATE.get() == p.getItem(), 4, player.getInventory());
	}
}
