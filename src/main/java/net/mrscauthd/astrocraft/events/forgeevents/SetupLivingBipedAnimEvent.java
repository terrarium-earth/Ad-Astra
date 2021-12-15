package net.mrscauthd.astrocraft.events.forgeevents;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

public class SetupLivingBipedAnimEvent extends Event
{
    private LivingEntity livingEntity;
    private HumanoidModel model;
    private float limbSwing;
    private float limbSwingAmount;
    private float ageInTicks;
    private float netHeadYaw;
    private float headPitch;

    public SetupLivingBipedAnimEvent(LivingEntity livingEntity, HumanoidModel model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
        this.livingEntity = livingEntity;
        this.model = model;
        this.limbSwing = limbSwing;
        this.limbSwingAmount = limbSwingAmount;
        this.ageInTicks = ageInTicks;
        this.netHeadYaw = netHeadYaw;
        this.headPitch = headPitch;
    }

    public LivingEntity getLivingEntity()
    {
        return livingEntity;
    }

    public HumanoidModel getModel()
    {
        return model;
    }

    public float getLimbSwing()
    {
        return limbSwing;
    }

    public float getLimbSwingAmount()
    {
        return limbSwingAmount;
    }

    public float getAgeInTicks()
    {
        return ageInTicks;
    }

    public float getNetHeadYaw()
    {
        return netHeadYaw;
    }

    public float getHeadPitch() {
        return headPitch;
    }

    @Cancelable
    public static class Pre extends SetupLivingBipedAnimEvent
    {
        public Pre(LivingEntity livingEntity, HumanoidModel model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
        {
            super(livingEntity, model, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        }
    }

    public static class Post extends SetupLivingBipedAnimEvent
    {
        public Post(LivingEntity livingEntity, HumanoidModel model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
        {
            super(livingEntity, model, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        }
    }
}
