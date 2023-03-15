package earth.terrarium.ad_astra.mixin.client;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.registry.ModBlocks;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.ChestType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Sheets.class)
public abstract class SheetsMixin {

    private static final Material AERONOS_CHEST = new Material(Sheets.CHEST_SHEET, new ResourceLocation(AdAstra.MOD_ID, "entity/chest/aeronos_chest"));
    private static final Material AERONOS_CHEST_RIGHT = new Material(Sheets.CHEST_SHEET, new ResourceLocation(AdAstra.MOD_ID, "entity/chest/aeronos_chest_right"));
    private static final Material AERONOS_CHEST_LEFT = new Material(Sheets.CHEST_SHEET, new ResourceLocation(AdAstra.MOD_ID, "entity/chest/aeronos_chest_left"));
    private static final Material STROPHAR_CHEST = new Material(Sheets.CHEST_SHEET, new ResourceLocation(AdAstra.MOD_ID, "entity/chest/strophar_chest"));
    private static final Material STROPHAR_CHEST_RIGHT = new Material(Sheets.CHEST_SHEET, new ResourceLocation(AdAstra.MOD_ID, "entity/chest/strophar_chest_right"));
    private static final Material STROPHAR_CHEST_LEFT = new Material(Sheets.CHEST_SHEET, new ResourceLocation(AdAstra.MOD_ID, "entity/chest/strophar_chest_left"));

    @Inject(method = "chooseMaterial(Lnet/minecraft/world/level/block/entity/BlockEntity;Lnet/minecraft/world/level/block/state/properties/ChestType;Z)Lnet/minecraft/client/resources/model/Material;", at = @At("HEAD"), cancellable = true)
    private static void ad_astra$getChestTexture(BlockEntity blockEntity, ChestType type, boolean christmas, CallbackInfoReturnable<Material> cir) {
        if (blockEntity.getBlockState().getBlock().equals(ModBlocks.AERONOS_CHEST.get())) {
            cir.setReturnValue(ad_astra$getCustomChestTexture(type, AERONOS_CHEST, AERONOS_CHEST_LEFT, AERONOS_CHEST_RIGHT));
        } else if (blockEntity.getBlockState().getBlock().equals(ModBlocks.STROPHAR_CHEST.get())) {
            cir.setReturnValue(ad_astra$getCustomChestTexture(type, STROPHAR_CHEST, STROPHAR_CHEST_LEFT, STROPHAR_CHEST_RIGHT));
        }
    }

    @Unique
    private static Material ad_astra$getCustomChestTexture(ChestType type, Material single, Material left, Material right) {
        return switch (type) {
            case LEFT -> left;
            case RIGHT -> right;
            case SINGLE -> single;
        };
    }
}
