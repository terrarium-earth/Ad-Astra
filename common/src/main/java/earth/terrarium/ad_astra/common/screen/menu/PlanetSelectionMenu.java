package earth.terrarium.ad_astra.common.screen.menu;

import com.teamresourceful.resourcefullib.common.codecs.yabn.YabnOps;
import com.teamresourceful.yabn.YabnParser;
import com.teamresourceful.yabn.reader.ByteReader;
import earth.terrarium.ad_astra.common.data.Planet;
import earth.terrarium.ad_astra.common.data.PlanetData;
import earth.terrarium.ad_astra.common.registry.ModMenus;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PlanetSelectionMenu extends AbstractContainerMenu {

    private final Player player;
    private final int tier;

    public PlanetSelectionMenu(int syncId, Player player, FriendlyByteBuf buf) {
        this(syncId, player, buf.readInt());
        try {
            PlanetData.updatePlanets(Planet.CODEC.listOf().parse(YabnOps.COMPRESSED, YabnParser.parse(new ByteBufByteReader(buf)))
                    .result()
                    .orElse(List.of()));
        } catch (Exception e) {
            e.printStackTrace();
            PlanetData.updatePlanets(List.of());
        }
    }

    public PlanetSelectionMenu(int syncId, Player player, int tier) {
        super(ModMenus.PLANET_SELECTION_SCREEN_HANDLER.get(), syncId);
        this.tier = tier;
        this.player = player;
    }

    public int getTier() {
        return tier;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public boolean stillValid(Player player) {
        return !player.isDeadOrDying();
    }

    @Override
    public @NotNull ItemStack quickMoveStack(Player player, int index) {
        return null;
    }

    private record ByteBufByteReader(ByteBuf buf) implements ByteReader {

        @Override
        public byte peek() {
            return buf.getByte(buf.readerIndex());
        }

        @Override
        public void advance() {
            buf.skipBytes(1);
        }

        @Override
        public byte readByte() {
            return buf.readByte();
        }
    }
}