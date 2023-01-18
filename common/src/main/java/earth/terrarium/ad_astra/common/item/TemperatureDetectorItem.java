package earth.terrarium.ad_astra.common.item;

import earth.terrarium.ad_astra.common.block.machine.entity.TemperatureRegulatorBlockEntity;
import earth.terrarium.ad_astra.common.system.TemperatureSystem;
import earth.terrarium.ad_astra.common.util.LangUtils;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class TemperatureDetectorItem extends Item {
    public TemperatureDetectorItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (!level.isClientSide) {
            List<TemperatureRegulatorBlockEntity> blockEntities = new ArrayList<>();
            for (BlockPos pos : TemperatureSystem.TEMPERATURE_REGULATOR_BLOCKS) {
                BlockEntity blockEntity = level.getBlockEntity(pos);
                if (level.hasChunkAt(pos) && blockEntity instanceof TemperatureRegulatorBlockEntity entity) {
                    blockEntities.add(entity);
                }
            }
            blockEntities.sort((o1, o2) -> {
                if (o1 == null || o2 == null) {
                    return 0;
                }
                return (int) (o1.getBlockPos().distSqr(player.blockPosition()) - o2.getBlockPos().distSqr(player.blockPosition()));
            });
            if (blockEntities.size() > 0 && (TemperatureSystem.posSafeTemperature(level, player.blockPosition().above()) || !TemperatureSystem.isSafeTemperature(blockEntities.get(0).getCurrentTemperature()) && player.blockPosition().distSqr(blockEntities.get(0).getBlockPos()) < 100)) {
                player.displayClientMessage(Component.translatable(LangUtils.TEMPERATURE_DETECTED, blockEntities.get(0).getCurrentTemperature()), true);
            } else {
                int temperature = TemperatureSystem.getLevelTemperature(level, player.blockPosition());
                if (level.getFluidState(player.blockPosition()).is(FluidTags.LAVA)) {
                    temperature = level.random.nextIntBetweenInclusive(1200, 1500);
                }
                player.displayClientMessage(Component.translatable(LangUtils.TEMPERATURE_DETECTED, temperature), true);
            }
        }

        return InteractionResultHolder.consume(player.getItemInHand(usedHand));
    }
}
