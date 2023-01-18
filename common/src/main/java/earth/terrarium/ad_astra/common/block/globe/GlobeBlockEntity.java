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
import org.jetbrains.annotations.NotNull;

public class GlobeBlockEntity extends BlockEntity {
    private float torque;
    private float yaw;
    public float prevYaw;

    public GlobeBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntityTypes.GLOBE.get(), blockPos, blockState);
    }

    public void serverTick() {
        if (this.getBlockState().getValue(GlobeBlock.POWERED) && this.torque <= 3f) {
            this.torque = 3f;
        }

        if (this.torque > 0) {
            this.torque -= 0.75f;
            this.prevYaw = this.yaw;
            this.yaw -= this.torque;
        } else if (this.torque < 0) {
            this.torque = 0;
        }
    }

    public void rotateGlobe() {
        this.torque = (float) ((Mth.PI * 15) / (1 + Math.pow(0.00003f, this.torque)));
        this.setChanged();
    }

    public float getYaw() {
        return this.yaw;
    }

    @Override
    public void load(CompoundTag tag) {
        this.torque = tag.getFloat("Torque");
        this.yaw = tag.getFloat("Yaw");
        this.prevYaw = this.yaw;
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        tag.putFloat("Torque", this.torque);
        tag.putFloat("Yaw", this.yaw);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }
}
