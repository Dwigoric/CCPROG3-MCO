/**
 * The FarmerType class.
 */
public class FarmerType {
    private final String typeName;
    private final int levelRequirement;
    private final int bonusEarnings;
    private final int seedCostReduction;
    private final int waterBonusLimitIncrease;
    private final int fertilizerBonusLimitIncrease;
    private final int registrationFee;

    /**
     * The FarmerType class constructor.
     * @param typeName                      The name of the farmer type.
     * @param levelRequirement              The level requirement of the farmer type.
     * @param bonusEarnings                 The bonus earnings of the farmer type.
     * @param seedCostReduction             The seed cost reduction of the farmer type.
     * @param waterBonusLimitIncrease       The water bonus limit increase of the farmer type.
     * @param fertilizerBonusLimitIncrease  The fertilizer bonus limit increase of the farmer type.
     * @param registrationFee               The registration fee of the farmer type.
     */
    public FarmerType(String typeName, int levelRequirement, int bonusEarnings, int seedCostReduction, int waterBonusLimitIncrease, int fertilizerBonusLimitIncrease, int registrationFee) {
        this.typeName = typeName;
        this.levelRequirement = levelRequirement;
        this.bonusEarnings = bonusEarnings;
        this.seedCostReduction = seedCostReduction;
        this.waterBonusLimitIncrease = waterBonusLimitIncrease;
        this.fertilizerBonusLimitIncrease = fertilizerBonusLimitIncrease;
        this.registrationFee = registrationFee;
    }

    /**
     * Gets the name of the farmer type.
     * @return  The name of the farmer type.
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * Gets the level requirement of the farmer type.
     * @return  The level requirement of the farmer type.
     */
    public int getLevelRequirement() {
        return levelRequirement;
    }

    /**
     * Gets the bonus earnings of the farmer type.
     * @return  The bonus earnings of the farmer type.
     */
    public int getBonusEarnings() {
        return bonusEarnings;
    }

    /**
     * Gets the seed cost reduction of the farmer type.
     * @return  The seed cost reduction of the farmer type.
     */
    public int getSeedCostReduction() {
        return seedCostReduction;
    }

    /**
     * Gets the water bonus limit increase of the farmer type.
     * @return  The water bonus limit increase of the farmer type.
     */
    public int getWaterBonusLimitIncrease() {
        return waterBonusLimitIncrease;
    }

    /**
     * Gets the fertilizer bonus limit increase of the farmer type.
     * @return  The fertilizer bonus limit increase of the farmer type.
     */
    public int getFertilizerBonusLimitIncrease() {
        return fertilizerBonusLimitIncrease;
    }

    /**
     * Gets the registration fee of the farmer type.
     * @return  The registration fee of the farmer type.
     */
    public int getRegistrationFee() {
        return registrationFee;
    }
}
