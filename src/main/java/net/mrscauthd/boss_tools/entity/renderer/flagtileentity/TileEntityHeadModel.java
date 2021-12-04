package net.mrscauthd.boss_tools.entity.renderer.flagtileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TileEntityHeadModel extends Model {
    protected final ModelRenderer field_217105_a;

    public TileEntityHeadModel() {
        this(0, 35, 64, 64);
    }

    public TileEntityHeadModel(int p_i51060_1_, int p_i51060_2_, int p_i51060_3_, int p_i51060_4_) {
        super(RenderType::getEntityTranslucent);
        this.textureWidth = p_i51060_3_;
        this.textureHeight = p_i51060_4_;
        this.field_217105_a = new ModelRenderer(this, p_i51060_1_, p_i51060_2_);

        this.field_217105_a.setTextureOffset(8, 8).addBox(-3.0F, -11.0F, 3.980F, 8.0F, 8.0F, 0.020F, 0.0F, false);

        this.field_217105_a.setTextureOffset(0, 8).addBox(-3.0F, -11.0F, 4.002F, 8.0F, 8.0F, 0.010F, 0.0F, false);
    }

    public void func_225603_a_(float p_225603_1_, float p_225603_2_, float p_225603_3_) {
        this.field_217105_a.rotateAngleY = p_225603_2_ * ((float)Math.PI / 180F);
        this.field_217105_a.rotateAngleX = p_225603_3_ * ((float)Math.PI / 180F);
    }

    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.field_217105_a.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}