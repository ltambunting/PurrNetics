package com.purrnetics.model;

import java.util.Map;

public record BreedingResult() {
    private static Map<Gene, Map<AllelePair, Double>> genotypeDistribution;
    private static Map<Trait, Map<String, Double>> phenotypeDistribution;
}
