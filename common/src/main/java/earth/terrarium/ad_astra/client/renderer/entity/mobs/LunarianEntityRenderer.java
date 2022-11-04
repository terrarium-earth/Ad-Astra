package earth.terrarium.ad_astra.client.renderer.entity.mobs;

import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.ad_astra.client.renderer.entity.mobs.models.LunarianEntityModel;
import earth.terrarium.ad_astra.entities.mobs.Lunarian;
import earth.terrarium.ad_astra.util.ModResourceLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CrossedArmsItemLayer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.VillagerProfession;

public class LunarianEntityRenderer extends MobRenderer<Lunarian, LunarianEntityModel<Lunarian>> {
    public static final ResourceLocation TEXTURE = new ModResourceLocation("textures/entity/lunarian/lunarian.png");
    public static final ResourceLocation FARMER_TEXTURE = new ModResourceLocation("textures/entity/lunarian/farmer_lunarian.png");
    public static final ResourceLocation FISHERMAN_TEXTURE = new ModResourceLocation("textures/entity/lunarian/fisherman_lunarian.png");
    public static final ResourceLocation SHEPHERD_TEXTURE = new ModResourceLocation("textures/entity/lunarian/shepherd_lunarian.png");
    public static final ResourceLocation FLETCHER_TEXTURE = new ModResourceLocation("textures/entity/lunarian/fletcher_lunarian.png");
    public static final ResourceLocation LIBRARIAN_TEXTURE = new ModResourceLocation("textures/entity/lunarian/librarian_lunarian.png");
    public static final ResourceLocation CARTOGRAPHER_TEXTURE = new ModResourceLocation("textures/entity/lunarian/cartographer_lunarian.png");
    public static final ResourceLocation CLERIC_TEXTURE = new ModResourceLocation("textures/entity/lunarian/cleric_lunarian.png");
    public static final ResourceLocation ARMORER_TEXTURE = new ModResourceLocation("textures/entity/lunarian/armorer_lunarian.png");
    public static final ResourceLocation WEAPONSMITH_TEXTURE = new ModResourceLocation("textures/entity/lunarian/weaponsmith_lunarian.png");
    public static final ResourceLocation TOOLSMITH_TEXTURE = new ModResourceLocation("textures/entity/lunarian/toolsmith_lunarian.png");
    public static final ResourceLocation BUTCHER_TEXTURE = new ModResourceLocation("textures/entity/lunarian/butcher_lunarian.png");
    public static final ResourceLocation LEATHERWORKER_TEXTURE = new ModResourceLocation("textures/entity/lunarian/leatherworker_lunarian.png");
    public static final ResourceLocation MASON_TEXTURE = new ModResourceLocation("textures/entity/lunarian/mason_lunarian.png");

    public LunarianEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new LunarianEntityModel<>(context.bakeLayer(LunarianEntityModel.LAYER_LOCATION)), 0.5f);
        this.addLayer(new CustomHeadLayer<>(this, context.getModelSet(), context.getItemInHandRenderer()));
        this.addLayer(new CrossedArmsItemLayer<>(this, context.getItemInHandRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(Lunarian entity) {

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
