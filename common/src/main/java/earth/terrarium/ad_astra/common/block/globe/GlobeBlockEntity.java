package earth.terrarium.ad_astra.common.block.globe;

import earth.terrarium.ad_astra.common.registry.ModBlockEntityTypes;
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
    private float yaw;
    public float prevYaw;

    public GlobeBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntityTypes.GLOBE.get(), blockPos, blockState);
    }

    public void serverTick() {
        if (getBlockState().getValue(GlobeBlock.POWERED) && torque <= 3f) {
            torque = 3f;
        }

        if (torque > 0) {
            torque -= 0.75f;
            prevYaw = yaw;
            yaw -= torque;
        } else if (torque < 0) {
            torque = 0;
        }
    }

    public void rotateGlobe() {
        torque = (float) ((Mth.PI * 15) / (1 + Math.pow(0.00003f, torque)));
        setChanged();
    }

    public float getYaw() {
        return yaw;
    }

    @Override
    public void load(CompoundTag tag) {
        torque = tag.getFloat("Torque");
        yaw = tag.getFloat("Yaw");
        prevYaw = yaw;
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        tag.putFloat("Torque", torque);
        tag.putFloat("Yaw", yaw);
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
