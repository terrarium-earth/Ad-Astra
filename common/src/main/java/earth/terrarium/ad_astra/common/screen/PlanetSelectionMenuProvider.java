package earth.terrarium.ad_astra.common.screen;

import com.teamresourceful.resourcefullib.common.codecs.yabn.YabnOps;
import com.teamresourceful.yabn.elements.YabnElement;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.data.Planet;
import earth.terrarium.ad_astra.common.data.PlanetData;
import earth.terrarium.ad_astra.common.screen.menu.PlanetSelectionMenu;
import earth.terrarium.botarium.common.menu.ExtraDataMenuProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jetbrains.annotations.NotNull;

public record PlanetSelectionMenuProvider(int tier) implements ExtraDataMenuProvider {

    @Override
    public void writeExtraData(ServerPlayer player, FriendlyByteBuf buf) {
        buf.writeInt(tier);
        YabnElement element = Planet.CODEC.listOf().encodeStart(YabnOps.COMPRESSED, PlanetData.planets().stream().toList())
                .getOrThrow(false, AdAstra.LOGGER::error);
        buf.writeBytes(element.toData());
    }

    @Override
    public AbstractContainerMenu createMenu(int syncId, @NotNull Inventory inventory, @NotNull Player player) {
        return new PlanetSelectionMenu(syncId, player, tier);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("gui.ad_astra.planet_selection.name");
    }
}