package com.purrnetics.model;

import java.util.Map;

public record BreedingResult(Map<Gene, Map<AllelePair, Double>> genotypeDistribution, Map<Trait, Map<String, Double>> phenotypeDistribution) {}