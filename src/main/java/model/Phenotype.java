package model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Phenotype {
    private final Map<Trait, String> expressedVariants; //the expressed variant for each trait
    
    public Phenotype() {
        expressedVariants = new HashMap<>();
    }

    public Map<Trait, String> getExpressedVariants() {
        return Collections.unmodifiableMap(expressedVariants);
    }
}
