package earth.terrarium.ad_astra.common.item;

import earth.terrarium.ad_astra.common.entity.SpacePainting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HangingEntityItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class SpacePaintingItem extends HangingEntityItem {
    public SpacePaintingItem(Properties settings) {
        super(EntityType.PAINTING, settings);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        SpacePainting spacePainting;
        BlockPos blockPos = context.getClickedPos();
        Direction direction = context.getClickedFace();
        BlockPos blockPos2 = blockPos.relative(direction);
        Player playerEntity = context.getPlayer();
        ItemStack itemStack = context.getItemInHand();
        if (playerEntity != null && !mayPlace(playerEntity, direction, itemStack, blockPos2)) {
            return InteractionResult.FAIL;
        }
        Level level = context.getLevel();

        Optional<SpacePainting> optional = SpacePainting.place(level, blockPos2, direction);
        if (optional.isEmpty()) {
            return InteractionResult.CONSUME;
        }
        spacePainting = optional.get();

        CompoundTag nbtCompound = itemStack.getTag();
        if (nbtCompound != null) {
            EntityType.updateCustomEntityTag(level, playerEntity, spacePainting, nbtCompound);
        }
        if (spacePainting.survives()) {
            if (!level.isClientSide) {
                spacePainting.playPlacementSound();
                level.gameEvent(playerEntity, GameEvent.ENTITY_PLACE, spacePainting.blockPosition());
                level.addFreshEntity(spacePainting);
            }
            itemStack.shrink(1);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.CONSUME;
    }
}
