package test.dungeonrealms.mining.model;

import test.dungeonrealms.mining.model.enums.Enchant;

public class PickEnchant {

    private Enchant enchant;
    private Integer percentage;
    private Boolean passive;

    public PickEnchant(Enchant enchant, Integer percentage, Boolean passive) {
        this.enchant = enchant;
        this.percentage = percentage;
        this.passive = passive;
    }

    public Boolean getPassive() {
        return passive;
    }

    public void setPassive(Boolean passive) {
        this.passive = passive;
    }

    public Enchant getEnchant() {
        return enchant;
    }

    public void setEnchant(Enchant enchant) {
        this.enchant = enchant;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }
}
