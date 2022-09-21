package com.github.alexnijjar.ad_astra.items;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.registry.ModItems;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;
import com.github.alexnijjar.ad_astra.util.ModUtils;

import dev.architectury.event.events.common.PlayerEvent;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import vazkii.patchouli.api.PatchouliAPI;

public class AstroduxItem extends Item {

	public AstroduxItem(Settings settings) {
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		if (user instanceof ServerPlayerEntity player) {
			if (ModUtils.modLoaded("patchouli")) {
				// TODO: Patchouli
				// PatchouliAPI.get().openBookGUI(player, new ModIdentifier("astrodux"));
				return TypedActionResult.success(user.getStackInHand(hand));
			} else {
				user.sendMessage(Text.translatable("info.ad_astra.install_patchouli"), true);
			}
		}
		return TypedActionResult.fail(user.getStackInHand(hand));
	}

	// Give guidebook at spawn
	static {
		PlayerEvent.PLAYER_JOIN.register((player) -> {
			if (AdAstra.CONFIG.general.giveAstroduxAtSpawn) {
				if (player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.TOTAL_WORLD_TIME)) <= 0) {
					player.giveItemStack(ModItems.ASTRODUX.get().getDefaultStack());
				}
			}
		});
	}
}
