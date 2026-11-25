package domain;

import java.awt.Color;

public class PowerUp {
    public enum Type {
        HEALTH, SCORE, DAMAGE_BOOST
    }

    private Type type;
    private int x;
    private int y;

    public PowerUp(Type type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public Type getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        switch (type) {
            case HEALTH: return Color.GREEN;
            case SCORE: return Color.YELLOW;
            case DAMAGE_BOOST: return Color.CYAN;
            default: return Color.WHITE;
        }
    }

    public String getLabel() {
        switch (type) {
            case HEALTH: return "+HP";
            case SCORE: return "+15";
            case DAMAGE_BOOST: return "+5%";
            default: return "";
        }
    }
}