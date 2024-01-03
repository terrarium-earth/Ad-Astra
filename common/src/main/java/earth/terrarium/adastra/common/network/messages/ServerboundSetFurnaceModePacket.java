package earth.terrarium.adastra.common.network.messages;

import com.teamresourceful.bytecodecs.base.ByteCodec;
import com.teamresourceful.bytecodecs.base.object.ObjectByteCodec;
import com.teamresourceful.resourcefullib.common.bytecodecs.ExtraByteCodecs;
import com.teamresourceful.resourcefullib.common.networking.base.CodecPacketHandler;
import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.blockentities.machines.EtrionicBlastFurnaceBlockEntity;
import earth.terrarium.adastra.common.utils.ModUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public record ServerboundSetFurnaceModePacket(
    BlockPos machine, EtrionicBlastFurnaceBlockEntity.Mode mode
) implements Packet<ServerboundSetFurnaceModePacket> {

    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "set_furnace_mode");
    public static final Handler HANDLER = new Handler();

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public PacketHandler<ServerboundSetFurnaceModePacket> getHandler() {
        return HANDLER;
    }

    private static class Handler extends CodecPacketHandler<ServerboundSetFurnaceModePacket> {
        public Handler() {
            super(ObjectByteCodec.create(
                ExtraByteCodecs.BLOCK_POS.fieldOf(ServerboundSetFurnaceModePacket::machine),
                ByteCodec.ofEnum(EtrionicBlastFurnaceBlockEntity.Mode.class).fieldOf(ServerboundSetFurnaceModePacket::mode),
                ServerboundSetFurnaceModePacket::new
            ));
        }

        @Override
        public PacketContext handle(ServerboundSetFurnaceModePacket packet) {
            return (player, level) -> ModUtils.getMachineFromMenuPacket(packet.machine(), player, level).ifPresent(
                machine -> {
                    if (machine instanceof EtrionicBlastFurnaceBlockEntity entity) {
                        entity.setMode(packet.mode());
                        entity.clearAlloyingRecipe();
                        entity.update();
                    }
                }
            );
        }
    }
}
