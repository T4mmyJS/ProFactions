package me.QuantumDev.ProFactions.utils;

public enum RolesUtility {

    LEADER ("Leader", "&4Leader", " &4[L]", "&4"),
    ADMIN ("Admin", "&cAdmin", " &c[A]", "&c"),
    MODERATOR ("Moderator", "&5Moderator", " &5[M]", "&5"),
    MEMBER ("Member", "&7Member", "", "&7");

    String name;
    String colouredName;
    String suffix;
    String colour;

    RolesUtility(String name, String colouredName, String suffix, String colour) {
        this.name = name;
        this.colouredName = colouredName;
        this.suffix = suffix;
        this.colour = colour;
    }

    public String getName() {
        return this.name;
    }

    public String getColouredName() {
        return this.colouredName;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public String getColour() {
        return this.colour;
    }


}
