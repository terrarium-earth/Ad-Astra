package net.mrscauthd.boss_tools.entity.renderer.alien;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.entity.alien.AlienEntity;

public class AlienRenderer extends MobRenderer<AlienEntity, AlienModel<AlienEntity>> {
    public AlienRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new AlienModel<>(), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(AlienEntity entity) {
        if (entity.getVillagerData().getProfession() == VillagerProfession.FARMER) {
            return new ResourceLocation(BossToolsMod.ModId,"textures/entities/alien/entity_alien1.png");
        }
        if (entity.getVillagerData().getProfession() == VillagerProfession.FISHERMAN) {
            return new ResourceLocation(BossToolsMod.ModId,"textures/entities/alien/entity_alien2.png");
        }
        if (entity.getVillagerData().getProfession() == VillagerProfession.SHEPHERD) {
            return new ResourceLocation(BossToolsMod.ModId,"textures/entities/alien/entity_alien3.png");
        }
        if (entity.getVillagerData().getProfession() == VillagerProfession.FLETCHER) {
            return new ResourceLocation(BossToolsMod.ModId,"textures/entities/alien/entity_alien4.png");
        }
        if (entity.getVillagerData().getProfession() == VillagerProfession.LIBRARIAN) {
            return new ResourceLocation(BossToolsMod.ModId,"textures/entities/alien/entity_alien5.png");
        }
        if (entity.getVillagerData().getProfession() == VillagerProfession.CARTOGRAPHER) {
            return new ResourceLocation(BossToolsMod.ModId,"textures/entities/alien/entity_alien6.png");
        }
        if (entity.getVillagerData().getProfession() == VillagerProfession.CLERIC) {
            return new ResourceLocation(BossToolsMod.ModId,"textures/entities/alien/entity_alien7.png");
        }
        if (entity.getVillagerData().getProfession() == VillagerProfession.ARMORER) {
            return new ResourceLocation(BossToolsMod.ModId,"textures/entities/alien/entity_alien8.png");
        }
        if (entity.getVillagerData().getProfession() == VillagerProfession.WEAPONSMITH) {
            return new ResourceLocation(BossToolsMod.ModId,"textures/entities/alien/entity_alien9.png");
        }
        if (entity.getVillagerData().getProfession() == VillagerProfession.TOOLSMITH) {
            return new ResourceLocation(BossToolsMod.ModId,"textures/entities/alien/entity_alien10.png");
        }
        if (entity.getVillagerData().getProfession() == VillagerProfession.BUTCHER) {
            return new ResourceLocation(BossToolsMod.ModId,"textures/entities/alien/entity_alien11.png");
        }
        if (entity.getVillagerData().getProfession() == VillagerProfession.LEATHERWORKER) {
            return new ResourceLocation(BossToolsMod.ModId,"textures/entities/alien/entity_alien12.png");
        }
        if (entity.getVillagerData().getProfession() == VillagerProfession.MASON) {
            return new ResourceLocation(BossToolsMod.ModId,"textures/entities/alien/entity_alien13.png");
        }

        return new ResourceLocation(BossToolsMod.ModId,"textures/entities/alien/entity_alien.png");
    }
}