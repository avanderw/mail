package net.avdw.color.palette.three;

public class GrayscaleThreeColorPalette implements ThreeColorPalette {
    @Override
    public int primaryTone() {
        return 0x999999;
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
