package earth.terrarium.ad_astra.items;

import dev.architectury.event.events.common.PlayerEvent;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.registry.ModItems;
import earth.terrarium.ad_astra.util.ModUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class AstroduxItem extends Item {

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
}
