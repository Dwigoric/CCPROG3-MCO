/**
 * The FarmerType class.
 */
public record FarmerType(String typeName, int levelRequirement, int bonusEarnings, int seedCostReduction,
                         int waterBonusLimitIncrease, int fertilizerBonusLimitIncrease, int registrationFee) {
    /**
     * The FarmerType class constructor.
     *
     * @param typeName                     The name of the farmer type.
     * @param levelRequirement             The level requirement of the farmer type.
     * @param bonusEarnings                The bonus earnings of the farmer type.
     * @param seedCostReduction            The seed cost reduction of the farmer type.
     * @param waterBonusLimitIncrease      The water bonus limit increase of the farmer type.
     * @param fertilizerBonusLimitIncrease The fertilizer bonus limit increase of the farmer type.
     * @param registrationFee              The registration fee of the farmer type.
     */
    public FarmerType {
    }

    /**
     * Gets the name of the farmer type.
     *
     * @return The name of the farmer type.
     */
    @Override
    public String typeName() {
        return typeName;
    }

    /**
     * Gets the level requirement of the farmer type.
     *
     * @return The level requirement of the farmer type.
     */
    @Override
    public int levelRequirement() {
        return levelRequirement;
    }

    /**
     * Gets the bonus earnings of the farmer type.
     *
     * @return The bonus earnings of the farmer type.
     */
    @Override
    public int bonusEarnings() {
        return bonusEarnings;
    }

    /**
     * Gets the seed cost reduction of the farmer type.
     *
     * @return The seed cost reduction of the farmer type.
     */
    @Override
    public int seedCostReduction() {
        return seedCostReduction;
    }

    /**
     * Gets the water bonus limit increase of the farmer type.
     *
     * @return The water bonus limit increase of the farmer type.
     */
    @Override
    public int waterBonusLimitIncrease() {
        return waterBonusLimitIncrease;
    }

    /**
     * Gets the fertilizer bonus limit increase of the farmer type.
     *
     * @return The fertilizer bonus limit increase of the farmer type.
     */
    @Override
    public int fertilizerBonusLimitIncrease() {
        return fertilizerBonusLimitIncrease;
    }

    /**
     * Gets the registration fee of the farmer type.
     *
     * @return The registration fee of the farmer type.
     */
    @Override
    public int registrationFee() {
        return registrationFee;
    }
}
