package org.example;

public enum WheelValue {
    ONE("1", Color.WHITE, 22.0/54.0 * 100),
    TWO("2", Color.GREEN, 15.0/54.0 * 100),
    FIVE("5", Color.RED, 7.0/54.0 * 100),
    TEN("10", Color.CYAN, 4.0/54.0 * 100),
    X2("x2", Color.REVERSED, 3.0/54.0 * 100),
    X4("x4", Color.REVERSED, 1.0/54.0 * 100),
    CHANCE("?", Color.YELLOW, 2.0/54.0 * 100);

    private final String value;
    private final Color color;
    private final double probability;

    WheelValue(String value, Color color, double probability) {
        this.value = value;
        this.color = color;
        this.probability = probability;
    }

    public String getValue() {
        return value;
    }

    public Color getColor() {
        return color;
    }

    public double getProbability() {
        return probability;
    }

    public static WheelValue getWheelByValue(String value){
        for(WheelValue val : values())
            if(val.getValue().equals(value))
                return val;
        return null;
    }
}
