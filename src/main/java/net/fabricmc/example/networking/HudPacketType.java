package net.fabricmc.example.networking;

public enum HudPacketType {
    CD("cd"),
    ADD_MODEL("am"),
    SET_MODEL("sm"),
    PLAY_ANIMATION("pa");

    private final String ident;
    HudPacketType(String ident) {
        this.ident = ident;
    }

    public String getIdentifier() {
        return ident;
    }
}
