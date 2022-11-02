package earth.terrarium.ad_astra.blocks.globes;

import earth.terrarium.ad_astra.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class GlobeBlockEntity extends BlockEntity {

    public static final float INERTIA = 0.0075f;
    public static final float DECELERATION = 0.00003f;

    private float angularVelocity = 0.0f;
    private float yaw = 0.0f;
    private float cachedYaw = 0.0f;

    public GlobeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GLOBE_BLOCK_ENTITY.get(), pos, state);
    }

    public void tick() {
        if (this.getBlockState().getValue(GlobeBlock.POWERED) && this.getAngularVelocity() <= 0.05f) {
            this.setAngularVelocity(0.05f);
        }

        if (this.getAngularVelocity() > 0) {
            // Simulate an inertia effect.
            this.setAngularVelocity(this.getAngularVelocity() - INERTIA);

            this.setCachedYaw(this.getYaw());
            this.setYaw(this.getYaw() - this.getAngularVelocity());

        } else if (this.getAngularVelocity() < 0) {
            this.setAngularVelocity(0);
        }
    }

    @Override
    public void setChanged() {
        super.setChanged();

        // Update renderer.
        if (this.level instanceof ServerLevel serverWorld) {
            serverWorld.getChunkSource().blockChanged(this.worldPosition);
        }
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.angularVelocity = nbt.getFloat("AngularVelocity");
        this.yaw = nbt.getFloat("Yaw");
        this.cachedYaw = nbt.getFloat("CachedYaw");
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putFloat("AngularVelocity", this.angularVelocity);
        nbt.putFloat("Yaw", this.yaw);
        nbt.putFloat("CachedYaw", this.cachedYaw);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    public float getAngularVelocity() {
        return this.angularVelocity;
    }

    public void setAngularVelocity(float angularVelocity) {
        this.angularVelocity = angularVelocity;
    }

    public float getYaw() {
        return this.yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getCachedYaw() {
        return this.cachedYaw;
    }

    public void setCachedYaw(float yaw) {
        this.cachedYaw = yaw;
    }
}