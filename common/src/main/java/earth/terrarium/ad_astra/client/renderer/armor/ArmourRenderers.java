package earth.terrarium.ad_astra.client.renderer.armor;

import dev.architectury.injectables.annotations.ExpectPlatform;
import earth.terrarium.ad_astra.item.armor.JetSuit;
import earth.terrarium.ad_astra.registry.ModItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import org.apache.commons.lang3.NotImplementedException;

@Environment(EnvType.CLIENT)
public class ArmourRenderers {

    public static void init() {
        ArmourRenderers.registerArmour((entity, stack, slot, original) -> {
            EntityModelSet modelLoader = Minecraft.getInstance().getEntityModels();
            ModelPart layer = modelLoader.bakeLayer(SpaceSuitModel.LAYER_LOCATION);
            return new SpaceSuitModel(layer, original, entity, slot, stack);
        }, ModItems.SPACE_HELMET.get(), ModItems.SPACE_SUIT.get(), ModItems.SPACE_PANTS.get(), ModItems.SPACE_BOOTS.get());

        ArmourRenderers.registerArmour((entity, stack, slot, original) -> {
            EntityModelSet modelLoader = Minecraft.getInstance().getEntityModels();
            ModelPart layer = modelLoader.bakeLayer(NetheriteSpaceSuitModel.LAYER_LOCATION);
            return new SpaceSuitModel(layer, original, entity, slot, stack);
        }, ModItems.NETHERITE_SPACE_HELMET.get(), ModItems.NETHERITE_SPACE_SUIT.get(), ModItems.NETHERITE_SPACE_PANTS.get(), ModItems.NETHERITE_SPACE_BOOTS.get());

        ArmourRenderers.registerArmour((entity, stack, slot, original) -> {
            EntityModelSet modelLoader = Minecraft.getInstance().getEntityModels();
            ModelPart layer = modelLoader.bakeLayer(JetSuitModel.LAYER_LOCATION);

            if (slot.equals(EquipmentSlot.CHEST)) {
                if (JetSuit.hasFullSet(entity)) {
                    JetSuit.spawnParticles(entity.level, entity, original);
                }
            }

            return new SpaceSuitModel(layer, original, entity, slot, stack);
        }, ModItems.JET_SUIT_HELMET.get(), ModItems.JET_SUIT.get(), ModItems.JET_SUIT_PANTS.get(), ModItems.JET_SUIT_BOOTS.get());
    }

    @ExpectPlatform
    public static void registerArmour(ArmourModelSupplier modelSupplier, Item... items) {
        throw new NotImplementedException();
    }
}