package earth.terrarium.ad_astra.common.block.pipe;


import earth.terrarium.ad_astra.common.block.BasicEntityBlock;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PipeDuctBlock extends BasicEntityBlock implements Pipe {
    private final long transferRate;
    private final PipeBlock.PipeType type;

    public PipeDuctBlock(long transferRate, PipeBlock.PipeType type, Properties properties) {
        super(properties);
        this.transferRate = transferRate;
        this.type = type;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(type == PipeBlock.PipeType.FLUID_PIPE ? Component.translatable("item.ad_astra.fluid_pipe_duct.tooltip") : Component.translatable("item.ad_astra.cable_duct.tooltip"));
    }

    @Override
    public long getTransferRate() {
        return transferRate;
    }
}
