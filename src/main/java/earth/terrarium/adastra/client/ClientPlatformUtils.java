package earth.terrarium.adastra.client;

import earth.terrarium.adastra.client.dimension.ModDimensionSpecialEffects;
import earth.terrarium.common_storage_lib.resources.ResourceStack;
import earth.terrarium.common_storage_lib.resources.fluid.FluidResource;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.msrandom.multiplatform.annotations.Expect;

import java.util.Map;
import java.util.function.Supplier;

public class ClientPlatformUtils {

    @FunctionalInterface
    public interface SpriteParticleRegistration<T extends ParticleOptions> {

        ParticleProvider<T> create(SpriteSet spriteSet);
    }

    @FunctionalInterface
    public interface LayerDefinitionRegistry {

        void register(ModelLayerLocation location, Supplier<LayerDefinition> definition);
    }

    @FunctionalInterface
    public interface ArmorFactory {

        HumanoidModel<?> create(ModelPart root, EquipmentSlot slot, ItemStack stack, HumanoidModel<LivingEntity> parentModel);
    }

    @FunctionalInterface
    public interface RenderHud {

        void renderHud(GuiGraphics graphics, DeltaTracker deltaTracker);
    }

    @Expect
    public static BakedModel getModel(ModelManager dispatcher, ResourceLocation id);

    @Expect
    public static void registerArmor(ResourceLocation texture, ModelLayerLocation layer, ArmorFactory factory, Item... items);

    @Expect
    public static void registerPlanetRenderers(Map<ResourceKey<Level>, ModDimensionSpecialEffects> renderers);

    @Expect
    public static TextureAtlasSprite getFluidSprite(ResourceStack<FluidResource> stack);

    @Expect
    public static int getFluidColor(ResourceStack<FluidResource> stack);

    @Expect
    public static Component getDisplayName(ResourceStack<FluidResource> stack);
}
