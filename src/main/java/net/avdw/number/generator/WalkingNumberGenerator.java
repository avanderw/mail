package net.avdw.number.generator;

public class WalkingNumberGenerator implements NumberGenerator {
    private Double walkingNumber;
    private final Double minWalk;
    private final Double maxWalk;
    private final boolean inBothDirections;
    private final RandomGenerator randomGenerator;

    WalkingNumberGenerator(final Double walkingNumber, final Double minWalk, final Double maxWalk, final boolean inBothDirections, final RandomGenerator randomGenerator) {
        this.walkingNumber = walkingNumber;
        this.minWalk = minWalk;
        this.maxWalk = maxWalk;
        this.inBothDirections = inBothDirections;
        this.randomGenerator = randomGenerator;
    }

    /**
     * Generate a value.
     *
     * @return a value following the implementation rule
     */
    @Override
    public Double nextValue() {
        walkingNumber += randomGenerator.nextOffset(walkingNumber, minWalk, maxWalk, inBothDirections);
        return walkingNumber;
    }
}
