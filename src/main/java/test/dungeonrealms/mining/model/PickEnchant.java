package test.dungeonrealms.mining.model;

import test.dungeonrealms.mining.model.enums.Enchant;

public class PickEnchant {

    private Enchant enchant;
    private Integer percentage;

    public PickEnchant(Enchant enchant, Integer percentage) {
        this.enchant = enchant;
        this.percentage = percentage;
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
