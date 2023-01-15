package earth.terrarium.ad_astra.common.screen;

import com.mojang.serialization.DataResult;
import com.teamresourceful.resourcefullib.common.codecs.yabn.YabnOps;
import com.teamresourceful.resourcefullib.common.networking.PacketHelper;
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
        PacketHelper.writeWithYabn(buf, Planet.CODEC.listOf(), PlanetData.planets().stream().toList(), true)
                .get()
                .mapRight(DataResult.PartialResult::message)
                .ifRight(AdAstra.LOGGER::error);
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