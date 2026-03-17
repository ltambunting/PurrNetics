package model;

import java.util.HashSet;
import java.util.Set;

public class Trait {
    private final String key; // represents the type of trait
    Set<String> traitVariants;

    public Trait(String key) {
        this.key = key;
        this.traitVariants = new HashSet<>();
    }
}
