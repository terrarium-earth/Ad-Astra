package com.github.alexnijjar.beyond_earth.client.renderer.entity.mobs;

import com.github.alexnijjar.beyond_earth.client.renderer.entity.mobs.models.AlienEntityModel;
import com.github.alexnijjar.beyond_earth.entities.mobs.AlienEntity;
import com.github.alexnijjar.beyond_earth.util.ModIdentifier;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.village.VillagerProfession;

public class AlienEntityRenderer extends MobEntityRenderer<AlienEntity, AlienEntityModel> {
    public static final Identifier TEXTURE = new ModIdentifier("textures/entities/alien/entity_alien.png");
    public static final Identifier FARMER_TEXTURE = new ModIdentifier("textures/entities/alien/farmer_alien.png");
    public static final Identifier FISHERMAN_TEXTURE = new ModIdentifier("textures/entities/alien/fisherman_alien.png");
    public static final Identifier SHEPHERD_TEXTURE = new ModIdentifier("textures/entities/alien/shepherd_alien.png");
    public static final Identifier FLETCHER_TEXTURE = new ModIdentifier("textures/entities/alien/fletcher_alien.png");
    public static final Identifier LIBRARIAN_TEXTURE = new ModIdentifier("textures/entities/alien/librarian_alien.png");
    public static final Identifier CARTOGRAPHER_TEXTURE = new ModIdentifier("textures/entities/alien/cartographer_alien.png");
    public static final Identifier CLERIC_TEXTURE = new ModIdentifier("textures/entities/alien/cleric_alien.png");
    public static final Identifier ARMORER_TEXTURE = new ModIdentifier("textures/entities/alien/armorer_alien.png");
    public static final Identifier WEAPONSMITH_TEXTURE = new ModIdentifier("textures/entities/alien/weaponsmith_alien.png");
    public static final Identifier TOOLSMITH_TEXTURE = new ModIdentifier("textures/entities/alien/toolsmith_alien.png");
    public static final Identifier BUTCHER_TEXTURE = new ModIdentifier("textures/entities/alien/butcher_alien.png");
    public static final Identifier LEATHERWORKER_TEXTURE = new ModIdentifier("textures/entities/alien/leatherworker_alien.png");
    public static final Identifier MASON_TEXTURE = new ModIdentifier("textures/entities/alien/mason_alien.png");

    public AlienEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new AlienEntityModel(context.getPart(AlienEntityModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public Identifier getTexture(AlienEntity entity) {

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

}
