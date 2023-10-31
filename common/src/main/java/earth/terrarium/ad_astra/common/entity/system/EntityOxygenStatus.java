package earth.terrarium.ad_astra.common.entity.system;

public enum EntityOxygenStatus {
    /**
     * AdAstraConfig.doOxygen is false or Entity is undead or<br>
     * Entity has 'ad_astra:entities/lives_without_oxygen' tag or<br>
     * Entity's level has oxygen
     */
    IGNORE,
    /**
     * Entity is equipped full set of space suit and<br>
     * Entity should consume oxygen in equipped space suit
     */
    CONSUME,
    /**
     * Entity is not equipped space suit or No oxygen in space suit<br>
     * Entity should take damage by suffocation
     */
    DAMAGE,
}
