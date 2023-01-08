package earth.terrarium.ad_astra.common.item;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.config.AdAstraConfig;
import earth.terrarium.ad_astra.common.registry.ModItems;
import earth.terrarium.botarium.util.CommonHooks;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import vazkii.patchouli.api.PatchouliAPI;

public class AstroduxItem extends Item {

    // Give guidebook at spawn
    public static void onPlayerJoin(Player player) {
        if (player.level.isClientSide) return;
        if (AdAstraConfig.giveAstroduxAtSpawn) {
            if (((ServerPlayer) player).getStats().getValue(Stats.CUSTOM.get(Stats.TOTAL_WORLD_TIME)) <= 0) {
                player.addItem(ModItems.ASTRODUX.get().getDefaultInstance());
            }
        }
    }

    public AstroduxItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (player instanceof ServerPlayer serverPlayer) {
            if (CommonHooks.isModLoaded("patchouli")) {
                PatchouliAPI.get().openBookGUI(serverPlayer, new ResourceLocation(AdAstra.MOD_ID, "astrodux"));
                return InteractionResultHolder.success(player.getItemInHand(usedHand));
            } else {
                player.displayClientMessage(Component.translatable("info.ad_astra.install_patchouli"), true);
            }
        }
        return InteractionResultHolder.fail(player.getItemInHand(usedHand));
    }
}
