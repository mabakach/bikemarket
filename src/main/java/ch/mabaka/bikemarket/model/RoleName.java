package ch.mabaka.bikemarket.model;

public enum RoleName {
    ADMIN("ADMIN"),
    BIKESELLER("BIKESELLER");

    private final String value;

    RoleName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
