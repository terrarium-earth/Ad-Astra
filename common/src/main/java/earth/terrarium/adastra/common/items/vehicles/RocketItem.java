package earth.terrarium.adastra.common.items.vehicles;

import earth.terrarium.adastra.common.blocks.LaunchPadBlock;
import earth.terrarium.adastra.common.blocks.properties.LaunchPadPartProperty;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.entities.vehicles.Rocket;
import earth.terrarium.adastra.common.tags.ModBlockTags;
import earth.terrarium.adastra.common.utils.TooltipUtils;
import earth.terrarium.botarium.common.fluid.FluidApi;
import earth.terrarium.botarium.common.item.ItemStackHolder;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.function.Supplier;

public class RocketItem extends VehicleItem {

    public RocketItem(Supplier<EntityType<?>> type, Properties properties) {
        super(type, properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        var level = context.getLevel();
        if (level.isClientSide()) return InteractionResult.PASS;
        var pos = context.getClickedPos();
        var stack = context.getItemInHand();
        var state = level.getBlockState(pos);

        if (!state.is(ModBlockTags.LAUNCH_PADS)) return InteractionResult.PASS;
        if (state.hasProperty(LaunchPadBlock.PART) && state.getValue(LaunchPadBlock.PART) != LaunchPadPartProperty.CENTER) {
            return InteractionResult.PASS;
        }

        level.playSound(null, pos, SoundEvents.NETHERITE_BLOCK_PLACE, SoundSource.BLOCKS, 1, 1);
        var vehicle = type().create(level);
        if (vehicle == null) return InteractionResult.PASS;
        vehicle.setPos(pos.getX() + 0.5, pos.getY() + 0.125f, pos.getZ() + 0.5);
        vehicle.setYRot(context.getHorizontalDirection().getOpposite().toYRot());
        level.addFreshEntity(vehicle);

        if (vehicle instanceof Rocket rocket) {
            ItemStackHolder holder = new ItemStackHolder(stack);
            var fluidContainer = getFluidContainer(stack).container();
            FluidApi.moveFluid(FluidApi.getItemFluidContainer(holder), rocket.fluidContainer(), fluidContainer.getFirstFluid(), false);
        }

        stack.shrink(1);
        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
        TooltipUtils.addDescriptionComponent(tooltipComponents, ConstantComponents.ROCKET_INFO);
    }
}
