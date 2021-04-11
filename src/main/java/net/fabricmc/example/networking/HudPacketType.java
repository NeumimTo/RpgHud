package net.fabricmc.example.networking;

public enum HudPacketType {
    CD("cd");

    private final String ident;
    HudPacketType(String ident) {
        this.ident = ident;
    }

    public String getIdentifier() {
        return ident;
    }
}
