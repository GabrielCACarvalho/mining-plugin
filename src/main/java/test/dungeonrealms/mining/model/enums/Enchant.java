package test.dungeonrealms.mining.model.enums;

public enum Enchant {

    MINING_SUCCESS("MINING SUCCESS"),
    DOUBLE_ORE("DOUBLE ORE"),
    TRIPLE_ORE("TRIPLE ORE"),
    GEM_FIND("GEM FIND");

    private final String name;

    Enchant(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
