package org.example;

/**
 * Brief Enum containing the colors
 */
public enum Color {
    YELLOW("\u001B[33;1m"),
    RED("\u001B[31;1m"),
    CYAN("\u001B[36m"),
    GREEN("\u001B[32;1m"),
    BLUE("\u001B[94;1m"),
    WHITE("\u001B[37;1m"),
    REVERSED("\u001B[7m");

    public static final String RESET = "\u001B[0m";
    private final String color;

    Color(String color) {
        this.color = color;
    }
    public String color() {
        return color;
    }

    public static String formatMessageColor(String message, Color color){
        return color.color() + message + RESET;
    }
}
