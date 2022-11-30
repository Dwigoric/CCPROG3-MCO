/**
 * The FarmerType record.
 * @param typeName                     The name of the farmer type.
 * @param levelRequirement             The level requirement of the farmer type.
 * @param bonusEarnings                The bonus earnings of the farmer type.
 * @param seedCostReduction            The seed cost reduction of the farmer type.
 * @param waterBonusLimitIncrease      The water bonus limit increase of the farmer type.
 * @param fertilizerBonusLimitIncrease The fertilizer bonus limit increase of the farmer type.
 * @param registrationFee              The registration fee of the farmer type.
 */
public record FarmerType(String typeName, int levelRequirement, int bonusEarnings, int seedCostReduction,
                         int waterBonusLimitIncrease, int fertilizerBonusLimitIncrease, int registrationFee) {}
