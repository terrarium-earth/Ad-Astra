package net.mrscauthd.beyond_earth.gui.screens.planetselection;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.network.NetworkEvent;
import net.mrscauthd.beyond_earth.ModInit;
import net.mrscauthd.beyond_earth.crafting.IngredientStack;
import net.mrscauthd.beyond_earth.crafting.SpaceStationRecipe;
import net.mrscauthd.beyond_earth.events.Methods;

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
			return !player.isDeadOrDying();
		}
	}

	public static class NetworkHandler {
		private int integer = 0;

		public NetworkHandler(int integer) {
			this.setInteger(integer);
		}

		public NetworkHandler(FriendlyByteBuf buffer) {
			this.setInteger(buffer.readInt());
		}

		public int getInteger() {
			return this.integer;
		}

		public void setInteger(int integer) {
			this.integer = integer;
		}

		public static NetworkHandler decode(FriendlyByteBuf buffer) {
			return new NetworkHandler(buffer);
		}

		public static void encode(NetworkHandler message, FriendlyByteBuf buffer) {
			buffer.writeInt(message.getInteger());
		}

		public static void handle(NetworkHandler message, Supplier<NetworkEvent.Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			ServerPlayer player = context.getSender();

			// Teleport Planet Buttons
			if (message.getInteger() == 0) {
				defaultOptions(player);
				Methods.teleportButton(player, Methods.overworld, false);
			}
			if (message.getInteger() == 1) {
				defaultOptions(player);
				Methods.teleportButton(player, Methods.moon, false);
			}
			if (message.getInteger() == 2) {
				defaultOptions(player);
				Methods.teleportButton(player, Methods.mars, false);
			}
			if (message.getInteger() == 3) {
				defaultOptions(player);
				Methods.teleportButton(player, Methods.mercury, false);
			}
			if (message.getInteger() == 4) {
				defaultOptions(player);
				Methods.teleportButton(player, Methods.venus, false);
			}
			/** Proxima Centauri: */
			if (message.getInteger() == 5) {
				defaultOptions(player);
				Methods.teleportButton(player, Methods.glacio, false);
			}

			// Teleport Orbit Buttons
			if (message.getInteger() == 6) {
				defaultOptions(player);
				Methods.teleportButton(player, Methods.earth_orbit, false);
			}
			if (message.getInteger() == 7) {
				defaultOptions(player);
				Methods.teleportButton(player, Methods.moon_orbit, false);
			}
			if (message.getInteger() == 8) {
				defaultOptions(player);
				Methods.teleportButton(player, Methods.mars_orbit, false);
			}
			if (message.getInteger() == 9) {
				defaultOptions(player);
				Methods.teleportButton(player, Methods.mercury_orbit, false);
			}
			if (message.getInteger() == 10) {
				defaultOptions(player);
				Methods.teleportButton(player, Methods.venus_orbit, false);
			}
			/** Proxima Centauri: */
			if (message.getInteger() == 11) {
				defaultOptions(player);
				Methods.teleportButton(player, Methods.glacio_orbit, false);
			}

			// Create Space Station Buttons
			if (message.getInteger() == 12) {
				defaultOptions(player);
				deleteItems(player);
				Methods.teleportButton(player, Methods.earth_orbit, true);
			}
			if (message.getInteger() == 13) {
				defaultOptions(player);
				deleteItems(player);
				Methods.teleportButton(player, Methods.moon_orbit, true);
			}
			if (message.getInteger() == 14) {
				defaultOptions(player);
				deleteItems(player);
				Methods.teleportButton(player, Methods.mars_orbit, true);
			}
			if (message.getInteger() == 15) {
				defaultOptions(player);
				deleteItems(player);
				Methods.teleportButton(player, Methods.mercury_orbit, true);
			}
			if (message.getInteger() == 16) {
				defaultOptions(player);
				deleteItems(player);
				Methods.teleportButton(player, Methods.venus_orbit, true);
			}
			/** Proxima Centauri: */
			if (message.getInteger() == 17) {
				defaultOptions(player);
				deleteItems(player);
				Methods.teleportButton(player, Methods.glacio_orbit, true);
			}

			context.setPacketHandled(true);
		}
	}

	public static void defaultOptions(Player player) {
		player.setNoGravity(false);
		Methods.holdSpaceMessage(player);
		player.closeContainer();
	}

	public static void deleteItems(Player player) {
		if (player.getAbilities().instabuild || player.isSpectator()) {
			return;
		}

		Inventory inv = player.getInventory();
		SpaceStationRecipe recipe = (SpaceStationRecipe) player.level.getRecipeManager().byKey(SpaceStationRecipe.KEY).orElse(null);
		
		for (IngredientStack ingredientStack : recipe.getIngredientStacks()) {
			inv.clearOrCountMatchingItems(ingredientStack::testWithoutCount, ingredientStack.getCount(), inv);
		}
	}
}
