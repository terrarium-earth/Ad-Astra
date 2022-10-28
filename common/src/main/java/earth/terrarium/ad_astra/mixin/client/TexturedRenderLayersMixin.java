package earth.terrarium.ad_astra.mixin.client;

import earth.terrarium.ad_astra.registry.ModBlocks;
import earth.terrarium.ad_astra.util.ModIdentifier;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TexturedRenderLayers.class)
public abstract class TexturedRenderLayersMixin {

    private static final SpriteIdentifier AERONOS_CHEST = new SpriteIdentifier(TexturedRenderLayers.CHEST_ATLAS_TEXTURE, new ModIdentifier("entity/chest/aeronos_chest"));
    private static final SpriteIdentifier AERONOS_CHEST_RIGHT = new SpriteIdentifier(TexturedRenderLayers.CHEST_ATLAS_TEXTURE, new ModIdentifier("entity/chest/aeronos_chest_right"));
    private static final SpriteIdentifier AERONOS_CHEST_LEFT = new SpriteIdentifier(TexturedRenderLayers.CHEST_ATLAS_TEXTURE, new ModIdentifier("entity/chest/aeronos_chest_left"));
    private static final SpriteIdentifier STROPHAR_CHEST = new SpriteIdentifier(TexturedRenderLayers.CHEST_ATLAS_TEXTURE, new ModIdentifier("entity/chest/strophar_chest"));
    private static final SpriteIdentifier STROPHAR_CHEST_RIGHT = new SpriteIdentifier(TexturedRenderLayers.CHEST_ATLAS_TEXTURE, new ModIdentifier("entity/chest/strophar_chest_right"));
    private static final SpriteIdentifier STROPHAR_CHEST_LEFT = new SpriteIdentifier(TexturedRenderLayers.CHEST_ATLAS_TEXTURE, new ModIdentifier("entity/chest/strophar_chest_left"));

    @Inject(method = "getChestTexture*", at = @At("HEAD"), cancellable = true)
    private static void adastra_getChestTexture(BlockEntity blockEntity, ChestType type, boolean christmas, CallbackInfoReturnable<SpriteIdentifier> ci) {
        if (blockEntity.getCachedState().getBlock().equals(ModBlocks.AERONOS_CHEST.get())) {
            ci.setReturnValue(adastra_getCustomChestTexture(type, AERONOS_CHEST, AERONOS_CHEST_LEFT, AERONOS_CHEST_RIGHT));
        } else if (blockEntity.getCachedState().getBlock().equals(ModBlocks.STROPHAR_CHEST.get())) {
            ci.setReturnValue(adastra_getCustomChestTexture(type, STROPHAR_CHEST, STROPHAR_CHEST_LEFT, STROPHAR_CHEST_RIGHT));
        }
    }

    @Unique
    private static SpriteIdentifier adastra_getCustomChestTexture(ChestType type, SpriteIdentifier single, SpriteIdentifier left, SpriteIdentifier right) {
        return switch (type) {
            case LEFT -> left;
            case RIGHT -> right;
            case SINGLE -> single;
        };
    }
}
