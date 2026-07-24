package com.purrnetics.model;

import java.util.Map;

public record SexLinkedTraitDistribution(Trait trait, Map<Sex, Map<String, Double>> distributions) {}
