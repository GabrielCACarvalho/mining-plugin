package test.dungeonrealms.mining.model;

import java.util.List;

public class Pick {
    
    private String name;
    private Integer tier;
    private Integer level;
    private List<PickEnchant> pickEnchs;
    private Integer xp;
    private Integer neededXp;

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getNeededXp() {
        return neededXp;
    }

    public void setNeededXp(Integer neededXp) {
        this.neededXp = neededXp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTier() {
        return tier;
    }

    public void setTier(Integer tier) {
        this.tier = tier;
    }

    public List<PickEnchant> getPickEnchs() {
        return pickEnchs;
    }

    public void setPickEnchs(List<PickEnchant> pickEnchs) {
        this.pickEnchs = pickEnchs;
    }

    public Integer getXp() {
        return xp;
    }

    public void setXp(Integer xp) {
        this.xp = xp;
    }
}
