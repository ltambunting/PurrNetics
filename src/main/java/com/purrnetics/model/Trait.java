package com.purrnetics.model;

import java.util.HashSet;
import java.util.Set;

public class Trait {
    private final String key; // represents the type of trait
    Set<String> traitVariants;

    public Trait(String key) {
        this.key = key;
        this.traitVariants = new HashSet<>();
    }

    // GETTERS
    public String getKey() {
        return this.key;
    }

    public Set<String> getTraitVariants() {
        return this.traitVariants;
    }

    public void addTraitVariant(String traitVariant) {
        this.traitVariants.add(traitVariant);
    }

}
