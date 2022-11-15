package earth.terrarium.ad_astra.items;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.registry.ModItems;
import earth.terrarium.ad_astra.util.ModResourceLocation;
import earth.terrarium.ad_astra.util.ModUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import vazkii.patchouli.api.PatchouliAPI;

public class AstroduxItem extends Item {

    // Give guidebook at spawn
    public static void onPlayerJoin(Player player) {
        if (player.level.isClientSide) return;
        if (AdAstra.CONFIG.general.giveAstroduxAtSpawn) {
            if (((ServerPlayer) player).getStats().getValue(Stats.CUSTOM.get(Stats.TOTAL_WORLD_TIME)) <= 0) {
                player.addItem(ModItems.ASTRODUX.get().getDefaultInstance());
            }
        }
    }

    public AstroduxItem(Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player user, InteractionHand hand) {
        if (user instanceof ServerPlayer player) {
            if (ModUtils.modLoaded("patchouli")) {
                PatchouliAPI.get().openBookGUI(player, new ModResourceLocation("astrodux"));
                return InteractionResultHolder.success(user.getItemInHand(hand));
            } else {
                user.displayClientMessage(Component.translatable("info.ad_astra.install_patchouli"), true);
            }
        }
        return InteractionResultHolder.fail(user.getItemInHand(hand));
    }
}
