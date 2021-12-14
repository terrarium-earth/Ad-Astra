package net.mrscauthd.boss_tools.events.forgeevents;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

public class RenderPlayerArmEvent extends Event
{
    private final PoseStack poseStack;
    private final MultiBufferSource multiBufferSource;
    private final int packedLight;
    private final AbstractClientPlayer player;
    private final HumanoidArm arm;
    private final ModelPart rightArmModel;
    private final ModelPart leftArmModel;
    private final PlayerModel<AbstractClientPlayer> playerModel;

    public RenderPlayerArmEvent(PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, AbstractClientPlayer player, HumanoidArm arm, PlayerModel<AbstractClientPlayer> playerModel, ModelPart rightArm, ModelPart leftArm)
    {
        this.poseStack = poseStack;
        this.multiBufferSource = multiBufferSource;
        this.packedLight = packedLight;
        this.player = player;
        this.arm = arm;
        this.rightArmModel = rightArm;
        this.leftArmModel = leftArm;
        this.playerModel = playerModel;
    }

    public HumanoidArm getArm()
    {
        return arm;
    }

    public PoseStack getPoseStack()
    {
        return poseStack;
    }

    public MultiBufferSource getMultiBufferSource()
    {
        return multiBufferSource;
    }

    public int getPackedLight()
    {
        return packedLight;
    }

    public AbstractClientPlayer getPlayer()
    {
        return player;
    }

    public PlayerModel<AbstractClientPlayer> getPlayerModel()
    {
        return playerModel;
    }

    public ModelPart getRightArmModel()
    {
        return rightArmModel;
    }

    public ModelPart getLeftArmModel()
    {
        return leftArmModel;
    }

    @Cancelable
    public static class RightArm extends RenderPlayerArmEvent
    {
        public RightArm(PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, AbstractClientPlayer player, HumanoidArm arm, PlayerModel<AbstractClientPlayer> playerModel, ModelPart rightArm, ModelPart leftArm)
        {
            super(poseStack, multiBufferSource, packedLight, player, arm, playerModel, rightArm, leftArm);
        }
    }

    @Cancelable
    public static class LeftArm extends RenderPlayerArmEvent
    {
        public LeftArm(PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, AbstractClientPlayer player, HumanoidArm arm, PlayerModel<AbstractClientPlayer> playerModel, ModelPart rightArm, ModelPart leftArm)
        {
            super(poseStack, multiBufferSource, packedLight, player, arm, playerModel, rightArm, leftArm);
        }
    }
}