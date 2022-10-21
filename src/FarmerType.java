public class FarmerType {
    private final String typeName;
    private final int levelRequirement;
    private final int bonusEarnings;
    private final int seedCostReduction;
    private final int waterBonusLimitIncrease;
    private final int fertilizerBonusLimitIncrease;
    private final int registrationFee;

    public FarmerType(String typeName, int levelRequirement, int bonusEarnings, int seedCostReduction, int waterBonusLimitIncrease, int fertilizerBonusLimitIncrease, int registrationFee) {
        this.typeName = typeName;
        this.levelRequirement = levelRequirement;
        this.bonusEarnings = bonusEarnings;
        this.seedCostReduction = seedCostReduction;
        this.waterBonusLimitIncrease = waterBonusLimitIncrease;
        this.fertilizerBonusLimitIncrease = fertilizerBonusLimitIncrease;
        this.registrationFee = registrationFee;
    }

    public String getTypeName() {
        return typeName;
    }

    public int getLevelRequirement() {
        return levelRequirement;
    }

    public int getBonusEarnings() {
        return bonusEarnings;
    }

    public int getSeedCostReduction() {
        return seedCostReduction;
    }

    public int getWaterBonusLimitIncrease() {
        return waterBonusLimitIncrease;
    }

    public int getFertilizerBonusLimitIncrease() {
        return fertilizerBonusLimitIncrease;
    }

    public int getRegistrationFee() {
        return registrationFee;
    }
}
