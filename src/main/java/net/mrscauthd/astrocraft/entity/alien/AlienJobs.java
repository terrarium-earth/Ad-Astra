package net.mrscauthd.astrocraft.entity.alien;

import net.minecraft.world.entity.npc.VillagerProfession;

public enum AlienJobs {
    JOB1(VillagerProfession.FARMER),
    JOB2(VillagerProfession.FISHERMAN),
    JOB3(VillagerProfession.SHEPHERD),
    JOB4(VillagerProfession.FLETCHER),
    JOB5(VillagerProfession.LIBRARIAN),
    JOB6(VillagerProfession.CARTOGRAPHER),
    JOB7(VillagerProfession.CLERIC),
    JOB8(VillagerProfession.ARMORER),
    JOB9(VillagerProfession.WEAPONSMITH),
    JOB10(VillagerProfession.TOOLSMITH),
    JOB11(VillagerProfession.BUTCHER),
    JOB12(VillagerProfession.LEATHERWORKER),
    JOB13(VillagerProfession.MASON);

    public VillagerProfession profession;

    private AlienJobs(VillagerProfession profession){
        this.profession = profession;
    }

    public VillagerProfession getAlienJobs() {
        return profession;
    }

}