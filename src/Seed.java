/**
 * The Seed record.
 * @param name             The name of the seed.
 * @param harvestTime      The harvest time of the seed.
 * @param waterNeeds       The minimum water level of the seed.
 * @param fertilizerNeeds  The minimum fertilizer level of the seed.
 * @param minProduce       The minimum produce of the seed.
 * @param maxProduce       The maximum produce of the seed.
 * @param cost             The cost of the seed.
 * @param baseSellingPrice The base value of the seed.
 * @param expYield         The experience gained from harvest the crop from this seed.
 * @param isFlower         Whether the seed is a flower.
 * @param isTree           Whether the seed is a tree.
 */
public record Seed(String name, int harvestTime, int waterNeeds, int waterLimit, int fertilizerNeeds,
                   int fertilizerLimit, int minProduce, int maxProduce, int cost, int baseSellingPrice, float expYield,
                   boolean isFlower, boolean isTree) {}
