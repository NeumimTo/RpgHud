package net.fabricmc.example.networking;

public enum HudPacketType {
    CD("cd"),
    SET_MODEL("sm"),
    REMOVE_MODELS("rm"),
    PLAY_ANIMATION("pa");

    private final String ident;
    HudPacketType(String ident) {
        this.ident = ident;
    }

    public String getIdentifier() {
        return ident;
    }

    public static HudPacketType byIdentification(String id) {
        for (HudPacketType value : values()) {
            if (id.equalsIgnoreCase(value.ident)) {
                return value;
            }
        }
        return null;
    }

}
