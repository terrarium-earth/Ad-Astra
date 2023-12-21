package earth.terrarium.adastra.common.blockentities;

import earth.terrarium.adastra.common.blocks.GlobeBlock;
import earth.terrarium.adastra.common.registry.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class GlobeBlockEntity extends BlockEntity {
    private float torque;
    private float yRot;
    private float lastYRot;

    public GlobeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.GLOBE.get(), pos, state);
    }

    public void tick() {
        if (getBlockState().getValue(GlobeBlock.POWERED) && torque <= 3f) {
            torque = 3f;
        }

        if (torque > 0) {
            torque -= 0.75f;
            lastYRot = yRot;
            yRot -= torque;
        } else if (torque < 0) {
            torque = 0;
        }
    }

    public void rotateGlobe() {
        torque = (float) ((Mth.PI * 15) / (1 + Math.pow(0.00003f, torque)));
        setChanged();
    }

    public float yRot() {
        return yRot;
    }

    public float lastYRot() {
        return lastYRot;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        torque = tag.getFloat("Torque");
        yRot = tag.getFloat("YRot");
        lastYRot = yRot;
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putFloat("Torque", torque);
        tag.putFloat("YRot", yRot);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }
}
