package net.avdw.number.generator;

public class ConstantNumberGenerator implements NumberGenerator {
    private final Double number;

    public ConstantNumberGenerator(final Double number) {
        this.number = number;
    }

    /**
     * Generate a value.
     *
     * @return a value following the implementation rule
     */
    @Override
    public Double nextValue() {
        return number;
    }
}
