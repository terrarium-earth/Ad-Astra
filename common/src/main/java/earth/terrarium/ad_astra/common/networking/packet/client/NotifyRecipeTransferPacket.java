package earth.terrarium.ad_astra.common.networking.packet.client;

import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.screen.menu.AbstractMachineMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

public record NotifyRecipeTransferPacket(ResourceLocation recipeId) implements Packet<NotifyRecipeTransferPacket> {

    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "notify_recipe_transfer_packet");
    public static final Handler HANDLER = new Handler();

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public PacketHandler<NotifyRecipeTransferPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler implements PacketHandler<NotifyRecipeTransferPacket> {
        @Override
        public void encode(NotifyRecipeTransferPacket packet, FriendlyByteBuf buf) {
            buf.writeResourceLocation(packet.recipeId());
        }

        @Override
        public NotifyRecipeTransferPacket decode(FriendlyByteBuf buf) {
            return new NotifyRecipeTransferPacket(buf.readResourceLocation());
        }

        @Override
        public PacketContext handle(NotifyRecipeTransferPacket packet) {
            return (player, level) -> {
                if (player.containerMenu instanceof AbstractMachineMenu<?> menu) {
                    Recipe<?> recipe = level.getRecipeManager().byKey(packet.recipeId()).get();
                    menu.onRecipeTransfer(recipe);
                }
            };
        }
    }
}
