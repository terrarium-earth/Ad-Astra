package earth.terrarium.adastra.client.radio.audio;

import earth.terrarium.adastra.common.utils.radio.RadioHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;

public final class StaticRadioSoundInstance extends RadioSoundInstance {

    private final Vec3 source;

    public StaticRadioSoundInstance(String url, RandomSource randomSource, BlockPos source) {
        super(url, randomSource);
        this.source = source.getCenter();
    }

    @Override
    public void tick() {
        super.tick();

        if (Minecraft.getInstance().level == null) return;
        BlockEntity entity = Minecraft.getInstance().level.getBlockEntity(BlockPos.containing(this.source));
        if (!(entity instanceof RadioHolder)) {
            this.stopped = true;
            RadioHandler.stop();
        }
    }

    @Override
    public float getVolume() {
        if (Minecraft.getInstance().player == null) return 0f;
        double range = Minecraft.getInstance().player.position().distanceToSqr(this.source);
        if (range > RadioHolder.RANGE) {
            return 0f;
        }
        float rangePercent = 1f - (float) Math.max((range - (RadioHolder.RANGE - RadioHolder.RANGE_DROPOFF)) / RadioHolder.RANGE_DROPOFF, 0f);
        return super.getVolume() * rangePercent;
    }
}
