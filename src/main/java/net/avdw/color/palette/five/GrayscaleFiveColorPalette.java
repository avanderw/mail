package net.avdw.color.palette.five;

public class GrayscaleFiveColorPalette implements FiveColorPalette {
    @Override
    public int primaryTint() {
        return 0xCCCCCC;
    }

    @Override
    public int primaryTone() {
        return 0x999999;
    }

    @Override
    public int primaryShade() {
        return 0x666666;
    }

    @Override
    public int secondaryTone() {
        return 0x333333;
    }

    @Override
    public int accentTone() {
        return 0xFFFFFF;
    }
}
