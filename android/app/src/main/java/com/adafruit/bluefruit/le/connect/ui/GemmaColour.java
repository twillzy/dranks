package com.adafruit.bluefruit.le.connect.ui;

public enum GemmaColour {
    BLUE(16776961),
    RED(-65514),
    GREEN(-8323328),
    PURPLE(-8060673),
    ORANGE(-25600),
    YELLOW(-3840),
    WHITE(-1),
    PINK(-65292);

    private final int colour;

    GemmaColour(int colour) {
        this.colour = colour;
    }

    public int getColour() {
        return colour;
    }
}
