package net.avdw.color.palette.three;
/**
 * Structure to provide colors for the themes.
 *
 * https://www.tigercolor.com/color-lab/color-theory/color-harmonies.htm
 *
 * Red: energy, power, passion
 * Orange: joy, enthusiasm, creativity
 * Yellow: happiness, intellect, energy
 * Green: ambition, growth, freshness, safety
 * Blue: tranquility, confidence, intelligence
 * Purple: luxury, ambition, creativity
 * Black: power, elegance, mystery
 * White: cleanliness, purity, perfection
 *
 * Start with greyscale
 * Use PRIMARY-SECONDARY-ACCENT, 60-30-10 hue rule
 */
public interface ThreeColorPalette {
    int primaryTone();
    int secondaryTone();
    int accentTone();
}
