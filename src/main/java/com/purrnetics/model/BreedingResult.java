package com.purrnetics.model;

import java.util.Map;

public record BreedingResult() {
    private static Map<AllelePair, Double> genotypeDistribution;
    private static Map<String, Double> phenotypeDistribution;
}
