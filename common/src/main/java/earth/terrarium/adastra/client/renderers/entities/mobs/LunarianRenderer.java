package earth.terrarium.adastra.client.renderers.entities.mobs;

import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.models.entities.mobs.LunarianModel;
import earth.terrarium.adastra.common.entities.mob.Lunarian;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CrossedArmsItemLayer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.VillagerProfession;
import org.jetbrains.annotations.NotNull;

// LEGACY ENTITY. WILL BE REPLACED IN THE FUTURE.
public class LunarianRenderer extends MobRenderer<Lunarian, LunarianModel<Lunarian>> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/entity/mob/lunarian/lunarian.png");
    public static final ResourceLocation FARMER_TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/entity/mob/lunarian/farmer_lunarian.png");
    public static final ResourceLocation FISHERMAN_TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/entity/mob/lunarian/fisherman_lunarian.png");
    public static final ResourceLocation SHEPHERD_TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/entity/mob/lunarian/shepherd_lunarian.png");
    public static final ResourceLocation FLETCHER_TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/entity/mob/lunarian/fletcher_lunarian.png");
    public static final ResourceLocation LIBRARIAN_TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/entity/mob/lunarian/librarian_lunarian.png");
    public static final ResourceLocation CARTOGRAPHER_TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/entity/mob/lunarian/cartographer_lunarian.png");
    public static final ResourceLocation CLERIC_TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/entity/mob/lunarian/cleric_lunarian.png");
    public static final ResourceLocation ARMORER_TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/entity/mob/lunarian/armorer_lunarian.png");
    public static final ResourceLocation WEAPONSMITH_TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/entity/mob/lunarian/weaponsmith_lunarian.png");
    public static final ResourceLocation TOOLSMITH_TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/entity/mob/lunarian/toolsmith_lunarian.png");
    public static final ResourceLocation BUTCHER_TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/entity/mob/lunarian/butcher_lunarian.png");
    public static final ResourceLocation LEATHERWORKER_TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/entity/mob/lunarian/leatherworker_lunarian.png");
    public static final ResourceLocation MASON_TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/entity/mob/lunarian/mason_lunarian.png");

    public LunarianRenderer(EntityRendererProvider.Context context) {
        super(context, new LunarianModel<>(context.bakeLayer(LunarianModel.LAYER_LOCATION)), 0.5f);
        this.addLayer(new CustomHeadLayer<>(this, context.getModelSet(), context.getItemInHandRenderer()));
        this.addLayer(new CrossedArmsItemLayer<>(this, context.getItemInHandRenderer()));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Lunarian entity) {

        VillagerProfession profession = entity.getVillagerData().getProfession();
        if (profession.equals(VillagerProfession.ARMORER)) {
            return ARMORER_TEXTURE;
        } else if (profession.equals(VillagerProfession.BUTCHER)) {
            return BUTCHER_TEXTURE;
        } else if (profession.equals(VillagerProfession.CARTOGRAPHER)) {
            return CARTOGRAPHER_TEXTURE;
        } else if (profession.equals(VillagerProfession.CLERIC)) {
            return CLERIC_TEXTURE;
        } else if (profession.equals(VillagerProfession.FARMER)) {
            return FARMER_TEXTURE;
        } else if (profession.equals(VillagerProfession.FISHERMAN)) {
            return FISHERMAN_TEXTURE;
        } else if (profession.equals(VillagerProfession.FLETCHER)) {
            return FLETCHER_TEXTURE;
        } else if (profession.equals(VillagerProfession.LEATHERWORKER)) {
            return LEATHERWORKER_TEXTURE;
        } else if (profession.equals(VillagerProfession.LIBRARIAN)) {
            return LIBRARIAN_TEXTURE;
        } else if (profession.equals(VillagerProfession.MASON)) {
            return MASON_TEXTURE;
        } else if (profession.equals(VillagerProfession.SHEPHERD)) {
            return SHEPHERD_TEXTURE;
        } else if (profession.equals(VillagerProfession.TOOLSMITH)) {
            return TOOLSMITH_TEXTURE;
        } else if (profession.equals(VillagerProfession.WEAPONSMITH)) {
            return WEAPONSMITH_TEXTURE;
        } else {
            return TEXTURE;
        }
    }

    protected void scale(Lunarian villagerEntity, PoseStack poseStack, float f) {
        float g = 0.9375f;
        if (villagerEntity.isBaby()) {
            g *= 0.5f;
            this.shadowRadius = 0.25f;
        } else {
            this.shadowRadius = 0.5f;
        }
        poseStack.scale(g, g, g);
    }
}
