package net.mrscauthd.boss_tools.gui.screens.planetselection;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.IContainerFactory;
import net.minecraftforge.fml.network.NetworkEvent;
import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.events.Methodes;

import java.util.function.Supplier;

public class PlanetSelectionGui {

	public static class GuiContainerFactory implements IContainerFactory<GuiContainer> {
		public GuiContainer create(int id, PlayerInventory inv, PacketBuffer extraData) {
			return new GuiContainer(id, inv, extraData);
		}
	}

	public static class GuiContainer extends Container {
		String rocket;

		public GuiContainer(int id, Inventory inv, FriendlyByteBuf extraData) {
			super(ModInnet.PLANET_SELECTION_GUI.get(), id);

			this.rocket = extraData.readString();
		}


		@Override
		public boolean canInteractWith(PlayerEntity player) {
			return !player.removed;
		}
	}

	public static class NetworkMessage {
		private int integer = 0;

		public NetworkMessage() {

		}

		public NetworkMessage(int integer) {
			this.setInteger(integer);
		}

		public NetworkMessage(PacketBuffer buffer) {
			this.setInteger(buffer.readInt());
		}

		public int getInteger() {
			return this.integer;
		}

		public void setInteger(int integer) {
			this.integer = integer;
		}

		public static NetworkMessage decode(PacketBuffer buffer) {
			return new NetworkMessage(buffer);
		}

		public static void encode(NetworkMessage message, PacketBuffer buffer) {
			buffer.writeInt(message.getInteger());
		}

		public static void handle(NetworkMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();

			//Teleport Planet Buttons
			if (message.getInteger() == 0) {
				defaultOptions(context.getSender());
				Methodes.teleportButton(context.getSender(), new ResourceLocation("minecraft:overworld"), false);
			}
			if (message.getInteger() == 1) {
				defaultOptions(context.getSender());
				Methodes.teleportButton(context.getSender(), new ResourceLocation("boss_tools:moon"), false);
			}
			if (message.getInteger() == 2) {
				defaultOptions(context.getSender());
				Methodes.teleportButton(context.getSender(), new ResourceLocation("boss_tools:mars"), false);
			}
			if (message.getInteger() == 3) {
				defaultOptions(context.getSender());
				Methodes.teleportButton(context.getSender(), new ResourceLocation("boss_tools:mercury"), false);
			}
			if (message.getInteger() == 4) {
				defaultOptions(context.getSender());
				Methodes.teleportButton(context.getSender(), new ResourceLocation("boss_tools:venus"), false);
			}

			//Teleport Orbit Buttons
			if (message.getInteger() == 5) {
				defaultOptions(context.getSender());
				Methodes.teleportButton(context.getSender(), new ResourceLocation("boss_tools:overworld_orbit"), false);
			}
			if (message.getInteger() == 6) {
				defaultOptions(context.getSender());
				Methodes.teleportButton(context.getSender(), new ResourceLocation("boss_tools:moon_orbit"), false);
			}
			if (message.getInteger() == 7) {
				defaultOptions(context.getSender());
				Methodes.teleportButton(context.getSender(), new ResourceLocation("boss_tools:mars_orbit"), false);
			}
			if (message.getInteger() == 8) {
				defaultOptions(context.getSender());
				Methodes.teleportButton(context.getSender(), new ResourceLocation("boss_tools:mercury_orbit"), false);
			}
			if (message.getInteger() == 9) {
				defaultOptions(context.getSender());
				Methodes.teleportButton(context.getSender(), new ResourceLocation("boss_tools:venus_orbit"), false);
			}

			//Create Space Station Buttons
			if (message.getInteger() == 10) {
				deleteItems(context.getSender());
				defaultOptions(context.getSender());
				Methodes.teleportButton(context.getSender(), new ResourceLocation("boss_tools:overworld_orbit"), true);
			}
			if (message.getInteger() == 11) {
				deleteItems(context.getSender());
				defaultOptions(context.getSender());
				Methodes.teleportButton(context.getSender(), new ResourceLocation("boss_tools:moon_orbit"), true);
			}
			if (message.getInteger() == 12) {
				deleteItems(context.getSender());
				defaultOptions(context.getSender());
				Methodes.teleportButton(context.getSender(), new ResourceLocation("boss_tools:mars_orbit"), true);
			}
			if (message.getInteger() == 13) {
				deleteItems(context.getSender());
				defaultOptions(context.getSender());
				Methodes.teleportButton(context.getSender(), new ResourceLocation("boss_tools:mercury_orbit"), true);
			}
			if (message.getInteger() == 14) {
				deleteItems(context.getSender());
				defaultOptions(context.getSender());
				Methodes.teleportButton(context.getSender(), new ResourceLocation("boss_tools:venus_orbit"), true);
			}

			context.setPacketHandled(true);
		}
	}

	public static void defaultOptions(PlayerEntity player) {
		player.setNoGravity(false);
		player.closeScreen();
	}

	public static void deleteItems(PlayerEntity player) {
		player.inventory.func_234564_a_(p -> Items.DIAMOND == p.getItem(), 6, player.inventory);
		player.inventory.func_234564_a_(p -> ModInnet.IRON_PLATE.get() == p.getItem(), 12, player.inventory);
		player.inventory.func_234564_a_(p -> ModInnet.STEEL_INGOT.get() == p.getItem(), 16, player.inventory);
		player.inventory.func_234564_a_(p -> ModInnet.DESH_PLATE.get() == p.getItem(), 4, player.inventory);
	}
}
